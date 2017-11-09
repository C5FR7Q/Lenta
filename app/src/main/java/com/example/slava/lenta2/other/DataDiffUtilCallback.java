package com.example.slava.lenta2.other;

import android.support.v7.util.DiffUtil;

import com.example.slava.lenta2.view.Data;

import java.util.List;

/**
 * Created by slava on 16.10.2017.
 */

public
class DataDiffUtilCallback
		extends DiffUtil.Callback
{
	private final List<Data> oldData;
	private final List<Data> newData;

	public
	DataDiffUtilCallback(final List<Data> oldData, final List<Data> newData) {
		this.oldData = oldData;
		this.newData = newData;
	}

	@Override
	public
	int getOldListSize() {
		return oldData.size();
	}

	@Override
	public
	int getNewListSize() {
		return newData.size();
	}

	@Override
	public
	boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
		return (oldData.get(oldItemPosition).getTitle()).equals(newData.get(newItemPosition).getTitle());
	}

	@Override
	public
	boolean areContentsTheSame(final int oldItemPosition, final int newItemPosition) {
		return (oldData.get(oldItemPosition).getTitle()).equals(newData.get(newItemPosition).getTitle());
	}
}
