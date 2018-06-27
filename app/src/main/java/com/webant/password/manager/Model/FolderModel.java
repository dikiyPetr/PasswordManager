package com.webant.password.manager.Model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.webant.password.manager.Adapters.Get.GetFolder_Item;
import com.webant.password.manager.Adapters.Get.GetPass_Item;
import com.webant.password.manager.Adapters.Get.GetTag_Item;
import com.webant.password.manager.Adapters.Post.PostAdapter;
import com.webant.password.manager.DBase.DBWorker;
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

/**
 * Created by dikiy on 25.03.2018.
 */

public class FolderModel {
    public void CreateFolder(final ModelCallback.Callback callback, Context context, final String name, final int way, List<String> tags) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        if (way != 0) {
            jsonObject.addProperty("parent", String.valueOf(way));
        }
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < tags.size(); i++) {
            jsonArray.add(tags.get(i).toString());
        }
        jsonObject.add("tags", jsonArray);
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.CreateFolder(map, jsonObject).enqueue(new Callback<PostAdapter>() {
            @Override
            public void onResponse(Call<PostAdapter> call, Response<PostAdapter> response) {
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
            public void onFailure(Call<PostAdapter> call, Throwable t) {
                callback.onFail();
            }
        });

    }

    public void update(final ModelCallback.Callback callback, Context context, int id, String name, List<String> tagsListEdit, List<Integer> tagsListEditMode) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.UpdateFolder(id, map, jsonObject).enqueue(new Callback<GetFolder_Item>() {
            @Override
            public void onResponse(Call<GetFolder_Item> call, Response<GetFolder_Item> response) {
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
            public void onFailure(Call<GetFolder_Item> call, Throwable t) {
                callback.onFail();
            }
        });
        Log.v("testLogTagsN", tagsListEdit.toString());
        Log.v("testLogTagsI", tagsListEditMode.toString());
        for (int i = 0; i < tagsListEdit.size(); i++) {
            Log.v("testLogTags", tagsListEdit.get(i) + " " + tagsListEditMode.get(i));
            if (tagsListEditMode.get(i) != 0) {
                jsonObject = new JsonObject();
                jsonObject.addProperty("tag", new DBWorker(context).getTagId(tagsListEdit.get(i)));
                if (tagsListEditMode.get(i) == -1) {
                    postLogin.DeleteTagInFolder(id, jsonObject, map).enqueue(new Callback<GetFolder_Item>() {
                        @Override
                        public void onResponse(Call<GetFolder_Item> call, Response<GetFolder_Item> response) {

                        }

                        @Override
                        public void onFailure(Call<GetFolder_Item> call, Throwable t) {

                        }
                    });
                } else if (tagsListEditMode.get(i) == 1)
                    postLogin.AddTagInFolder(id, jsonObject, map).enqueue(new Callback<GetFolder_Item>() {
                        @Override
                        public void onResponse(Call<GetFolder_Item> call, Response<GetFolder_Item> response) {

                        }

                        @Override
                        public void onFailure(Call<GetFolder_Item> call, Throwable t) {

                        }
                    });

            }
        }
    }

    public interface CreateFolderCallback {
        void onLoad();
    }

}
