package com.example.dikiy.passwordmain.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Get.GetFolder;
import com.example.dikiy.passwordmain.Adapters.Get.GetFolder_Item;
import com.example.dikiy.passwordmain.DBase.DBWorker;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.ItemModel.MainItem;
import com.example.dikiy.passwordmain.Retrofit.ApiUtils;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        void onLoad();
    }



    class RefreshBDTask extends AsyncTask{
        private final RefreshBDCallback callback;

        RefreshBDTask(RefreshBDCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            PostLogin postLogin = ApiUtils.getAPIService();
            Response response = null;
            final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+ LoadText.getText("access_token"));
            try {
                response = postLogin.GetFolder(map).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response.code()==200){
            GetFolder getFolder = (GetFolder) response.body();
            ArrayList<GetFolder_Item> getFolder_items= new ArrayList<>();
            getFolder_items.addAll(getFolder.getItems());
            Log.v("12344444","1"+getFolder_items.toString());
            DBWorker dbWorker = new DBWorker();
//            dbWorker.initFolder(getFolder_items);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (callback != null) {
                callback.onLoad();
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
        return items;
        }

        @Override
        protected void onPostExecute(List<MainItem> users) {
            if (callback != null) {
                callback.onLoad(users);
            }
        }
    }
}
