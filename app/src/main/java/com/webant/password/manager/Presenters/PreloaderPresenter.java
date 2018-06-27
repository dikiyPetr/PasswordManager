package com.webant.password.manager.Presenters;

import com.webant.password.manager.Model.ModelCallback;
import com.webant.password.manager.Model.PreloaderModel;
import com.webant.password.manager.PreloaderActivity;

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
        connect();
    }

    public void connect() {
        model.CheckToken(new ModelCallback.CallbackNoError() {
            @Override
            public void onLoad() {
                view.startApplication(true);
            }

            @Override
            public void onFail() {
                view.startApplication(false);
            }

        }, view.getApplicationContext());
    }
}
