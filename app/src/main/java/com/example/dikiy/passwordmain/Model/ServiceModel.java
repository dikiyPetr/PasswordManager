package com.example.dikiy.passwordmain.Model;

import android.content.Context;

import com.example.dikiy.passwordmain.Adapters.Get.GetService;
import com.example.dikiy.passwordmain.Adapters.Get.GetService_Items;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;
import com.example.dikiy.passwordmain.Retrofit.RetrofitClient;

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
                else
                    callback.onError();
            }

            @Override
            public void onFailure(Call<GetService> call, Throwable t) {
                callback.onFail();
            }
        });

    }

    public interface LoadServiceCallback {
        void onLoad(List<GetService_Items> list);

        void onFail();

        void onError();
    }

}
