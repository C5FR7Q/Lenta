package com.example.slava.lenta2.views.fragment_main.presenter;

import android.view.View;

import com.example.slava.lenta2.Constants;
import com.example.slava.lenta2.adapters.RvAdapterMain;
import com.example.slava.lenta2.views.activity.presenter.IMainPresenter;
import com.example.slava.lenta2.views.fragment_details.view.DetailsFragment;
import com.example.slava.lenta2.views.fragment_main.view.IFragmentView;

import java.util.ArrayList;

/**
 * Created by slava on 06.09.2017.
 */

public class FragmentPresenter implements IFragmentPresenter{
    private IFragmentView fragmentView;
    private IMainPresenter mainPresenter;

    public FragmentPresenter(IFragmentView fragmentView, IMainPresenter mainPresenter) {
        this.fragmentView = fragmentView;
        this.mainPresenter = mainPresenter;
        ArrayList<String> titles = initTitles();
        ArrayList<View.OnClickListener> listeners = initListeners();
        fragmentView.setAdapter(new RvAdapterMain(titles, listeners));
    }

    private ArrayList<String> initTitles() {
        return Constants.getTitles();
    }

    private ArrayList<View.OnClickListener> initListeners() {
        ArrayList<View.OnClickListener> listeners = new ArrayList<>();
        for (int i = 0; i < 3;i ++){
            int finalI = i;
            listeners.add(v -> onViewButtonClicked(finalI));
        }
        return listeners;
    }

    @Override
    public void onViewButtonClicked(int i){
        if (mainPresenter != null){
            String title = initTitles().get(i);
            mainPresenter.replaceFragment(DetailsFragment.getInstance(title, mainPresenter));
            mainPresenter.showHomeButton(true, title);
        }
    }

}
