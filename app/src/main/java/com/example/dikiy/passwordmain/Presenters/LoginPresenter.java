package com.example.dikiy.passwordmain.Presenters;

import com.example.dikiy.passwordmain.LoginActivity;
import com.example.dikiy.passwordmain.Model.LoginModel;
import com.example.dikiy.passwordmain.Model.ModelCallback;
import com.example.dikiy.passwordmain.Model.PreloaderModel;
import com.example.dikiy.passwordmain.PreloaderActivity;

/**
 * Created by dikiy on 14.03.2018.
 */

public class LoginPresenter {

    private LoginActivity view;
    private final LoginModel model;

    public LoginPresenter(LoginModel model) {
        this.model = model;
    }

    public void attachView(LoginActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }

    public void login(String name, String pass) {
      checkDeviceId(name,pass);
    }

    private void loginStat(String name, String pass) {
        model.Login(new ModelCallback.CallbackCheckField() {
            @Override
            public void onLoad() {
                view.login();
            }

            @Override
            public void onFail() {
                view.modelFail();
            }

            @Override
            public void onError() {
                view.modelError();
            }

            @Override
            public void onFieldError() {
                view.invalidLogin();
            }
        },view.getApplicationContext(), name, pass);

    }
    public void checkDeviceId(final String name, final String pass) {
        model.GetDeviceId(new ModelCallback.Callback() {
            @Override
            public void onLoad() {
                loginStat(name,pass);
            }

            @Override
            public void onFail() {
                view.modelFail();
            }

            @Override
            public void onError(String s) {
                view.modelError();
            }
        }, view.getApplicationContext());
    }

}
