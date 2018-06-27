package com.webant.password.manager.Model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.google.gson.JsonObject;
import com.webant.password.manager.Adapters.Get.GetService_Items;
import com.webant.password.manager.Adapters.Post.PostAdapter;
import com.webant.password.manager.DBase.LoadText;
import com.webant.password.manager.Retrofit.PostLogin;
import com.webant.password.manager.Retrofit.RetrofitClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServiceModel {
    public void createService(final ModelCallback.Callback callback, Context context, String name,
                              String url, String login, String pass, String token, String token_name) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("url", url);
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("pass", pass);
        jsonObject.addProperty("token", token);
        jsonObject.addProperty("token_name", token_name);
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.AddService(map, jsonObject).enqueue(new Callback<GetService_Items>() {
            @Override
            public void onResponse(Call<GetService_Items> call, Response<GetService_Items> response) {
                if (response.isSuccessful())
                    callback.onLoad();
                else {
                    try {
                        callback.onError(response.code(), response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetService_Items> call, Throwable t) {
                callback.onFail();
            }
        });
    }

    public void editService(final ModelCallback.Callback callback, Context context, int id, String name,
                            String url, String login, String pass, String token, String token_name) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("url", url);
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("pass", pass);
        jsonObject.addProperty("token", token);
        jsonObject.addProperty("token_name", token_name);
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.EditService(id, map, jsonObject).enqueue(new Callback<GetService_Items>() {
            @Override
            public void onResponse(Call<GetService_Items> call, Response<GetService_Items> response) {
                if (response.isSuccessful())
                    callback.onLoad();
                else {
                    try {
                        callback.onError(response.code(), response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetService_Items> call, Throwable t) {
callback.onFail();
            }
        });
    }

    public void loadService(final LoadServiceCallback callback, Context context, int id) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.GetService(id, map).enqueue(new Callback<GetService_Items>() {
            @Override
            public void onResponse(Call<GetService_Items> call, Response<GetService_Items> response) {
                if (response.isSuccessful())
                    callback.onLoad(response.body());
                else {
                    try {
                        callback.onError(response.code(), response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetService_Items> call, Throwable t) {
                callback.onFail();
            }
        });
    }

    public interface LoadServiceCallback {
        void onLoad(GetService_Items item);

        void onFail();

        void onError(int code, String s);
    }
}
