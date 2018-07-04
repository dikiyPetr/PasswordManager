package com.webant.password.manager.Model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.google.gson.JsonObject;
import com.webant.password.manager.Adapters.Get.GetFolder;
import com.webant.password.manager.Adapters.Get.GetFolder_Item;
import com.webant.password.manager.Adapters.Get.GetPass_Item;
import com.webant.password.manager.Adapters.Get.GetStorage;
import com.webant.password.manager.Adapters.Get.GetTag;
import com.webant.password.manager.Adapters.Get.Users;
import com.webant.password.manager.Adapters.Model.CutItem;
import com.webant.password.manager.Adapters.Model.DeleteItems;
import com.webant.password.manager.Adapters.Model.SelectedShareItems;
import com.webant.password.manager.Adapters.Post.PostAdapter;
import com.webant.password.manager.Adapters.Post.PostRegister;
import com.webant.password.manager.DBase.DBWorker;
import com.webant.password.manager.DBase.LoadText;
import com.webant.password.manager.ItemModel.MainItem;
import com.webant.password.manager.Retrofit.PostLogin;
import com.webant.password.manager.Retrofit.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModel {
    public void moveItem(final MoveItemCallback callback, Context context, final List<CutItem> cutItems, final int folderId) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        for (int i = 0; i < cutItems.size(); i++) {
            if (!cutItems.get(i).getType()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("folder", folderId);
                final int finalI = i;
                postLogin.MovePass(cutItems.get(i).getStorageid(), map, jsonObject).enqueue(new Callback<GetPass_Item>() {
                    @Override
                    public void onResponse(Call<GetPass_Item> call, Response<GetPass_Item> response) {
                        if(cutItems.size()== finalI -1);
                        callback.onLoad();
                    }

                    @Override
                    public void onFailure(Call<GetPass_Item> call, Throwable t) {
                        if(cutItems.size()== finalI -1);
                        callback.onLoad();
                    }
                });
            } else {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("parent", folderId);
                final int finalI1 = i;
                postLogin.MoveFolder(cutItems.get(i).getId(), map, jsonObject).enqueue(new Callback<GetFolder_Item>() {
                    @Override
                    public void onResponse(Call<GetFolder_Item> call, Response<GetFolder_Item> response) {
                        if(cutItems.size()== finalI1 -1);
                        callback.onLoad();
                    }

                    @Override
                    public void onFailure(Call<GetFolder_Item> call, Throwable t) {
                        if(cutItems.size()== finalI1 -1);
                        callback.onLoad();
                    }
                });

            }
        }
    }

    int task = 0;

    public void refreshBd(final ModelCallback.Callback callback, Context context) {
        Map<String, String> map = new HashMap<>();
        final DBWorker dbWorker = new DBWorker(context);
        task = 0;
        map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        postLogin.GetFolder(map).enqueue(new Callback<GetFolder>() {
            @Override
            public void onResponse(Call<GetFolder> call, Response<GetFolder> response) {
                task++;
                ArrayList<GetFolder_Item> getFolder_items = new ArrayList<>();
                if (response.isSuccessful()) {
                    getFolder_items = (ArrayList<GetFolder_Item>) response.body().getItems();
                    dbWorker.setDataFolder(getFolder_items);
                    if (task == 4)
                        callback.onLoad();
                } else {
                    if (task == 4) {
                        try {
                            callback.onError(response.code(), response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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
                task++;
                if (response.isSuccessful()) {
                    dbWorker.setDataPass(response.body());
                    if (task == 4)
                        callback.onLoad();
                } else {
                    if (task == 4) {
                        try {
                            callback.onError(response.code(), response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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
                    if (task == 4) {
                        try {
                            callback.onError(response.code(), response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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
                    if (task == 4) {
                        try {
                            callback.onError(response.code(), response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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

    public void deleteItem(final ModelCallback.Callback callback, final Context context, final List<DeleteItems> items) {
        for (int i = 0; i < items.size(); i++) {
            boolean mode = items.get(i).getMode();
            final int item = items.get(i).getId();
            if (mode) {
                //delete folder
                final Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
                PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
                final int finalI = i;
                postLogin.DeleteFolder(item, map).enqueue(new Callback<PostAdapter>() {

                    @Override
                    public void onResponse(Call<PostAdapter> call, Response<PostAdapter> response) {
                        if (response.isSuccessful()) {
                            DBWorker dbWorker = new DBWorker(context);
                            dbWorker.deleteFolder(item);
                            if (finalI == items.size() - 1)
                                callback.onLoad();
                        } else if (finalI == items.size() - 1) {
                            try {
                                callback.onError(response.code(), response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<PostAdapter> call, Throwable t) {
                        if (finalI == items.size() - 1)
                            callback.onFail();
                    }
                });


            } else {
                final Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
                PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
                final int finalI1 = i;
                postLogin.DeletePass(item, map).enqueue(new Callback<PostAdapter>() {
                    @Override
                    public void onResponse(Call<PostAdapter> call, Response<PostAdapter> response) {
                        if (response.isSuccessful()) {
                            DBWorker dbWorker = new DBWorker(context);
                            dbWorker.deletePass(item);
                            if (finalI1 == items.size() - 1)
                                callback.onLoad();
                        } else if (finalI1 == items.size() - 1) {
                            try {
                                callback.onError(response.code(), response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PostAdapter> call, Throwable t) {
                        if (finalI1 == items.size() - 1)
                            callback.onFail();

                    }
                });


            }
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
                    LoadText.setText(context, "username", response.body().getUsername());
                    LoadText.setText(context, "email", response.body().getEmail());
                    callback.onLoad();
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

    public void sendPassToUser(final ModelCallback.Callback callback, Context context, final List<SelectedShareItems> list, String mail) {
        final int[] taskId = {0};
        for (int i = 0; i < list.size(); i++) {
            final Map<String, String> map = new HashMap<>();
            map.put("Authorization", "Bearer " + LoadText.getText(context, "access_token"));
            PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("user", mail);
            Log.v("testLogSend", i + " " + list.get(i).getId() + " " + list.get(i).getKey());
            postLogin.SendPass(map, jsonObject, list.get(i).getId()).enqueue(new Callback<PostRegister>() {
                @Override
                public void onResponse(Call<PostRegister> call, Response<PostRegister> response) {
                    taskId[0]++;
                    if (taskId[0] == list.size())
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
                    taskId[0]++;
                    if (taskId[0] == list.size())
                        callback.onFail();
                }
            });
        }
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

        void onError(boolean mode);
    }
}
