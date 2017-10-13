package com.example.slava.lenta2.presenter;

import android.os.Bundle;

import com.example.slava.lenta2.model.cache.ICache;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.ITitlesClient;
import com.example.slava.lenta2.other.DataListMapper;
import com.example.slava.lenta2.other.IPostExecuteSchedulerProvider;
import com.example.slava.lenta2.view.Data;
import com.example.slava.lenta2.view.fragment.BaseFragment;
import com.example.slava.lenta2.view.fragment.DetailsFragment;
import com.example.slava.lenta2.view.fragment.IMainFragmentView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by slava on 06.09.2017.
 */

public class MainFragmentPresenter implements IMainFragmentPresenter {
    private ICache cache;
    private DataListMapper mapper;
    private IMainFragmentView fragmentView;
    private IMainActivityPresenter mainPresenter;
    private ITitlesClient titlesClient;
    private LentaClient lentaClient;
    private IPostExecuteSchedulerProvider postExecuteSchedulerProvider;
    private CompositeDisposable disposables;

    public MainFragmentPresenter(IMainFragmentView fragmentView,
                                 IMainActivityPresenter mainPresenter,
                                 ITitlesClient titlesClient,
                                 LentaClient lentaClient,
                                 IPostExecuteSchedulerProvider postExecuteSchedulerProvider,
                                 ICache cache) {
        this.fragmentView = fragmentView;
        this.mainPresenter = mainPresenter;
        this.titlesClient = titlesClient;
        this.lentaClient = lentaClient;
        this.postExecuteSchedulerProvider = postExecuteSchedulerProvider;
        this.cache = cache;
        this.mapper = new DataListMapper();
    }

    private ArrayList<String> initTitles() {
        return titlesClient.getTitles();
    }

    @Override
    public void onViewClicked(int position) {
        if (!fragmentView.hasInternetConnection()){
            fragmentView.showMessage(BaseFragment.MSG_NO_INTERNET);
            return;
        }
        if (mainPresenter != null){
            String title = initTitles().get(position);
            mainPresenter.replaceFragment(DetailsFragment.getInstance(title, mainPresenter), true);
            mainPresenter.showHomeButton(true, title);
        }
    }

    @Override
    public void onCreateView(Bundle savedInstanceState, IMainFragmentView view) {
        this.fragmentView = view;
        disposables = new CompositeDisposable();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        disposables = new CompositeDisposable();
        if (fragmentView.hasInternetConnection()) {
            sendInternetRequest();
        }
        else {
            loadFromCache();
        }
    }

    private void loadFromCache() {
        disposables.add(cache.getDataList()
                .observeOn(postExecuteSchedulerProvider.getScheduler())
                .subscribe((datas) -> {
                    fragmentView.setDatas(datas);
                }, throwable -> fragmentView.showMessage(
                        "Internet connection is lost. No cache available."
                )));
    }

    private void sendInternetRequest() {
        List<List<Data>> datas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            disposables.add(
                    lentaClient
                        .get(i)
                        .map(mapper)
                        .observeOn(postExecuteSchedulerProvider.getScheduler())
                        .subscribe((e) -> {
                                    datas.add(e);

                            /*Это, скорее всего, костыль. Были идеи нормальной реализации с использованием
                            * combineLatest. Но в итоге выглядело как еще больший костыль. При этом
                             * работало не так, как нужно.*/
                                    if (datas.size() == 3){
                                        cache.putDataList(datas);
                                    }
                                }, throwable -> {},
                                () -> fragmentView.setDatas(datas)));
        }
    }

    @Override
    public void onDestroyView() {
        this.fragmentView = null;
        this.disposables.dispose();
        this.disposables = null;
    }


    /*Если заранее известно, что при pull-to-refresh порядок новостей в категориях не меняется,
    * можно оптимизировать процесс. Для этого в refresh должен передаваться лист адаптера. Далее:
    * для данного презентера для каждой категории сравнивается первый элемент(статья) посредством
    * какого-то идентификатора. Можно, что логично, сравнивать по загаловкам статьи. Если равны,
    * то не вызывается метод adapter.setAllDatas(...), так как перерисовывать одно и то же смысла
    * не имеет. Для DetailsFragmentPresenter все аналогично, только для его единственной категории. */
    @Override
    public void refresh() {
        fragmentView.setRefreshing(true);
        if (!fragmentView.hasInternetConnection()){
            fragmentView.showMessage(BaseFragment.MSG_NO_INTERNET);
            fragmentView.setRefreshing(false);
            return;
        }
        List<List<Data>> datas = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            disposables.add(lentaClient
                    .get(i)
                    .observeOn(postExecuteSchedulerProvider.getScheduler())
                    .map(mapper)
                    .subscribe(datas::add, throwable -> {},
                            () ->{
                                fragmentView.setRefreshing(false);
                                fragmentView.setDatas(datas);
                            }));
    }
    @Override
    public void onSelect(String link) {
        if (link.matches("^https://lenta\\.ru.*$"))
            fragmentView.browse(link);
    }
}
