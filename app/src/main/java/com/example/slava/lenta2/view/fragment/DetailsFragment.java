package com.example.slava.lenta2.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slava.lenta2.R;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.TitlesClient;
import com.example.slava.lenta2.other.PostExecuteSchedulerProvider;
import com.example.slava.lenta2.presenter.DetailsFragmentPresenter;
import com.example.slava.lenta2.presenter.IDetailsFragmentPresenter;
import com.example.slava.lenta2.presenter.IMainActivityPresenter;
import com.example.slava.lenta2.view.Data;
import com.example.slava.lenta2.view.adapters.RvAdapterItem;

import java.util.List;

/**
 * Created by slava on 07.09.2017.
 */

public class DetailsFragment extends BaseFragment implements IDetailsFragmentView {
    private static final String MAIN_PRESENTER = "main_presenter";
    private static final String TITLE = "title";
    private IDetailsFragmentPresenter presenter;
    private RecyclerView recyclerView;
    private RvAdapterItem adapter;

    public static DetailsFragment getInstance(String title, IMainActivityPresenter mainPresenter) {
        Log.d("DetailsFragment", "mainPresenter == null:" + (mainPresenter == null));
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MAIN_PRESENTER, mainPresenter);
        bundle.putString(TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        String title = getArguments().getString(TITLE);
        IMainActivityPresenter mainPresenter = (IMainActivityPresenter) getArguments().getParcelable(MAIN_PRESENTER);
        presenter = new DetailsFragmentPresenter(this,
                mainPresenter,
                title,
                new TitlesClient(),
                LentaClient.getInstance(),
                new PostExecuteSchedulerProvider());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        presenter.onCreateView(savedInstanceState, this);

        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeLayout);
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(() -> presenter.refresh());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroyView();
    }

    @Override
    public void showDatas(List<Data> datas, RvAdapterItem.OnItemSelectedListener listener) {
        adapter = new RvAdapterItem(datas, true, true, listener);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void refreshDatas(List<Data> datas) {
        adapter.refreshDatas(datas);
    }

}
