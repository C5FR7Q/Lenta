package com.example.slava.lenta2.views.fragment_details.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slava.lenta2.Constants;
import com.example.slava.lenta2.R;
import com.example.slava.lenta2.views.activity.presenter.IMainPresenter;
import com.example.slava.lenta2.views.fragment_details.presenter.DetailsPresenter;
import com.example.slava.lenta2.views.fragment_details.presenter.IDetailsPresenter;

/**
 * Created by slava on 07.09.2017.
 */

public class DetailsFragment extends Fragment implements IDetailsFragmentView {
    private IDetailsPresenter presenter;
    private RecyclerView recyclerView;

    public static DetailsFragment getInstance(String title, IMainPresenter mainPresenter) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.MAIN_PRESENTER, mainPresenter);
        bundle.putString(Constants.TITLE, title);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        IMainPresenter mainPresenter = (IMainPresenter) getArguments().getParcelable(Constants.MAIN_PRESENTER);
        String title = getArguments().getString(Constants.TITLE);
        View view = inflater.inflate(R.layout.fragment, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        presenter = new DetailsPresenter(this, mainPresenter, title);
        return view;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }
}
