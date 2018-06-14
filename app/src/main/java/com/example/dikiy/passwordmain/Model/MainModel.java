package com.example.dikiy.passwordmain.Model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Get.GetFolder;
import com.example.dikiy.passwordmain.Adapters.Get.GetFolder_Item;
import com.example.dikiy.passwordmain.Adapters.Get.GetPass;
import com.example.dikiy.passwordmain.Adapters.Get.GetPass_Item;
import com.example.dikiy.passwordmain.Adapters.Get.GetStorage;
import com.example.dikiy.passwordmain.Adapters.Get.GetTag;
import com.example.dikiy.passwordmain.Adapters.Get.Users;
import com.example.dikiy.passwordmain.Adapters.Model.CutItem;
import com.example.dikiy.passwordmain.Adapters.Post.PostAdapter;
import com.example.dikiy.passwordmain.DBase.DBWorker;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.ItemModel.MainItem;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;
import com.example.dikiy.passwordmain.Retrofit.RetrofitClient;
import com.example.dikiy.passwordmain.crypto.Aes256Class;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dikiy on 16.02.2018.
 */

public class MainModel {
    public void moveItem(final MoveItemCallback callback, Context context, List<CutItem> cutItems, int folderId) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));

        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);


        for (int i = 0; i < cutItems.size(); i++) {
            if (!cutItems.get(i).getType()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("folder", folderId);

                postLogin.MovePass(cutItems.get(i).getId(), map, jsonObject).enqueue(new Callback<GetPass_Item>() {
                    @Override
                    public void onResponse(Call<GetPass_Item> call, Response<GetPass_Item> response) {
                        callback.onLoad();
                    }

                    @Override
                    public void onFailure(Call<GetPass_Item> call, Throwable t) {
                        callback.onLoad();
                    }
                });
            } else {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("parent", folderId);

                postLogin.MoveFolder(cutItems.get(i).getId(), map, jsonObject).enqueue(new Callback<GetFolder_Item>() {
                    @Override
                    public void onResponse(Call<GetFolder_Item> call, Response<GetFolder_Item> response) {
                        callback.onLoad();
                    }

                    @Override
                    public void onFailure(Call<GetFolder_Item> call, Throwable t) {
                        callback.onLoad();
                    }
                });

            }
        }
    }

    int task = 0;

    public void refreshBd(final RefreshBDCallback callback, Context context) {
        Map<String, String> map = new HashMap<>();
        final DBWorker dbWorker = new DBWorker(context);
        task = 0;
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.GetFolder(map).enqueue(new Callback<GetFolder>() {
            @Override
            public void onResponse(Call<GetFolder> call, Response<GetFolder> response) {
                task++;
                Log.v("steps1", String.valueOf(response.code()));
                ArrayList<GetFolder_Item> getFolder_items = new ArrayList<>();
                if (response.isSuccessful()) {
                    getFolder_items = (ArrayList<GetFolder_Item>) response.body().getItems();
                    dbWorker.setDataFolder(getFolder_items);
                    if (task == 4)
                        callback.onLoad();
                } else {
                    if (task == 4)
                        callback.onFail();
                }

            }

            @Override
            public void onFailure(Call<GetFolder> call, Throwable t) {
                task++;
                if (task == 4)
                    callback.onFail();

            }
        });
        postLogin.GetPass(map).enqueue(new Callback<GetStorage>() {
            @Override
            public void onResponse(Call<GetStorage> call, Response<GetStorage> response) {
                Log.v("steps1", String.valueOf(response.code()));
                task++;
                if (response.isSuccessful()) {
                    dbWorker.setDataPass(response.body());
                    if (task == 4)
                        callback.onLoad();
                } else {
                    if (task == 4)
                        callback.onFail();
                }

            }

            @Override
            public void onFailure(Call<GetStorage> call, Throwable t) {
                task++;
                if (task == 4)
                    callback.onFail();
            }
        });

        postLogin.GetGroup(map).enqueue(new Callback<GetTag>() {
            @Override
            public void onResponse(Call<GetTag> call, Response<GetTag> response) {
                task++;
                if (response.code() == 200) {
                    dbWorker.initGroup(response.body().getItems());
                    if (task == 4)
                        callback.onLoad();
                } else {
                    if (task == 4)
                        callback.onFail();
                }
            }

            @Override
            public void onFailure(Call<GetTag> call, Throwable t) {
                task++;
                if (task == 4)
                    callback.onFail();
            }
        });
        postLogin.GetTag(map).enqueue(new Callback<GetTag>() {
            @Override
            public void onResponse(Call<GetTag> call, Response<GetTag> response) {
                task++;
                if (response.code() == 200) {
                    dbWorker.initTag(response.body().getItems());
                    if (task == 4)
                        callback.onLoad();
                } else {
                    if (task == 4)
                        callback.onFail();
                }
            }

            @Override
            public void onFailure(Call<GetTag> call, Throwable t) {
                task++;
                if (task == 4)
                    callback.onFail();
            }
        });
    }

    public void deleteItem(final DeleteItemCallback callback, final Context context, final int item, boolean mode) {
        if (mode) {
            //delete folder
            final Map<String, String> map = new HashMap<>();
            map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));

            PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
            ;
            postLogin.DeleteFolder(item, map).enqueue(new Callback<PostAdapter>() {

                @Override
                public void onResponse(Call<PostAdapter> call, Response<PostAdapter> response) {
                    if (response.isSuccessful()) {
                        DBWorker dbWorker = new DBWorker(context);
                        dbWorker.deleteFolder(item);
                    }
                    callback.onLoad();
                }

                @Override
                public void onFailure(Call<PostAdapter> call, Throwable t) {
                    callback.onLoad();
                }
            });


        } else {
            //delete pass

            final Map<String, String> map = new HashMap<>();
            map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));

            PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
            postLogin.DeletePass(item, map).enqueue(new Callback<PostAdapter>() {

                @Override
                public void onResponse(Call<PostAdapter> call, Response<PostAdapter> response) {
                    if (response.isSuccessful()) {
                        DBWorker dbWorker = new DBWorker(context);
                        dbWorker.deletePass(item);
                    }
                    callback.onLoad();
                }

                @Override
                public void onFailure(Call<PostAdapter> call, Throwable t) {
                    callback.onLoad();
                }
            });


        }
    }

    public void GetUser(final RefreshBDCallback callback, final Context context) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.GetThisUser(map).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {
                    callback.onLoad();
                    LoadText.setText(context, "username", response.body().getUsername());
                    LoadText.setText(context, "email", response.body().getEmail());
                } else {
                    callback.onFail();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                callback.onFail();
            }
        });
    }

    public void addPasswordInStorage(final ModelCallback.Callback callback, Context context, String id, String clue) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pass", id);
        jsonObject.addProperty("clue", Aes256Class.encode(clue,LoadText.getMasterPass(context),false));
        postLogin.AddPasswordInStorage(map,jsonObject).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
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
            public void onFailure(Call<Users> call, Throwable t) {
                callback.onFail();
            }
        });
    }

    public interface MoveItemCallback {
        void onLoad();
    }

    public interface LoadUserCallback {
        void onLoad(List<MainItem> users);
    }

    public interface RefreshBDCallback {
        void onLoad();

        void onFail();
    }

    public interface DeleteItemCallback {
        void onLoad();
    }
}
