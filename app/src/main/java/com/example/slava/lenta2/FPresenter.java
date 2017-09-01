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
        RecyclerView rec = fragListener.getRec();
        this.fragListener = fragListener;
        title = this.fragListener.getTitle();
        boolean includeDescription = false;
        switch (title){
            case OneFragment.VAL_ALL:
                datas = LentaClient.getInstance().getAll();
//                rec.setLayoutManager(new GridLayoutManager(fragListener.getCtx(), 2));
                rec.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;
            case OneFragment.VAL_HOTTEST:
                datas = LentaClient.getInstance().getHottest();
                rec.setLayoutManager(new LinearLayoutManager(fragListener.getCtx()));
                includeDescription = true;
                break;
            case OneFragment.VAL_NEWEST:
                datas = LentaClient.getInstance().getNewest();
//                rec.setLayoutManager(new GridLayoutManager(fragListener.getCtx(), 2));
                rec.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;
        }

        adapter = new RvAdapter(fragListener.getCtx(), datas, includeDescription);
        rec.setAdapter(adapter);
//        fragMan = fragListener.getFm();
//        this.fragment = fragment;
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

    public void reload(){
        fragListener.getRec().setAdapter(adapter);
    }

//    public String getTitle() {
//        return title;
//    }

    protected FPresenter(Parcel in) {
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
    }

}
