package com.example.dikiy.passwordmain.Model;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Get.GetPass_Item;
import com.example.dikiy.passwordmain.Adapters.Get.GetTag_Item;
import com.example.dikiy.passwordmain.Adapters.Post.PostAdapter;
import com.example.dikiy.passwordmain.Adapters.Post.PostRegister;
import com.example.dikiy.passwordmain.DBase.DBWorker;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;
import com.example.dikiy.passwordmain.Retrofit.RetrofitClient;
import com.example.dikiy.passwordmain.crypto.Aes256Class;
import com.example.dikiy.passwordmain.crypto.Rsa256Class;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
            postLogin.DeleteTagInPass(id, jsonObject, map).enqueue(new Callback<GetPass_Item>() {
                @Override
                public void onResponse(Call<GetPass_Item> call, Response<GetPass_Item> response) {

                }

                @Override
                public void onFailure(Call<GetPass_Item> call, Throwable t) {

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
            postLogin.DeleteGroupInPass(id, jsonObject, map).enqueue(new Callback<GetPass_Item>() {
                @Override
                public void onResponse(Call<GetPass_Item> call, Response<GetPass_Item> response) {

                }

                @Override
                public void onFailure(Call<GetPass_Item> call, Throwable t) {

                }
            });

        }
    }

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
                        callback.onError(response.errorBody().string());
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

    public interface RemoveTagCallback {
        void onLoad();
    }


    public void AddTagOrGroup(AddTagCallback callback, final Context context, boolean type, String s, final int passid) {
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
                        if (response.isSuccessful())
                            addTagInPass(context, passid, response.body().getId());
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
                        if (response.isSuccessful())
                            addGroupInPass(context, passid, response.body().getId());
                    }

                    @Override
                    public void onFailure(Call<GetTag_Item> call, Throwable t) {

                    }
                });

            }

        }
    }

    private void addGroupInPass(Context context, int idpass, int idGroup) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("group", idGroup);
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.AddGroupInPass(idpass, map, jsonObject).enqueue(new Callback<GetPass_Item>() {
            @Override
            public void onResponse(Call<GetPass_Item> call, Response<GetPass_Item> response) {

            }

            @Override
            public void onFailure(Call<GetPass_Item> call, Throwable t) {

            }
        });
    }

    private void addTagInPass(Context context, int idpass, int idTag) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("tag", String.valueOf(idTag));
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.AddTagInPass(idpass, map, jsonObject).enqueue(new Callback<GetPass_Item>() {
            @Override
            public void onResponse(Call<GetPass_Item> call, Response<GetPass_Item> response) {

            }

            @Override
            public void onFailure(Call<GetPass_Item> call, Throwable t) {

            }
        });
    }

    public interface AddTagCallback {
        void onLoad(String s);
    }

    public void UpdatePass(final UpdatePassCallback callback, final Context context, final int id, final String name, final String url, final String login, final String password) {
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
    }

    public interface UpdatePassCallback {
        void onLoad();

        void onError(String s);
    }


    public interface LoadUserCallback {
        void onLoad(String pass);

        void onError(String s);
    }

    public void CreatePass(final CreatePassCallback callback, Context context, String name, String folder, String url, String pass, String login, String description, List<String> tags, List<String> groups) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        if (!folder.equals("0"))
            jsonObject.addProperty("folder", String.valueOf(folder));
        String key = Aes256Class.generatorKey();
        Log.v("testLog_key", key);
        String passKey = Aes256Class.encode(pass, key, true);
        Log.v("testLog_pass+key", passKey);
        key = Aes256Class.encode(key, LoadText.getMasterPass(context), false);
        Log.v("testLog_key+master_Pass", key);
        Log.v("testLog_decode_key", Aes256Class.decode(key, LoadText.getMasterPass(context)) + "123");
        Log.v("testLog_decode_pass", Aes256Class.decode(passKey, Aes256Class.decode(key, LoadText.getMasterPass(context))));
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
        postLogin.CreatePass(map, jsonObject).enqueue(new Callback<PostAdapter>() {
            @Override
            public void onResponse(Call<PostAdapter> call, Response<PostAdapter> response) {
                if (response.isSuccessful())
                    callback.onLoad();
                else {
                    try {
                        callback.onFail(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostAdapter> call, Throwable t) {
                callback.onFail(t.getMessage());
            }
        });
    }

    public interface CreatePassCallback {
        void onLoad();

        void onFail(String s);
    }


}
