package com.example.dikiy.passwordmain.Model;

import android.content.Context;
import android.os.AsyncTask;

import com.example.dikiy.passwordmain.Adapters.Get.GetService_Items_Commands;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Retrofit.ApiWorker;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;
import com.example.dikiy.passwordmain.Retrofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServiceCommandModel {
    public void CreateServiceCommand(final CreateServiceCommandCallback callback, Context context, String name, String service, String method, List<String> paramsBody, List<String> paramsUrl, String templateBody, String templateUrl) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("service", service);
        jsonObject.addProperty("method", method);
        JsonArray paramsBodyJson = new JsonParser().parse(new Gson().toJson(paramsBody)).getAsJsonArray();
        jsonObject.add("params_body", paramsBodyJson);
        JsonArray paramsUrlJson = new JsonParser().parse(new Gson().toJson(paramsUrl)).getAsJsonArray();
        jsonObject.add("params_url", paramsUrlJson);
        jsonObject.addProperty("template_body", templateBody);
        jsonObject.addProperty("template_url", templateUrl);

        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.AddServiceCommand(map, jsonObject).enqueue(new Callback<GetService_Items_Commands>() {
            @Override
            public void onResponse(Call<GetService_Items_Commands> call, Response<GetService_Items_Commands> response) {
                if (response.isSuccessful())
                    callback.onLoad();
                else
                    callback.onError();
            }

            @Override
            public void onFailure(Call<GetService_Items_Commands> call, Throwable t) {
                callback.onFail();
            }
        });

    }

    public void loadCommand(final LoadCommandCallback callback, Context context, String id) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.GetCommand(map, id).enqueue(new Callback<GetService_Items_Commands>() {
            @Override
            public void onResponse(Call<GetService_Items_Commands> call, Response<GetService_Items_Commands> response) {
                if (response.isSuccessful())
                    callback.onLoad(response.body());
                else
                    callback.onError();
            }

            @Override
            public void onFailure(Call<GetService_Items_Commands> call, Throwable t) {
                callback.onFail();
            }
        });
    }

    public void edit(final LoadCommandCallback callback, Context context, String id, String name, String method, String templateBody, String templateUrl) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("method", method);
        jsonObject.addProperty("template_body", templateBody);
        jsonObject.addProperty("template_url", templateUrl);
        postLogin.EditCommand(Integer.parseInt(id), map, jsonObject).enqueue(new Callback<GetService_Items_Commands>() {
            @Override
            public void onResponse(Call<GetService_Items_Commands> call, Response<GetService_Items_Commands> response) {
                if (response.isSuccessful())
                    callback.onLoad(response.body());
                else {
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<GetService_Items_Commands> call, Throwable t) {
                callback.onFail();
            }
        });
    }

    public interface LoadCommandCallback {
        void onLoad(GetService_Items_Commands item);

        void onFail();

        void onError();
    }

    public interface CreateServiceCommandCallback {
        void onLoad();

        void onFail();

        void onError();
    }
}
