package com.example.dikiy.passwordmain.Model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Get.GetFolder_Item;
import com.example.dikiy.passwordmain.Adapters.Get.GetPass_Item;
import com.example.dikiy.passwordmain.Adapters.Get.GetTag_Item;
import com.example.dikiy.passwordmain.Adapters.Post.PostAdapter;
import com.example.dikiy.passwordmain.DBase.DBWorker;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.ItemModel.MainItem;
import com.example.dikiy.passwordmain.Retrofit.ApiWorker;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;
import com.example.dikiy.passwordmain.Retrofit.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
    public void CreateFolder(final CreateFolderCallback callback, Context context, final String name, final int way, List<String> tags, List<String> groups) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText(context,"access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        if(way!=0) {
            jsonObject.addProperty("parent", String.valueOf(way));
        }
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
        PostLogin postLogin =  RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.CreateFolder(map,jsonObject).enqueue(new Callback<PostAdapter>() {
            @Override
            public void onResponse(Call<PostAdapter> call, Response<PostAdapter> response) {
                callback.onLoad();
            }

            @Override
            public void onFailure(Call<PostAdapter> call, Throwable t) {

            }
        });

    }

    public interface CreateFolderCallback {
        void onLoad();
    }

    public void RemoveTag(RemoveTagCallback callback, Context context, String s, int id, boolean b) {
        DBWorker dbWorker = new DBWorker(context);
        int itemId = 0;
        if (!b) {
            itemId = dbWorker.getTagId(s);
            final Map<String, String> map = new HashMap<>();
            map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("tag", String.valueOf(itemId));

            PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
            Log.v("asdasdasdxcaz", id + " " + itemId);
            postLogin.DeleteTagInFolder(id, jsonObject, map).enqueue(new Callback<GetFolder_Item>() {
                @Override
                public void onResponse(Call<GetFolder_Item> call, Response<GetFolder_Item> response) {

                }

                @Override
                public void onFailure(Call<GetFolder_Item> call, Throwable t) {

                }
            });
        } else {
            itemId = dbWorker.getGroupId(s);
            final Map<String, String> map = new HashMap<>();
            map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("group", String.valueOf(itemId));

            PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
            Log.v("asdasdasdxcaz", id + " " + itemId);
            postLogin.DeleteGroupInFolder(id, jsonObject, map).enqueue(new Callback<GetFolder_Item>() {
                @Override
                public void onResponse(Call<GetFolder_Item> call, Response<GetFolder_Item> response) {

                }

                @Override
                public void onFailure(Call<GetFolder_Item> call, Throwable t) {

                }
            });

        }
    }

    public interface RemoveTagCallback {
        void onLoad();
    }

    public void AddTagOrGroup(final AddTagCallback callback, final Context context, boolean type, String s, final int passid) {
        DBWorker dbWorker = new DBWorker(context);
        int itemId = 0;
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        final JsonObject jsonObject = new JsonObject();

        final PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        if (!type) {
            itemId = dbWorker.getTagId(s);
            jsonObject.addProperty("tag", String.valueOf(itemId));
            if (itemId != 0) {

                addTagInPass(context, passid, itemId);
            } else {
                jsonObject.addProperty("name", s);
                postLogin.AddTag(map, jsonObject).enqueue(new Callback<GetTag_Item>() {
                    @Override
                    public void onResponse(Call<GetTag_Item> call, Response<GetTag_Item> response) {
                        if(response.isSuccessful()){
                            addTagInPass(context, passid, response.body().getId());
                            callback.onLoad();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetTag_Item> call, Throwable t) {

                    }
                });
            }

        } else {
            itemId = dbWorker.getGroupId(s);
            if (itemId != 0) {
                jsonObject.addProperty("group", String.valueOf(itemId));
                addGroupInPass(context, passid, itemId);

            } else {
                jsonObject.addProperty("name", s);
                postLogin.AddGroup(map, jsonObject).enqueue(new Callback<GetTag_Item>() {
                    @Override
                    public void onResponse(Call<GetTag_Item> call, Response<GetTag_Item> response) {
                        if(response.isSuccessful()) {
                            addGroupInPass(context, passid, response.body().getId());
                            callback.onLoad();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetTag_Item> call, Throwable t) {

                    }
                });

            }

        }
    }
    private void addGroupInPass(final Context context, final int idpass, int idGroup) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("group", idGroup);
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.AddGroupInFolder(idpass, map, jsonObject).enqueue(new Callback<GetFolder_Item>() {
            @Override
            public void onResponse(Call<GetFolder_Item> call, Response<GetFolder_Item> response) {

            }

            @Override
            public void onFailure(Call<GetFolder_Item> call, Throwable t) {

            }
        });
    }

    private void addTagInPass(Context context, int idpass, int idTag) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("tag", String.valueOf(idTag));
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.AddTagInFolder(idpass, map, jsonObject).enqueue(new Callback<GetFolder_Item>() {
            @Override
            public void onResponse(Call<GetFolder_Item> call, Response<GetFolder_Item> response) {

            }

            @Override
            public void onFailure(Call<GetFolder_Item> call, Throwable t) {

            }
        });
    }

    public interface AddTagCallback {
        void onLoad();
    }

    public void UpdateFolder(final UpdateFolderCallback callback, Context context, int id, String name) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText(context,"access_token"));

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);



        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.UpdateFolder(id,map,jsonObject).enqueue(new Callback<GetFolder_Item>() {
            @Override
            public void onResponse(Call<GetFolder_Item> call, Response<GetFolder_Item> response) {
                callback.onLoad();
            }

            @Override
            public void onFailure(Call<GetFolder_Item> call, Throwable t) {

            }
        });
    }

    public interface UpdateFolderCallback {
        void onLoad();
    }
}
