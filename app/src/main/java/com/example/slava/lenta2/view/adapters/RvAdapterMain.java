package com.example.slava.lenta2.view.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.slava.lenta2.R;
import com.example.slava.lenta2.model.titles_client.TitlesClient;
import com.example.slava.lenta2.view.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slava on 29.08.2017.
 */

public
class RvAdapterMain
		extends RecyclerView.Adapter<RvAdapterMain.ViewHolder>
{
	public
	interface OnViewBtnClickListener
	{
		void onViewBtnClicked(int position);
	}

	private final ArrayList<String> titles;

	private final OnViewBtnClickListener mViewBtnClickListener;
	private final RvAdapterItem.OnItemSelectedListener insideListener;
	private final ArrayList<RvAdapterItem> insideAdapters;
	private List<List<Data>> mLists = new ArrayList<>();

	public
	RvAdapterMain(final TitlesClient titlesClient,
	              final OnViewBtnClickListener viewBtnClickListener,
	              final RvAdapterItem.OnItemSelectedListener insideListener) {
		titles = titlesClient.getTitles();
		mViewBtnClickListener = viewBtnClickListener;
		this.insideListener = insideListener;
		insideAdapters = new ArrayList<>();
	}

	@Override
	public
	ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
		final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public
	void onBindViewHolder(final ViewHolder holder, final int position) {
		Log.d("RvAdapterMain", "mLists.size():" + mLists.size());
		for (int i = 0; i < mLists.size(); i++){
			Log.d("RvAdapterMain", "mLists.get(i).size():" + mLists.get(i).size());
		}
		final boolean includeDesc;
		holder.tvTitle.setText(titles.get(position));
		holder.btnView.setOnClickListener(v -> mViewBtnClickListener.onViewBtnClicked(position));
		if (position == 0) {
			holder.rv.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
			includeDesc = true;
		} else {
			holder.rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
			includeDesc = false;
		}

		if (insideAdapters.size() <= position) {
			final RvAdapterItem adapter = new RvAdapterItem(mLists.get(position), includeDesc, false, insideListener);
			insideAdapters.add(adapter);
		}
		holder.rv.setAdapter(insideAdapters.get(position));
	}

	@Override
	public
	int getItemCount() {
		return (mLists == null) ? 0 : mLists.size();
	}

	/*public
	void addData(final List<Data> data) {
		if (isValid(data)) {
			mLists.add(data);
			notifyDataSetChanged();
		}
	}*/

	public
	void setLists(final List<List<Data>> lists) {
		if (isValid(lists)) {
			if (mLists.size() == 0) {
				mLists = lists;
				notifyDataSetChanged();
			} else {
				mLists = lists;
				for (int i = 0; i < insideAdapters.size(); i++) {
					insideAdapters.get(i).refreshData(mLists.get(i));
				}
			}
		}
	}

	private
	boolean isValid(final List list) {
		return !(list == null || list.isEmpty());
	}

	public
	class ViewHolder
			extends RecyclerView.ViewHolder
	{
		private final TextView tvTitle;
		private final Button btnView;
		private final RecyclerView rv;

		private
		ViewHolder(final View itemView) {
			super(itemView);
			tvTitle = (TextView) itemView.findViewById(R.id.tvFmtTitle);
			btnView = (Button) itemView.findViewById(R.id.btnFmtView);
			rv = (RecyclerView) itemView.findViewById(R.id.rv_item);
		}
	}

}
