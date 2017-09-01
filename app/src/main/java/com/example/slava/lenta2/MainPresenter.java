package com.example.slava.lenta2;

import android.app.FragmentManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by slava on 31.08.2017.
 */

public class MainPresenter implements Parcelable{
//    Возможно, стоило использовать Map<String, OneFragment> вместо массива, где String -
//    одна из переменных типа OneFragment.VAL_...
    interface ActVis{
        void setVisibility(int i, int visibility);
    }

    private ActVis actVis;
    private static final String TAG = "MainPresenter";
    private static MainPresenter mainPresenter;
    private OneFragment[] fragments = new OneFragment[3];
    private FPresenter[] presenters = new FPresenter[3];
    private FragmentManager fragmentManager;
    private String selectedTitle;
    private boolean selected = false;

    private MainPresenter(FragmentManager fragmentManager, ActVis actVis) {
        this.fragmentManager = fragmentManager;

        fragments[0] = (OneFragment.newInstance(OneFragment.VAL_HOTTEST));
        fragments[1] = (OneFragment.newInstance(OneFragment.VAL_NEWEST));
        fragments[2] = (OneFragment.newInstance(OneFragment.VAL_ALL));

        presenters[0] = fragments[0].getPresenter();
        presenters[1] = fragments[1].getPresenter();
        presenters[2] = fragments[2].getPresenter();

        this.actVis = actVis;

        makeRightOrder();
    }

    protected MainPresenter(Parcel in) {
        selectedTitle = in.readString();
    }

    public static final Creator<MainPresenter> CREATOR = new Creator<MainPresenter>() {
        @Override
        public MainPresenter createFromParcel(Parcel in) {
            return new MainPresenter(in);
        }

        @Override
        public MainPresenter[] newArray(int size) {
            return new MainPresenter[size];
        }
    };

    public static void createPresenter(FragmentManager fragmentManager, ActVis actVis){
        if (mainPresenter == null)
            mainPresenter = new MainPresenter(fragmentManager, actVis);
    }

    public static MainPresenter getInstance(){
        return mainPresenter;
    }

    public void makeRightOrder(){
        fragmentManager.beginTransaction()
                .add(R.id.cnt1, fragments[0])
                .add(R.id.cnt2, fragments[1])
                .add(R.id.cnt3, fragments[2])
                .commit();
    }

    public void setVisibility(int i, int visibility){
        actVis.setVisibility(i, visibility);
    }


    public boolean shouldFinish() {
        if (selected) {
            showAll();
            selected = false;
            return false;
        }

        return true;

    }

    private void showAll() {
        for (int i = 0; i < 3; i++) {
            setVisibility(i, View.VISIBLE);

            presenters[i].setCutSize();
        }


    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setSelected() {
        this.selected = true;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(selectedTitle);
    }
}
