package com.example.dikiy.passwordmain.Model;

import android.content.Context;

import com.example.dikiy.passwordmain.Adapters.Post.PostAdapter;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;
import com.example.dikiy.passwordmain.Retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dikiy on 14.03.2018.
 */

public class PreloaderModel {
    public void CheckToken(final PreloaderModel.CheckLoadCallback callback, final Context context) {
        if (LoadText.getText(context, "access_token").length() > 5) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("client_id", LoadText.getText(context, "client_id"));
            jsonObject.addProperty("grant_type", "refresh_token");
            jsonObject.addProperty("refresh_token", LoadText.getText(context, "refresh_token"));
            jsonObject.addProperty("client_secret", LoadText.getText(context, "client_secret"));
            PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
            postLogin.Login(jsonObject).enqueue(new Callback<PostAdapter>() {
                @Override
                public void onResponse(Call<PostAdapter> call, Response<PostAdapter> response) {
                    if (response.isSuccessful()) {
                        LoadText.refreshToken(context, response.body());
                        callback.onLoad();
                    } else {
                        callback.onFail();
                    }
                }

                @Override
                public void onFailure(Call<PostAdapter> call, Throwable t) {
                    callback.onFail();
                }
            });
        } else {
            callback.onFail();
        }
    }

    public interface CheckLoadCallback {
        void onLoad();

        void onFail();
    }
}
