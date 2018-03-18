package com.example.dikiy.passwordmain.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Get.GetFolder;
import com.example.dikiy.passwordmain.Adapters.Get.GetFolder_Item;
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

            return false;
        }

        @Override
        protected void onPostExecute(Boolean b ) {
            if (callback != null) {
                callback.onLoad(b);
            }
        }


    }




    class LoadUsersTask extends AsyncTask<Void, Void, List<MainItem>> {

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
        ArrayList<MainItem> items = new ArrayList<>();
        DBWorker dbWorker=new DBWorker();
        items.addAll(dbWorker.loadData(1));
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));
        PostLogin postLogin = ApiUtils.getAPIService();
        Log.v("Steps","1");
        postLogin.GetFolder(map).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.v("Steps","2");
                ArrayList<MainItem> items = new ArrayList<>();
                if(response.code()==200) {
                    GetFolder getFolder;
                    getFolder = (GetFolder) response.body();
                    ArrayList<GetFolder_Item> getFolder_items = new ArrayList<>();
                    getFolder_items.add(getFolder.getItems().get(0));
                    items.add(new MainItem(getFolder_items.get(0).getName()));
                    Log.v("Steps","3");
                }
               onPostExecute(items);
            }
            @Override
                public void onFailure(Call call, Throwable t) {
                Log.v("Steps",t.getMessage());
            }
        });

        return null;
        }

        @Override
        protected void onPostExecute(List<MainItem> users) {
            Log.v("Steps", String.valueOf(users));
            Log.v("Steps","4");
            if (users != null) {
                Log.v("Steps","5");
                callback.onLoad(users);
            }else{
                Log.v("Steps","!5");
            super.onPreExecute();
        }}


    }
}
