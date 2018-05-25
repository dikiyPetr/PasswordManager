package com.example.dikiy.passwordmain.Presenters;

import com.example.dikiy.passwordmain.LoginActivity;
import com.example.dikiy.passwordmain.Model.LoginModel;
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
    public void login(String name,String pass) {
        loginStat(name,pass);
    }

    private void loginStat(String name,String pass) {
                model.Login(new LoginModel.LoginCallback() {

                    @Override
                    public void onLoad() {
                            view.loginStat();
                    }

                    @Override
                    public void onFail() {
                                view.fail();
                    }

                    @Override
                    public void onError(int code) {
                                view.error(code);
                    }
                },name,pass);

    }

    public void register(String name, String login, String pass) {
        model.Register(new LoginModel.RegisterCallback() {
            @Override
            public void onLoad() {

                    view.rigisterOk();
            }

            @Override
            public void onFail() {
                    view.fail();
            }

            @Override
            public void onError(int code) {

                    view.error(code);

            }
        },name,login,pass);
    }
}
