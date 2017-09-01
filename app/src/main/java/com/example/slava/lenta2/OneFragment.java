package com.example.slava.lenta2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by slava on 28.08.2017.
 */

public class OneFragment extends Fragment implements FPresenter.FragListener{
    public static final String VAL_HOTTEST = "Hottest news";
    public static final String VAL_NEWEST = "Latest news";
    public static final String VAL_ALL = "All news";

    public static final String KEY_HEADER = "header";
    private static final String KEY_PRESENTER = "presenter";

    private static final String TAG = "OneFragment";

    private RecyclerView recyclerView;
    private FPresenter presenter;
    private String title;

    public static OneFragment newInstance(String title){
        Log.d(TAG, "newInstance: Fragment created");
        OneFragment fragment = new OneFragment();
        Bundle arguments = new Bundle();
        arguments.putString(KEY_HEADER, title);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        title = getArguments().getString(KEY_HEADER);

        initViews(view);

        presenter = new FPresenter(this, this);

//        if (savedInstanceState != null) {
//            presenter = savedInstanceState.getParcelable(KEY_PRESENTER);
//            presenter.reload();
//        }
//        else {
//            presenter = new FPresenter(this, this);
//        }

        return view;
    }

    private void initViews(View view) {
        ((TextView) view.findViewById(R.id.tvFragmentTitle)).setText(title);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        (view.findViewById(R.id.button)).setOnClickListener(v -> presenter.onViewButtonClicked());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_PRESENTER, presenter);
    }

    @Override
    public RecyclerView getRec() {
        return recyclerView;
    }

    @Override
    public Context getCtx() {
        return getActivity();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public FragmentManager getFm() {
        return getActivity().getFragmentManager();
    }

    public FPresenter getPresenter() {
        return presenter;
    }
}
