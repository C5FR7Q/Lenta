package com.example.slava.lenta2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by slava on 31.08.2017.
 */

public class MainPresenter implements Parcelable{
//    Возможно, стоило использовать Map<String, OneFragment> вместо массива, где String -
//    одна из переменных типа OneFragment.VAL_...
    private static final String TAG = "MainPresenter";
    private static MainPresenter mainPresenter;
    private OneFragment[] fragments = new OneFragment[3];
    private FragmentManager fragmentManager;
    private String selectedTitle;

    private MainPresenter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;

        fragments[0] = (OneFragment.newInstance(OneFragment.VAL_HOTTEST));
        fragments[1] = (OneFragment.newInstance(OneFragment.VAL_NEWEST));
        fragments[2] = (OneFragment.newInstance(OneFragment.VAL_ALL));

        makeRightOrder(null);
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

    public static void createPresenter(FragmentManager fragmentManager){
        if (mainPresenter == null)
            mainPresenter = new MainPresenter(fragmentManager);
    }

    public static MainPresenter getInstance(){
        return mainPresenter;
    }

    public void makeRightOrder(@Nullable String title){
        FragmentTransaction transaction = null;
        boolean cleared = false;

        if (title != null)
            for (int i = 0; i < 3; i++)
                if (fragments[i].getTitle().equals(title)){
                    transaction = fragmentManager.beginTransaction().remove(fragments[i]);
                    cleared = true;
            }
        for (int i = 0; i < 3; i++) {
            if (!cleared) {
                transaction = fragmentManager.beginTransaction()
                        .add(R.id.content, fragments[i]);
                cleared = true;
            }
            else
                transaction.add(R.id.content, fragments[i]);
            Log.d(TAG, "makeRightOrder: ARARAR" + title);
        }

        transaction.commit();
    }

    public void setSelectedTitle(@Nullable String s) {
        selectedTitle = s;
    }


    public boolean shouldFinish() {
        if (selectedTitle != null) {
            makeRightOrder(selectedTitle);
            selectedTitle = null;
            return false;
        }

        return true;

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
