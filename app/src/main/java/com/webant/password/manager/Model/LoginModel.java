package com.webant.password.manager.Model;

import android.content.Context;
import android.util.Log;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.webant.password.manager.Adapters.Post.PostAdapter;
import com.webant.password.manager.Adapters.Post.PostRegister;
import com.webant.password.manager.DBase.LoadText;
import com.webant.password.manager.MyConstants;
import com.webant.password.manager.Retrofit.PostLogin;
import com.webant.password.manager.Retrofit.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel {
    public void Login(final ModelCallback.Callback callback, final Context context, String name, String pass) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("client_id", LoadText.getText(context, "client_id"));
        jsonObject.addProperty("username", name);
        jsonObject.addProperty("grant_type", "password");
        jsonObject.addProperty("password", pass);
        jsonObject.addProperty("client_secret", LoadText.getText(context, "client_secret"));
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.Login(jsonObject).enqueue(new Callback<PostAdapter>() {
            @Override
            public void onResponse(Call<PostAdapter> call, Response<PostAdapter> response) {
                if (response.isSuccessful()) {
                    LoadText.refreshToken(context, response.body());
                    callback.onLoad();
                } else {
                    try {
                        callback.onError(response.code(),response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<PostAdapter> call, Throwable t) {
                callback.onFail();
            }
        });
    }

    public void GetDeviceId(final ModelCallback.Callback callback, final Context context) {
        if (LoadText.getText(context, "client_id").isEmpty()) {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            jsonArray.add("password");
            jsonArray.add("refresh_token");
            jsonObject.addProperty("name", "");
            jsonObject.add("allowed_grant_types", jsonArray);
            PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
            postLogin.GetRandomId(jsonObject).enqueue(new Callback<PostAdapter>() {
                @Override
                public void onResponse(Call<PostAdapter> call, Response<PostAdapter> response) {
                    if (response.isSuccessful()) {
                        LoadText.newRandomId(context, response.body());
                        callback.onLoad();
                    } else {
                        try {
                            callback.onError(response.code(), response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PostAdapter> call, Throwable t) {
                    callback.onFail();
                }
            });
        } else {
            callback.onLoad();
        }
    }

    public void PostNewUser(final ModelCallback.Callback callback, final Context context, String email, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.Register(jsonObject).enqueue(new Callback<PostRegister>() {
            @Override
            public void onResponse(Call<PostRegister> call, Response<PostRegister> response) {
                if (response.isSuccessful()) {
                    callback.onLoad();
                } else {
                    try {
                        callback.onError(response.code(), response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostRegister> call, Throwable t) {
                callback.onFail();
            }
        });
    }
}
