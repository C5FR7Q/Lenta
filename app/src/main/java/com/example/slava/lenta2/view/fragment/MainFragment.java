package com.example.slava.lenta2.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slava.lenta2.R;
import com.example.slava.lenta2.model.cache.Cache;
import com.example.slava.lenta2.model.data_client.LentaClient;
import com.example.slava.lenta2.model.titles_client.TitlesClient;
import com.example.slava.lenta2.other.DataListMapper;
import com.example.slava.lenta2.other.PostExecuteSchedulerProvider;
import com.example.slava.lenta2.other.PreExecuteSchedulerProvider;
import com.example.slava.lenta2.presenter.IMainActivityPresenter;
import com.example.slava.lenta2.presenter.IMainFragmentPresenter;
import com.example.slava.lenta2.presenter.MainFragmentPresenter;
import com.example.slava.lenta2.repository.Repository;
import com.example.slava.lenta2.view.Data;
import com.example.slava.lenta2.view.adapters.RvAdapterMain;

import java.util.List;

/**
 * Created by slava on 28.08.2017.
 */

public
class MainFragment
		extends BaseFragment
		implements IMainFragmentView
{
	private static final String MAIN_PRESENTER = "main_presenter";
	private IMainFragmentPresenter fragmentPresenter;
	private RvAdapterMain adapter;

	public static
	Fragment getInstance(final IMainActivityPresenter mainPresenter) {
		final MainFragment fragment = new MainFragment();
		final Bundle bundle = new Bundle();
		bundle.putParcelable(MAIN_PRESENTER, mainPresenter);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public
	void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		final IMainActivityPresenter mainPresenter = getArguments().getParcelable(MAIN_PRESENTER);
		fragmentPresenter = new MainFragmentPresenter(
				this,
				mainPresenter,
				new TitlesClient(),
				new PostExecuteSchedulerProvider(),
				new Repository(LentaClient.getInstance(), new Cache(getActivity()), new DataListMapper(), new PreExecuteSchedulerProvider())
		);
		fragmentPresenter.onCreate(savedInstanceState);
		adapter = new RvAdapterMain(
				new TitlesClient().getTitles(),
				fragmentPresenter,
				link -> fragmentPresenter.onSelect(link)
		);

	}

	@Nullable
	@Override
	public
	View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment, container, false);
		final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_main);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
		recyclerView.setAdapter(adapter);

		fragmentPresenter.onCreateView(savedInstanceState, this);

		refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
		refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
		refreshLayout.setOnRefreshListener(() -> fragmentPresenter.refresh());
		return view;
	}

	@Override
	public
	void onDestroyView() {
		super.onDestroyView();
		fragmentPresenter.onDestroyView();
	}

	@Override
	public
	void setDataLists(final List<List<Data>> lists) {
		adapter.setLists(lists);
	}

}
