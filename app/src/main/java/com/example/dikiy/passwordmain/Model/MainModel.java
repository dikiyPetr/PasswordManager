package com.example.dikiy.passwordmain.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Get.GetFolder;
import com.example.dikiy.passwordmain.Adapters.Get.GetFolder_Item;
import com.example.dikiy.passwordmain.Adapters.Get.GetPass;
import com.example.dikiy.passwordmain.Adapters.Get.GetPass_Item;
import com.example.dikiy.passwordmain.DBase.DBWorker;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.ItemModel.MainItem;
import com.example.dikiy.passwordmain.Retrofit.ApiUtils;
import com.example.dikiy.passwordmain.Retrofit.ApiWorker;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;

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
    public void loadUsers(LoadUserCallback callback) {
        LoadUsersTask loadUsersTask = new LoadUsersTask(callback);
        loadUsersTask.execute();
    }
    public void refreshBd(RefreshBDCallback callback) {
        RefreshBDTask refreshBDCallback = new RefreshBDTask(callback);
        refreshBDCallback.execute();

    }

    public interface LoadUserCallback {
        void onLoad(List<MainItem> users);
    }


    public interface RefreshBDCallback {
        void onLoad(Boolean b);
    }



    class RefreshBDTask extends AsyncTask<Void, Void, Boolean>{
        private final RefreshBDCallback callback;

        RefreshBDTask(RefreshBDCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Map<String, String> map = new HashMap<>();
            final DBWorker dbWorker = new DBWorker();

            map.put("Authorization", "Bearer "+LoadText.getText("access_token"));
            PostLogin postLogin = ApiUtils.getAPIService();
            postLogin.GetFolder(map).enqueue(new Callback<GetFolder>() {
                @Override
                public void onResponse(Call<GetFolder> call, Response<GetFolder> response) {
                    List<MainItem> items=new ArrayList<>();
                    ArrayList<GetFolder_Item> getFolder_items =new ArrayList<GetFolder_Item>();
                    getFolder_items.addAll(response.body().getItems());

                    dbWorker.setData(getFolder_items,true);
                }

                @Override
                public void onFailure(Call<GetFolder> call, Throwable t) {
                    Log.v("steps","!f");
                }
            });
            postLogin.GetPass(map).enqueue(new Callback<GetPass>() {
                @Override
                public void onResponse(Call<GetPass> call, Response<GetPass> response) {
                    List<MainItem> items=new ArrayList<>();
                    ArrayList<GetPass_Item> getFolder_items =new ArrayList<GetPass_Item>();
                    getFolder_items.addAll(response.body().getItems());
                    for(int i=0;i<getFolder_items.size();i++){
                        items.add(new MainItem(getFolder_items.get(i).getName(),getFolder_items.get(i).getId(),false));
                    }
                    dbWorker.setData(items,false);
                }

                @Override
                public void onFailure(Call<GetPass> call, Throwable t) {
                    Log.v("steps",t.getLocalizedMessage());
                }
            });


            return false;
        }

        @Override
        protected void onPostExecute(Boolean b ) {
            if (callback != null) {
                callback.onLoad(b);
            }
        }


    }




    class LoadUsersTask extends AsyncTask<Void, Void,  List<MainItem>> {
        private List<MainItem> passlist=new ArrayList<>();
        private List<MainItem> folderlist=new ArrayList<>();
        private int stat=0;
        private final LoadUserCallback callback;
        private int id;
        LoadUsersTask(LoadUserCallback callback) {
            this.callback = callback;
        }
        protected void setId(int id){
            this.id=id;
        }
        @Override
        protected List<MainItem> doInBackground(Void... params) {

        return }


        @Override
        protected void onPostExecute(List<MainItem> users) {

            if (users != null) {
                Log.v("steps1", String.valueOf(users.size()));
                callback.onLoad(users);
            }else{
            super.onPreExecute();
        }}

        protected void loadList(List<MainItem> users,boolean type) {
                Log.v("steps","1");
                if (type) {
                    folderlist.addAll(users);
                    stat++;
                    Log.v("steps", String.valueOf(users.size()));
                } else {
                    passlist.addAll(users);
                    stat++;
                    Log.v("steps", String.valueOf(users.size()));
                }
                if(stat==2){
                    Log.v("steps", String.valueOf(users.size()));
                    List<MainItem> mainlist=new ArrayList<>();
                    Log.v("steps1", String.valueOf(mainlist.size()));
                    mainlist.addAll(folderlist);
                    Log.v("steps1", String.valueOf(mainlist.size()));
                    mainlist.addAll(passlist);
                    Log.v("steps1", String.valueOf(mainlist.size()));
                    onPostExecute(mainlist);
                }

        }

    }
}
