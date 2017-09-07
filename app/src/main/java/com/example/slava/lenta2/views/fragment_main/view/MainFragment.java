package com.example.slava.lenta2.views.fragment_main.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slava.lenta2.Constants;
import com.example.slava.lenta2.R;
import com.example.slava.lenta2.views.activity.presenter.IMainPresenter;
import com.example.slava.lenta2.views.fragment_main.presenter.FragmentPresenter;
import com.example.slava.lenta2.views.fragment_main.presenter.IFragmentPresenter;

/**
 * Created by slava on 28.08.2017.
 */

public class MainFragment extends Fragment implements IFragmentView{
    private RecyclerView recyclerView;
    private IFragmentPresenter fragmentPresenter;

    public static Fragment getInstance(IMainPresenter mainPresenter) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.MAIN_PRESENTER, mainPresenter);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        IMainPresenter mainPresenter = (IMainPresenter) getArguments().getSerializable(Constants.MAIN_PRESENTER);
        View view = inflater.inflate(R.layout.fragment, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_main);
        fragmentPresenter = new FragmentPresenter(this, mainPresenter);
        return view;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

}
