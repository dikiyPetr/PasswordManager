package com.example.dikiy.passwordmain.Model;

import android.content.Context;

import com.example.dikiy.passwordmain.Adapters.Get.GetCommand;
import com.example.dikiy.passwordmain.Adapters.Get.GetService_Items_Commands;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;
import com.example.dikiy.passwordmain.Retrofit.RetrofitClient;

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
                else
                    callback.onError();
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

        void onError();
    }

}
