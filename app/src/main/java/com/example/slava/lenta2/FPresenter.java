package com.example.slava.lenta2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by slava on 30.08.2017.
 */

public class FPresenter implements Parcelable{
    interface FragListener{
        RecyclerView getRec();
        Context getCtx();
        String getTitle();
        FragmentManager getFm();
    }
    private static final String TAG = "FPresenter";
    private String title;
    private Observable<List<Data>> datas;
    private FragmentManager fragMan;
    private Fragment fragment;

    public FPresenter(FragListener fragListener, Fragment fragment) {
        RecyclerView rec = fragListener.getRec();
        title = fragListener.getTitle();
        switch (title){
            case OneFragment.VAL_ALL:
                datas = LentaClient.getInstance().getAll();
                rec.setLayoutManager(new GridLayoutManager(fragListener.getCtx(), 2));
                Log.d(TAG, "FPresenter: 1");
            break;
            case OneFragment.VAL_HOTTEST:
                datas = LentaClient.getInstance().getHottest();
                LinearLayoutManager layout = new LinearLayoutManager(fragListener.getCtx());
                rec.setLayoutManager(layout);

                Log.d(TAG, "FPresenter: 2");
            break;
            case OneFragment.VAL_NEWEST:
                datas = LentaClient.getInstance().getNewest();
                rec.setLayoutManager(new GridLayoutManager(fragListener.getCtx(), 2));
                Log.d(TAG, "FPresenter: 3");
            break;
        }


        rec.setAdapter(new RvAdapter(fragListener.getCtx(), datas));
        fragMan = fragListener.getFm();
        this.fragment = fragment;
    }


//  Видимо здесь бы пригодился Dagger со своим Dependency Injection
    public void onViewButtonClicked(){
        fragMan.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.content, fragment)
                .commit();
//        MainPresenter.getInstance().setSelectedTitle(title);
    }

    public String getTitle() {
        return title;
    }

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
