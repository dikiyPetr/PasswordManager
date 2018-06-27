package com.webant.password.manager.Model;

import android.content.Context;


import com.webant.password.manager.Adapters.Get.GetCommand;
import com.webant.password.manager.Adapters.Get.GetService_Items_Commands;
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

public class ServiceCommandModel {
    public void LoadCommand(final LoadCommandCallback callback, Context context,String id) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));

        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.GetCommands(map, Integer.parseInt(id)).enqueue(new Callback<GetCommand>() {
            @Override
            public void onResponse(Call<GetCommand> call, Response<GetCommand> response) {
                if (response.isSuccessful())
                    callback.onLoad(response.body().getItems());
                else {
                    try {
                        callback.onError(response.code(),response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCommand> call, Throwable t) {
                callback.onFail();
            }
        });

    }

    public interface LoadCommandCallback {
        void onLoad(List<GetService_Items_Commands> list);

        void onFail();

        void onError(int code,String message);
    }
    public void deleteCommand(final ModelCallback.Callback callback, Context context, int id) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));

        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.DeleteCommand(id,map).enqueue(new Callback<GetService_Items_Commands>() {
            @Override
            public void onResponse(Call<GetService_Items_Commands> call, Response<GetService_Items_Commands> response) {
                if (response.isSuccessful())
                    callback.onLoad();
                else {
                    try {
                        callback.onError(response.code(),response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetService_Items_Commands> call, Throwable t) {
                callback.onFail();
            }
        });
    }
}
