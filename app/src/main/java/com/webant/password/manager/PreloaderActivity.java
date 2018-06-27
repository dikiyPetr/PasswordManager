package com.webant.password.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.webant.password.manager.DBase.LoadText;
import com.webant.password.manager.Model.PreloaderModel;
import com.webant.password.manager.Presenters.PreloaderPresenter;

/**
 * Created by dikiy on 09.03.2018.
 */

public class PreloaderActivity extends AppCompatActivity {
    private PreloaderPresenter presenter;
    private PreloaderModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preloader_activity);
        model = new PreloaderModel();
        presenter = new PreloaderPresenter(model);
        presenter.attachView(this);
        presenter.viewIsReady();
        overridePendingTransition(R.anim.close_activity_anim, R.anim.start_activity_anim);
    }

    public void startApplication(boolean state) {
        if (state) {
            startActivity(new Intent(this, LockActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
