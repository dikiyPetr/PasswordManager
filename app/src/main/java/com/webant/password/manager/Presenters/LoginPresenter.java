package com.webant.password.manager.Presenters;


import com.webant.password.manager.ConverterErrorResponse;
import com.webant.password.manager.LoginActivity;
import com.webant.password.manager.Model.LoginModel;
import com.webant.password.manager.Model.ModelCallback;

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
        checkDeviceId(name, pass);
    }

    private void loginStat(String name, String pass) {
        model.Login(new ModelCallback.Callback() {
            @Override
            public void onLoad() {
                view.login();
            }

            @Override
            public void onFail() {
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
                view.setEnable(true);

            }

            @Override
            public void onError(int code, String s) {
                view.setError(new ConverterErrorResponse(code, s).loginMessage(view.getApplicationContext()));
                view.setEnable(true);
            }
        }, view.getApplicationContext(), name, pass);

    }

    public void checkDeviceId(final String name, final String pass) {
        model.GetDeviceId(new ModelCallback.Callback() {
            @Override
            public void onLoad() {
                loginStat(name, pass);
            }

            @Override
            public void onFail() {
                view.setEnable(true);
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
            }

            @Override
            public void onError(int code, String s) {
                view.setEnable(true);
                new ConverterErrorResponse(code, s).sendMessage(view.getApplicationContext());
            }
        }, view.getApplicationContext());
    }

}
