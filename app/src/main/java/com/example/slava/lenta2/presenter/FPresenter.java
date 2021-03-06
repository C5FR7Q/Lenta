package com.example.slava.lenta2.presenter;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.slava.lenta2.RvAdapter;
import com.example.slava.lenta2.model.Data;
import com.example.slava.lenta2.model.LentaClient;
import com.example.slava.lenta2.view.OneFragment;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by slava on 30.08.2017.
 */

public class FPresenter implements Parcelable{

    private RvAdapter adapter;
    private FragListener fragListener;
    private boolean includeDescription;



    public interface FragListener{
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

        MainPresenter.putFragPresenter(this, title);

    }

    public void reload(FragListener fragListener) {
        RecyclerView.LayoutManager layoutManager;
        this.fragListener = fragListener;

        if (includeDescription){
            layoutManager = new LinearLayoutManager(fragListener.getCtx());
        }
        else layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        if (MainPresenter.getActionBar().getTitle().equals(MainPresenter.MAIN_TITLE)) {
            showTitleAndButton(true);
        }
        else showTitleAndButton(false);

        fragListener.getRec().setLayoutManager(layoutManager);
        adapter = new RvAdapter(fragListener.getCtx(), datas, includeDescription);
        fragListener.getRec().setAdapter(adapter);
        MainPresenter.putFragPresenter(this, title);
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

        // TODO: 01.09.2017 from here

        includeDescription = true;
//        adapter = new RvAdapter(fragListener.getCtx(), datas, true);
//        ((RvAdapter) fragListener.getRec().getAdapter()).setIncludeDesc(includeDescription);
        setManagerAndIncDesc(true);
//        fragListener.getRec().setAdapter(adapter);

        adapter.setFullSize();
    }

    public void setManagerAndIncDesc(boolean b){
        if (!title.equals(OneFragment.VAL_HOTTEST))
            includeDescription = b;
        if (!includeDescription){
            if (!title.equals(OneFragment.VAL_HOTTEST))
            {
                fragListener.getRec().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            }
        }
        else {
            fragListener.getRec().setLayoutManager(new LinearLayoutManager(fragListener.getCtx()));
        }
        ((RvAdapter) fragListener.getRec().getAdapter()).setIncludeDesc(includeDescription);
        fragListener.getRec().setAdapter(adapter);
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
