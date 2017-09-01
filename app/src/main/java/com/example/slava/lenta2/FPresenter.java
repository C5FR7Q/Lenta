package com.example.slava.lenta2;

import android.app.Fragment;
import android.app.FragmentManager;
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
        FragmentManager getFm();
    }
    private String title;
    private Observable<List<Data>> datas;
//    private FragmentManager fragMan;
//    private Fragment fragment;

    public FPresenter(FragListener fragListener, Fragment fragment) {
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


//        fragMan = fragListener.getFm();
//        this.fragment = fragment;
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

    //  Видимо здесь бы пригодился Dagger со своим Dependency Injection
    public void onViewButtonClicked(){
        MainPresenter instance = MainPresenter.getInstance();
        switch (title){
            case OneFragment.VAL_HOTTEST:
                instance.setVisibility(1, View.GONE);
                instance.setVisibility(2, View.GONE);
                break;
            case OneFragment.VAL_NEWEST:
                instance.setVisibility(0, View.GONE);
                instance.setVisibility(2, View.GONE);
                break;
            case OneFragment.VAL_ALL:
                instance.setVisibility(0, View.GONE);
                instance.setVisibility(1, View.GONE);
                break;

        }

        adapter.setFullSize();
        adapter.notifyDataSetChanged();
        fragListener.getRec().scrollToPosition(0);
        instance.setSelected();
    }

//    public void setFullSize(){
//        adapter.setFullSize();
//    }
    public void setCutSize() {
        adapter.setCutSize();
    }

    //    public String getTitle() {
//        return title;
//    }


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
