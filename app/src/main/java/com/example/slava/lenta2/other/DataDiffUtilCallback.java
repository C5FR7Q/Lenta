package com.example.slava.lenta2.other;

import android.support.v7.util.DiffUtil;

import com.example.slava.lenta2.view.Data;

import java.util.List;

/**
 * Created by slava on 16.10.2017.
 */

public class DataDiffUtilCallback extends DiffUtil.Callback {
    private List<Data> oldData;
    private List<Data> newData;

    public DataDiffUtilCallback(List<Data> oldData, List<Data> newData) {
        this.oldData = oldData;
        this.newData = newData;
    }

    @Override
    public int getOldListSize() {
        return oldData.size();
    }

    @Override
    public int getNewListSize() {
        return newData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldData.get(oldItemPosition) == newData.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return (oldData.get(oldItemPosition).getTitle()).equals(newData.get(newItemPosition).getTitle());
    }
}
