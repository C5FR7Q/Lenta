package com.example.slava.lenta2.other;

import android.support.v7.util.DiffUtil;
import android.util.Log;

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
        boolean b = (oldData.get(oldItemPosition).getTitle()).equals(newData.get(newItemPosition).getTitle());
        Log.d("DataDiffUtilCallback", "b:" + b);
        return b;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        boolean equals = (oldData.get(oldItemPosition).getTitle()).equals(newData.get(newItemPosition).getTitle());
        Log.d("DataDiffUtilCallback", "equals:" + equals);
        return equals;
    }
}
