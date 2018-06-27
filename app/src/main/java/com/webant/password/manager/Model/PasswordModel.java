package com.webant.password.manager.Model;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.webant.password.manager.Adapters.Get.GetCommand;
import com.webant.password.manager.Adapters.Get.GetPass_Item;
import com.webant.password.manager.Adapters.Get.GetService;
import com.webant.password.manager.Adapters.Model.ParamsItem;
import com.webant.password.manager.Adapters.Post.PostAdapter;
import com.webant.password.manager.Adapters.Post.PostRegister;
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

/**
 * Created by dikiy on 16.02.2018.
 */

public class PasswordModel {

    public void givePass(final ModelCallback.Callback callback, Context context, int id, String email) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user", email);
        postLogin.SendPass(map, jsonObject, id).enqueue(new Callback<PostRegister>() {
            @Override
            public void onResponse(Call<PostRegister> call, Response<PostRegister> response) {
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
            public void onFailure(Call<PostRegister> call, Throwable t) {
                callback.onFail();
            }
        });
    }

    public void UpdatePass(final ModelCallback.Callback callback, final Context context, final int id, final String name, final String url, final String login, final String password, List<String> tagsListEdit, List<Integer> tagsListEditMode) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("url", url);
        jsonObject.addProperty("pass", password);
        jsonObject.addProperty("login", login);


        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.UpdatePass(id, map, jsonObject).enqueue(new Callback<GetPass_Item>() {
            @Override
            public void onResponse(Call<GetPass_Item> call, Response<GetPass_Item> response) {
                if (response.isSuccessful()) {
                    callback.onLoad();
                    DBWorker dbWorker = new DBWorker(context);
                    dbWorker.updatePass(id, name, url, login, password);
                } else {
                    try {
                        callback.onError(response.code(),response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetPass_Item> call, Throwable t) {
                callback.onFail();
            }
        });
        for (int i = 0; i < tagsListEdit.size(); i++) {
            if (tagsListEditMode.get(i) != 0) {
                jsonObject = new JsonObject();
                jsonObject.addProperty("tag", new DBWorker(context).getTagId(tagsListEdit.get(i)));
                if (tagsListEditMode.get(i) == -1) {
                    postLogin.DeleteTagInPass(id, jsonObject, map).enqueue(new Callback<GetPass_Item>() {
                        @Override
                        public void onResponse(Call<GetPass_Item> call, Response<GetPass_Item> response) {

                        }

                        @Override
                        public void onFailure(Call<GetPass_Item> call, Throwable t) {

                        }
                    });
                } else if (tagsListEditMode.get(i) == 1)
                    postLogin.AddTagInPass(id, jsonObject, map).enqueue(new Callback<GetPass_Item>() {
                        @Override
                        public void onResponse(Call<GetPass_Item> call, Response<GetPass_Item> response) {

                        }

                        @Override
                        public void onFailure(Call<GetPass_Item> call, Throwable t) {

                        }
                    });

            }
        }
    }

    public interface UpdatePassCallback {
        void onLoad();

        void onError(String s);
    }


    public void CreatePass(final CreatePassCallback callback, Context context, String name, String folder, String url, String pass, String login, String description, List<String> tags, List<String> groups) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        if (!folder.equals("0"))
            jsonObject.addProperty("folder", String.valueOf(folder));
        String key = NewAes.generatorKey();
        String passKey = NewAes.encrypt(pass, key);
        key = NewAes.encrypt(key, ((AplicationListner) context).getMasterPass());
        jsonObject.addProperty("url", url);
        jsonObject.addProperty("pass", passKey);
        jsonObject.addProperty("secretKey", key);
        jsonObject.addProperty("login", login);
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < tags.size(); i++) {
            jsonArray.add(tags.get(i).toString());
        }
        jsonObject.add("tags", jsonArray);
        JsonArray jsonArray1 = new JsonArray();
        for (int i = 0; i < groups.size(); i++) {
            jsonArray1.add(groups.get(i).toString());
        }
        jsonObject.add("groups", jsonArray1);
        jsonObject.addProperty("description", description);
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.CreatePass(map, jsonObject).enqueue(new Callback<GetPass_Item>() {
            @Override
            public void onResponse(Call<GetPass_Item> call, Response<GetPass_Item> response) {
                if (response.isSuccessful())
                    callback.onLoad(response.body().getId());
                else {
                    try {
                        callback.onError(response.code(),response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetPass_Item> call, Throwable t) {
                callback.onFail();
            }
        });
    }

    public interface CreatePassCallback {
        void onLoad(int id);
        void onError(int code,String message);
        void onFail();
    }

    public void LoadService(final ServiceModel.LoadServiceCallback callback, Context context) {
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
                        callback.onError(response.code(),response.errorBody().string());
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

    public void LoadCommand(final ServiceCommandModel.LoadCommandCallback callback, Context context, String id) {
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

    public void setParams(final ModelCallback.CallbackNoError callback, Context context, int commandId, int passId, List<ParamsItem> url, List<ParamsItem> body) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pass", passId);
        JsonArray urlArray = new JsonArray();
        for (int i = 0; i < url.size(); i++) {
            urlArray.add(url.get(i).getValue());
        }
        jsonObject.add("url", urlArray);
        JsonArray bodyArray = new JsonArray();
        for (int i = 0; i < body.size(); i++) {
            bodyArray.add(body.get(i).getValue());
        }
        jsonObject.add("body", bodyArray);
        jsonObject.addProperty("command", commandId);
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.SetParams(map, jsonObject).enqueue(new Callback<PostAdapter>() {
            @Override
            public void onResponse(Call<PostAdapter> call, Response<PostAdapter> response) {
                if (response.isSuccessful())
                    callback.onLoad();
                else
                    callback.onFail();
            }

            @Override
            public void onFailure(Call<PostAdapter> call, Throwable t) {
                callback.onFail();
            }
        });

    }
}
