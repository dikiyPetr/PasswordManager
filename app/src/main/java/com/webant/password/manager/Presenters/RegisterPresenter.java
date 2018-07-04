package com.webant.password.manager.Presenters;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.webant.password.manager.ConverterErrorResponse;
import com.webant.password.manager.Model.LoginModel;
import com.webant.password.manager.Model.MainModel;
import com.webant.password.manager.Model.ModelCallback;
import com.webant.password.manager.RegisterActivity;

@SuppressLint("ParcelCreator")
public class RegisterPresenter implements Parcelable {
    private RegisterActivity view;
    private final LoginModel model;

    public RegisterPresenter(LoginModel model) {
        this.model = model;
    }

    public void attachView(RegisterActivity registerActivity) {
        view = registerActivity;
    }

    public void register(String email, String password) {
        model.PostNewUser(new ModelCallback.Callback() {
            @Override
            public void onLoad() {
                view.execute();
            }

            @Override
            public void onFail() {
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
                view.activityMode(false);
            }

            @Override
            public void onError(int code, String s) {

                view.setError(new ConverterErrorResponse(code, s).getRegisterMessage(view.getApplicationContext()));
                view.activityMode(false);
            }
        }, view.getApplicationContext(), email, password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
