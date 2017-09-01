package com.example.slava.lenta2;

import android.app.FragmentManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by slava on 31.08.2017.
 */

public class MainPresenter implements Parcelable{
    interface ActVis{
        void setVisibility(int i, int visibility);
    }

    private static ActVis actVis;
    private static final String TAG = "MainPresenter";
    private static MainPresenter mainPresenter;
    private static OneFragment[] fragments = new OneFragment[3];
    private static FPresenter[] presenters = new FPresenter[3];
    private static int vis[] = new int[]{View.VISIBLE, View.VISIBLE, View.VISIBLE};
    private static FragmentManager fragmentManager;
    private static String selectedTitle;

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

    public static void makeRightOrder(){
        fragmentManager.beginTransaction()
                .add(R.id.cnt1, fragments[0])
                .add(R.id.cnt2, fragments[1])
                .add(R.id.cnt3, fragments[2])
                .commit();
    }

    public static void setVisibility(int i, int visibility){
        actVis.setVisibility(i, visibility);
        vis[i] = visibility;
    }


    public static boolean shouldFinish() {
        boolean selected = false;
        for (int i = 0; i < 3; i++){
            if (vis[i] == View.GONE){
                selected = true;
                break;
            }
        }
        if (selected) {
            showAll();
            return false;
        }

        return true;

    }

    private static void showAll() {
        for (int i = 0; i < 3; i++) {
            setVisibility(i, View.VISIBLE);
            presenters[i].setCutSize();
        }

    }

    public static void update(ActVis actVis){
        MainPresenter.actVis = actVis;
        for (int i = 0; i < 3; i++){
            setVisibility(i, vis[i]);
        }
    }

    public static void putFragPresenter(FPresenter presenter, String title){
        if (title.equals(OneFragment.VAL_HOTTEST)) presenters[0] = presenter;
        if (title.equals(OneFragment.VAL_NEWEST)) presenters[1] = presenter;
        if (title.equals(OneFragment.VAL_ALL)) presenters[2] = presenter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(selectedTitle);
    }
}
