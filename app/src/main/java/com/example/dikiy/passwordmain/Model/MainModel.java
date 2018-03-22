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
import com.example.dikiy.passwordmain.Retrofit.PostLogin;

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
    public void loadUsers(LoadUserCallback callback,int folderid) {
        LoadUsersTask loadUsersTask = new LoadUsersTask(callback);
        loadUsersTask.setId(folderid);
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
        private int task=0;
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
                    Log.v("steps1", String.valueOf(response.code()));
                    ArrayList<GetFolder_Item> getFolder_items=new ArrayList<>();
                    getFolder_items= (ArrayList<GetFolder_Item>) response.body().getItems();
                    dbWorker.setDataFolder(getFolder_items);
                    task++;
                    onPostExecute(true);
                }

                @Override
                public void onFailure(Call<GetFolder> call, Throwable t)
                {
                    Log.v("steps1",t.getLocalizedMessage());
                    task=task-5;
                    onPostExecute(true);
                }
            });
            postLogin.GetPass(map).enqueue(new Callback<GetPass>() {
                @Override
                public void onResponse(Call<GetPass> call, Response<GetPass> response) {
                    Log.v("steps1", String.valueOf(response.code()));
                    ArrayList<GetPass_Item> getPass_items=new ArrayList<>();
                    getPass_items= (ArrayList<GetPass_Item>) response.body().getItems();
                    dbWorker.setDataPass(getPass_items);
                    task++;
                    onPostExecute(true);
                }

                @Override
                public void onFailure(Call<GetPass> call, Throwable t) {
                    task=task-5;
                    onPostExecute(true);
                }
            });
        return false;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            if(b){
                if(task==2){
                    callback.onLoad(true);
                }else if (task<0){
                    callback.onLoad(false);
                }
            }
        }




    }




    class LoadUsersTask extends AsyncTask<Void, Void,  List<MainItem>> {
        private List<MainItem> passlist=new ArrayList<>();
        private List<MainItem> folderlist=new ArrayList<>();
        private int stat=0;
        private final LoadUserCallback callback;
        private int id=0;
        LoadUsersTask(LoadUserCallback callback) {
            this.callback = callback;
        }

        public void setId(int id){this.id=id;
        }
        @Override
        protected List<MainItem> doInBackground(Void... params) {
        List<MainItem> list = new ArrayList<>();
        DBWorker dbWorker = new DBWorker();
        list.addAll(dbWorker.loadData(id));

            Log.v("LoadLog", String.valueOf(list.size()));


        return list; }


        @Override
        protected void onPostExecute(List<MainItem> users) {
            callback.onLoad(users);
        }



    }
}
