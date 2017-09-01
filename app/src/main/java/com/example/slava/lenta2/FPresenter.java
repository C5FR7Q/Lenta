package com.example.slava.lenta2;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by slava on 30.08.2017.
 */

public class FPresenter implements Parcelable{

    private RvAdapter adapter;
    private FragListener fragListener;
    private boolean includeDescription;



    interface FragListener{
        RecyclerView getRec();
        Context getCtx();
        String getTitle();
        void showTitleAndButton(boolean b);
    }
    private String title;
    private Observable<List<Data>> datas;

    public FPresenter(FragListener fragListener) {
        this.fragListener = fragListener;
        title = this.fragListener.getTitle();
        includeDescription = false;
        RecyclerView.LayoutManager layoutManager = null;
        switch (title){
            case OneFragment.VAL_ALL:
                datas = LentaClient.getInstance().getAll();
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                break;
            case OneFragment.VAL_HOTTEST:
                datas = LentaClient.getInstance().getHottest();
                layoutManager = new LinearLayoutManager(fragListener.getCtx());
                includeDescription = true;
                break;
            case OneFragment.VAL_NEWEST:
                datas = LentaClient.getInstance().getNewest();
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                break;
        }

        fragListener.getRec().setLayoutManager(layoutManager);
        adapter = new RvAdapter(fragListener.getCtx(), datas, includeDescription);
        fragListener.getRec().setAdapter(adapter);

    }

    public void reload(FragListener fragListener) {
        this.fragListener = fragListener;
        RecyclerView.LayoutManager layoutManager = null;
        switch (title){
            case OneFragment.VAL_ALL:
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                break;
            case OneFragment.VAL_HOTTEST:
                layoutManager = new LinearLayoutManager(fragListener.getCtx());
                break;
            case OneFragment.VAL_NEWEST:
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                break;
        }
        fragListener.getRec().setLayoutManager(layoutManager);
        adapter = new RvAdapter(fragListener.getCtx(), datas, includeDescription);
        fragListener.getRec().setAdapter(adapter);
    }

    public void onViewButtonClicked(){
        switch (title){
            case OneFragment.VAL_HOTTEST:
                MainPresenter.setVisibility(1, View.GONE);
                MainPresenter.setVisibility(2, View.GONE);
                break;
            case OneFragment.VAL_NEWEST:
                MainPresenter.setVisibility(0, View.GONE);
                MainPresenter.setVisibility(2, View.GONE);
                break;
            case OneFragment.VAL_ALL:
                MainPresenter.setVisibility(0, View.GONE);
                MainPresenter.setVisibility(1, View.GONE);
                break;

        }

        MainPresenter.getActionBar().setTitle(title);
        MainPresenter.getActionBar().setDisplayHomeAsUpEnabled(true);
        showTitleAndButton(false);
        adapter.setFullSize();
    }

    public void showTitleAndButton(boolean b) {
        fragListener.showTitleAndButton(b);
    }

    public void setCutSize() {
        adapter.setCutSize();
    }

//Parcellable generation.
    protected FPresenter(Parcel in) {
        includeDescription = in.readByte() != 0;
        title = in.readString();
    }

    public static final Creator<FPresenter> CREATOR = new Creator<FPresenter>() {
        @Override
        public FPresenter createFromParcel(Parcel in) {
            return new FPresenter(in);
        }

        @Override
        public FPresenter[] newArray(int size) {
            return new FPresenter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (includeDescription ? 1 : 0));
        dest.writeString(title);
    }
}
