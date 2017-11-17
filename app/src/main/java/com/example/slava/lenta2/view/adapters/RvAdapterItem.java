package com.example.slava.lenta2.view.adapters;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.slava.lenta2.R;
import com.example.slava.lenta2.other.DataDiffUtilCallback;
import com.example.slava.lenta2.view.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slava on 29.08.2017.
 */

public
class RvAdapterItem
		extends RecyclerView.Adapter<RvAdapterItem.ViewHolder>
{
	public
	interface OnItemSelectedListener
	{

		void onSelect(String link);
	}

	private final boolean includeDesc;
	private final OnItemSelectedListener listener;
	private final boolean isFullSized;
	private List<Data> mDataList = new ArrayList<>();

	public
	RvAdapterItem(final List<Data> mDataList,
	              final boolean includeDesc,
	              final boolean isFullSized,
	              final OnItemSelectedListener listener) {
		this.mDataList = mDataList;
		this.includeDesc = includeDesc;
		this.listener = listener;
		this.isFullSized = isFullSized;
	}

	@Override
	public
	ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
		final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inside_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public
	void onBindViewHolder(final ViewHolder holder, final int position) {
		final Data data = mDataList.get(position);

		holder.tvArticleDate.setText(data.getPubDate());
		holder.tvArticleTitle.setText(data.getTitle());
		if (includeDesc) {
			holder.tvArticleBody.setText(data.getDescription());
		} else {
			holder.tvArticleBody.setVisibility(View.GONE);
		}
		holder.tvArticleCategory.setText(data.getCategory());

		if (data.getPicLink() != null) {
			Glide.with(holder.itemView.getContext())
					.load(data.getPicLink())
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					.into(holder.iv);
		} else {
			holder.iv.setVisibility(View.GONE);
		}

		holder.itemView.setOnClickListener(v -> listener.onSelect(data.getLink()));
	}

	@Override
	public
	int getItemCount() {
		if (isFullSized) {
			return mDataList.size();
		}
		return Math.min(mDataList.size(), 4);
	}

	public
	void refreshData(final List<Data> dataList) {
		final DataDiffUtilCallback utilCallback = new DataDiffUtilCallback(mDataList, dataList);
		final DiffUtil.DiffResult result = DiffUtil.calculateDiff(utilCallback, true);

		mDataList.clear();
		mDataList.addAll(dataList);

		result.dispatchUpdatesTo(this);
//        notifyDataSetChanged();
	}

	public
	class ViewHolder
			extends RecyclerView.ViewHolder
	{
		private final TextView tvArticleTitle;
		private final TextView tvArticleBody;
		private final TextView tvArticleDate;
		private final TextView tvArticleCategory;
		private final ImageView iv;

		private
		ViewHolder(final View itemView) {
			super(itemView);
			tvArticleBody = (TextView) itemView.findViewById(R.id.tvArcticleBody);
			tvArticleCategory = (TextView) itemView.findViewById(R.id.tvArticleCategory);
			tvArticleDate = (TextView) itemView.findViewById(R.id.tvArticleDate);
			tvArticleTitle = (TextView) itemView.findViewById(R.id.tvArticleTitle);
			iv = (ImageView) itemView.findViewById(R.id.iv);
		}
	}

}
