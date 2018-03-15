package com.example.dikiy.passwordmain.Presenters;

import com.example.dikiy.passwordmain.Model.PasswordModel;
import com.example.dikiy.passwordmain.Model.PreloaderModel;
import com.example.dikiy.passwordmain.PasswordActivity;
import com.example.dikiy.passwordmain.PreloaderActivity;

/**
 * Created by dikiy on 14.03.2018.
 */

public class PreloaderPresenter {
    private PreloaderActivity view;
    private final PreloaderModel model;

    public PreloaderPresenter(PreloaderModel model) {
        this.model = model;
    }

    public void attachView(PreloaderActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }
    public void viewIsReady() {
        loadUsers();
    }

    private void loadUsers() {
        model.CheckToken(new PreloaderModel.CheckLoadCallback() {
            @Override
            public void onLoad(boolean stat) {
                if(stat){
                    view.lockview();
                }else {view.loginview();}
            }
        });
    }
}