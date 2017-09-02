package com.example.slava.lenta2;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements MainPresenter.MActToMPres {

    private LinearLayout[] cnt = new LinearLayout[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        init();

        if (savedInstanceState == null)
            MainPresenter.createPresenter(getFragmentManager(), this);
        else MainPresenter.update(this);
    }

    private void init() {
        cnt[0] = (LinearLayout)findViewById(R.id.cnt1);
        cnt[1] = (LinearLayout)findViewById(R.id.cnt2);
        cnt[2] = (LinearLayout)findViewById(R.id.cnt3);
    }

    @Override
    public void onBackPressed() {
        if (MainPresenter.shouldFinish())
            finish();
    }

    @Override
    public void setVisibility(int i, int visibility){
        cnt[i].setVisibility(visibility);
    }

    @Override
    public ActionBar supportActionBar() {
        return getSupportActionBar();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
