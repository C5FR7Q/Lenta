package com.example.slava.lenta2.presenter;


import android.app.Fragment;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.slava.lenta2.view.activity.IMainView;
import com.example.slava.lenta2.view.fragment.MainFragment;

/**
 * Created by slava on 06.09.2017.
 */

public
class MainActivityPresenter
		implements IMainActivityPresenter, Parcelable
{
	private static final String TITLE_MAIN = "Lenta";
	private IMainView mainView;
	private boolean isShowing;
	private String title;

	public
	MainActivityPresenter(final IMainView view) {
		mainView = view;
		replaceFragment(MainFragment.getInstance(this), false);
		isShowing = false;
		title = TITLE_MAIN;
	}

	private
	MainActivityPresenter(final Parcel in) {
		isShowing = in.readByte() != 0;
		title = in.readString();
	}

	public static final Creator<MainActivityPresenter> CREATOR = new Creator<MainActivityPresenter>()
	{
		@Override
		public
		MainActivityPresenter createFromParcel(final Parcel in) {
			return new MainActivityPresenter(in);
		}

		@Override
		public
		MainActivityPresenter[] newArray(final int size) {
			return new MainActivityPresenter[size];
		}
	};

	//    Fragments are ready
	@Override
	public
	void onResume() {

	}

	@Override
	public
	void onCreate(final IMainView view) {
		mainView = view;
		showHomeButton(isShowing, title);
	}

	@Override
	public
	void onDestroy() {
		mainView = null;
	}

	@Override
	public
	void onBackPressed() {
		mainView.showHomeButton(false, TITLE_MAIN);
	}

	@Override
	public
	void replaceFragment(final Fragment fragment, final boolean addToBackStack) {
		mainView.replaceFragment(fragment, addToBackStack);
	}

	@Override
	public
	void showProgressDialog() {
		mainView.showProgressDialog();
	}

	@Override
	public
	void hideProgressDialog() {
		mainView.hideProgressDialog();
	}

	@Override
	public
	void showHomeButton(final boolean isShowing, final String title) {
		mainView.showHomeButton(isShowing, title);
		this.isShowing = isShowing;
		this.title = title;
	}

	@Override
	public
	int describeContents() {
		return 0;
	}

	@Override
	public
	void writeToParcel(final Parcel parcel, final int i) {
		parcel.writeByte((byte) (isShowing ? 1 : 0));
		parcel.writeString(title);
	}
}
