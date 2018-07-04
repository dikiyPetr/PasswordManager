package com.webant.password.manager.Presenters;


import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.webant.password.manager.ConverterErrorResponse;
import com.webant.password.manager.LoginActivity;
import com.webant.password.manager.Model.LoginModel;
import com.webant.password.manager.Model.ModelCallback;

@SuppressLint("ParcelCreator")
public class LoginPresenter implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
