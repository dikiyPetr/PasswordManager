package com.example.dikiy.passwordmain.Model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Get.GetService;
import com.example.dikiy.passwordmain.Adapters.Get.GetService_Items;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Retrofit.ApiWorker;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;
import com.example.dikiy.passwordmain.Retrofit.RetrofitClient;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServiceModel {
    public void createService(final CreateServiceCallback callback, Context context, String name, String url, String login, String pass, String token, String token_name) {
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
        postLogin.AddService(map,jsonObject).enqueue(new Callback<GetService_Items>() {
            @Override
            public void onResponse(Call<GetService_Items> call, Response<GetService_Items> response) {
                if(response.isSuccessful())
                    callback.onLoad();
                else
                    callback.onError();
            }

            @Override
            public void onFailure(Call<GetService_Items> call, Throwable t) {
                callback.onFail();
            }
        });
    }

    public interface CreateServiceCallback {
        void onLoad();

        void onFail();

        void onError();
    }
}
