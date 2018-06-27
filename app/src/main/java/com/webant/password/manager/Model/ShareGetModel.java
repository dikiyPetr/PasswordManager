package com.webant.password.manager.Model;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.webant.password.manager.Adapters.Get.GetPass_Item;
import com.webant.password.manager.Adapters.Get.GetStorage;
import com.webant.password.manager.Adapters.Get.Users;
import com.webant.password.manager.Adapters.Model.ShareGetItems;
import com.webant.password.manager.AplicationListner;
import com.webant.password.manager.DBase.DBWorker;
import com.webant.password.manager.DBase.LoadText;
import com.webant.password.manager.Retrofit.PostLogin;
import com.webant.password.manager.Retrofit.RetrofitClient;
import com.webant.password.manager.crypto.NewAes;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareGetModel {
    public void getSharedPassword(final CallbackPassword callback, Context context, List<String> idList) {
        for (int i = 0; i < idList.size(); i++) {
            try {
                final int id = i;
                final Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
                PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
                postLogin.GetPass(idList.get(i), map).enqueue(new Callback<GetPass_Item>() {
                    @Override
                    public void onResponse(Call<GetPass_Item> call, Response<GetPass_Item> response) {
                        if (response.isSuccessful()) {
                            callback.onLoad(response.body(), id);
                        } else {
                            try {
                                if (response.errorBody() != null)
                                    callback.onError(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetPass_Item> call, Throwable t) {
                        callback.onError(t.getMessage());
                    }
                });
            } catch (Exception e) {
                Toast.makeText(context, "invalid id", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void passwordAdd(final ModelCallback.CallbackId callbackId, Context context, final ShareGetItems item, final int id) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pass", item.getId());
        jsonObject.addProperty("clue", NewAes.encrypt(item.getClue(), ((AplicationListner) context).getMasterPass()));
        if(item.getWay()!=0)
        jsonObject.addProperty("folder", item.getWay());
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.AddPasswordInStorage(map, jsonObject).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful())
                    callbackId.onLoad(id);
                else
                    callbackId.onError();

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                callbackId.onFail();
            }
        });
    }

    public void getMyPasswor(final ModelCallback.Callback callback, final Context context) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.GetPass(map).enqueue(new Callback<GetStorage>() {
            @Override
            public void onResponse(Call<GetStorage> call, Response<GetStorage> response) {
                if (response.isSuccessful()) {
                  new DBWorker(context).setDataPass(response.body());
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
            public void onFailure(Call<GetStorage> call, Throwable t) {
                callback.onFail();
            }
        });
    }

    public interface CallbackPassword {
        void onLoad(GetPass_Item item, int id);

        void onError(String s);
    }
}
