package com.example.slava.lenta2.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slava.lenta2.R;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.TitlesClient;
import com.example.slava.lenta2.other.SchedulersProviderImpl;
import com.example.slava.lenta2.presenter.IMainActivityPresenter;
import com.example.slava.lenta2.presenter.MainFragmentPresenter;
import com.example.slava.lenta2.presenter.IMainFragmentPresenter;

/**
 * Created by slava on 28.08.2017.
 */

public class MainFragment extends Fragment implements IMainFragmentView {
    private static final String MAIN_PRESENTER = "main_presenter";
    private RecyclerView recyclerView;
    private IMainFragmentPresenter fragmentPresenter;

    public static Fragment getInstance(IMainActivityPresenter mainPresenter) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MAIN_PRESENTER, mainPresenter);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        IMainActivityPresenter mainPresenter = (IMainActivityPresenter) getArguments().getParcelable(MAIN_PRESENTER);
        fragmentPresenter = new MainFragmentPresenter(this,
                mainPresenter,
                new TitlesClient(),
                LentaClient.getInstance(),
                new SchedulersProviderImpl());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        fragmentPresenter.onCreateView(savedInstanceState, this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentPresenter.onDestroyView();
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void browse(String link) {
        getActivity().startActivity(new Intent()
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse(link))
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }

}
