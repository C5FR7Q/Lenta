package com.example.slava.lenta2.views.activity.view;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.slava.lenta2.R;
import com.example.slava.lenta2.views.activity.presenter.IMainPresenter;
import com.example.slava.lenta2.views.activity.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements IMainView {
    private IMainPresenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this.getApplicationContext());
        progressDialog.setMessage("Loading");
        presenter = new MainPresenter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        presenter.onBackPressed();
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showHomeButton(boolean isShowing, String title) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShowing);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void replaceFragment(Fragment fragment){
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

}
