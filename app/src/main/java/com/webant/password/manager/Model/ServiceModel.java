package com.webant.password.manager.Model;

import android.content.Context;


import com.webant.password.manager.Adapters.Get.GetService;
import com.webant.password.manager.Adapters.Get.GetService_Items;
import com.webant.password.manager.DBase.LoadText;
import com.webant.password.manager.Retrofit.PostLogin;
import com.webant.password.manager.Retrofit.RetrofitClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceModel {
    public void LoadService(final LoadServiceCallback callback, Context context) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));

        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.GetServices(map).enqueue(new Callback<GetService>() {
            @Override
            public void onResponse(Call<GetService> call, Response<GetService> response) {
                if (response.isSuccessful())
                    callback.onLoad(response.body().getItems());
                else {
                    try {
                        callback.onError(response.code(), response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetService> call, Throwable t) {
                callback.onFail();
            }
        });

    }

    public void deleteService(final ModelCallback.Callback callback, Context context, int id) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));

        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.DeleteService(id, map).enqueue(new Callback<GetService_Items>() {
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

    public interface LoadServiceCallback {
        void onLoad(List<GetService_Items> list);

        void onFail();

        void onError(int code, String message);
    }

}
