package com.example.slava.lenta2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    public static final String KEY_PRESENTER = "presenter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        if (savedInstanceState == null)
            MainPresenter.createPresenter(getFragmentManager());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_PRESENTER, MainPresenter.getInstance());
    }
}
