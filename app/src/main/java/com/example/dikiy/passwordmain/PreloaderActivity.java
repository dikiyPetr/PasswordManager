package com.example.dikiy.passwordmain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dikiy.passwordmain.Model.PasswordModel;
import com.example.dikiy.passwordmain.Model.PreloaderModel;
import com.example.dikiy.passwordmain.Presenters.MainPresenter;
import com.example.dikiy.passwordmain.Presenters.PasswordPresenter;
import com.example.dikiy.passwordmain.Presenters.PreloaderPresenter;

/**
 * Created by dikiy on 09.03.2018.
 */

public class PreloaderActivity extends AppCompatActivity {
    private PreloaderPresenter presenter;
    private PreloaderModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new PreloaderModel();
        presenter = new PreloaderPresenter(model);
        presenter.attachView(this);
        presenter.viewIsReady();

    }
    public void lockview(){
        Intent intent = new Intent(this, LockActivity.class);
        startActivity(intent);
        finish();
    }
    public void loginview(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
