package com.example.slava.lenta2.presenter;

import android.os.Bundle;

import com.example.slava.lenta2.model.data_client.Data;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.ITitlesClient;
import com.example.slava.lenta2.other.IPostExecuteSchedulerProvider;
import com.example.slava.lenta2.view.fragment.DetailsFragment;
import com.example.slava.lenta2.view.fragment.IMainFragmentView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by slava on 06.09.2017.
 */

public class MainFragmentPresenter implements IMainFragmentPresenter {
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
                                 IPostExecuteSchedulerProvider postExecuteSchedulerProvider) {
        this.fragmentView = fragmentView;
        this.mainPresenter = mainPresenter;
        this.titlesClient = titlesClient;
        this.lentaClient = lentaClient;
        this.postExecuteSchedulerProvider = postExecuteSchedulerProvider;
    }

    private ArrayList<String> initTitles() {
        return titlesClient.getTitles();
    }

    @Override
    public void onViewClicked(int position) {
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
        List<List<Data>> datas = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            disposables.add(lentaClient
                    .get(i)
                    .observeOn(postExecuteSchedulerProvider.getScheduler())
                    .subscribe(datas::add, throwable -> {
                    }, () -> fragmentView.setDatas(datas)));
    }

    @Override
    public void onDestroyView() {
        this.fragmentView = null;
        disposables.dispose();
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
        List<List<Data>> datas = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            disposables.add(lentaClient
                    .get(i)
                    .observeOn(postExecuteSchedulerProvider.getScheduler())
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
