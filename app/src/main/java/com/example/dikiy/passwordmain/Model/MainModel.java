package com.example.dikiy.passwordmain.Model;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Get.GetFolder;
import com.example.dikiy.passwordmain.Adapters.Get.GetFolder_Item;
import com.example.dikiy.passwordmain.Adapters.Get.GetPass;
import com.example.dikiy.passwordmain.Adapters.Get.GetPass_Item;
import com.example.dikiy.passwordmain.Adapters.Post.PostAdapter;
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
    public void loadUsers(LoadUserCallback callback,int folderid) {
        LoadUsersTask loadUsersTask = new LoadUsersTask(callback);
        loadUsersTask.setId(folderid);
        loadUsersTask.execute();

    }
    public void refreshBd(RefreshBDCallback callback) {
        RefreshBDTask refreshBDCallback = new RefreshBDTask(callback);
        refreshBDCallback.execute();

    }
    public void deleteItem(DeleteItemCallback callback, int item, boolean mode) {
        DeleteItemTask deleteItemCallback = new DeleteItemTask(callback, item, mode);
        deleteItemCallback.execute();

    }
    public void edititem(DeleteItemCallback callback, int item, boolean mode) {
        DeleteItemTask deleteItemCallback = new DeleteItemTask(callback, item, mode);
        deleteItemCallback.execute();

    }

    public interface LoadUserCallback {
        void onLoad(List<MainItem> users);
    }


    public interface RefreshBDCallback {
        void onLoad(Boolean b);
    }
    public interface DeleteItemCallback {
        void onLoad(int id,boolean mode);
    }
    class EditItemTask extends AsyncTask<Void, Void, Integer>{

        @Override
        protected Integer doInBackground(Void... voids) {



            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {

        }
    }
    class DeleteItemTask extends AsyncTask<Void, Void, Integer>{
        private final DeleteItemCallback callback;
        int item;
        boolean mode;
        DeleteItemTask(DeleteItemCallback callback, int item, boolean mode) {
            this.callback = callback;
            this.item = item;
            this.mode= mode;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            if(mode){
                //delete folder
                final Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer "+LoadText.getText("access_token"));

                PostLogin postLogin = ApiUtils.getAPIService();
                postLogin.DeleteFolder(item,map).enqueue(new Callback<PostAdapter>() {

                    @Override
                    public void onResponse(Call<PostAdapter> call, Response<PostAdapter> response) {
                        if(response.code()==200){
                    DBWorker dbWorker= new DBWorker();
                    dbWorker.deleteFolder(item);
                    onPostExecute(1);
                    }else if(response.code()==409){
                            onPostExecute(-2);
                            Log.v("steps121312", String.valueOf(response.code()));
                        }else{
                            onPostExecute(-1);
                        }}

                    @Override
                    public void onFailure(Call<PostAdapter> call, Throwable t) {
                        onPostExecute(-1);
                    }
                });


            }else{
                //delete pass

                final Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer "+LoadText.getText("access_token"));

                PostLogin postLogin = ApiUtils.getAPIService();
                postLogin.DeletePass(item,map).enqueue(new Callback<PostAdapter>() {

                    @Override
                    public void onResponse(Call<PostAdapter> call, Response<PostAdapter> response) {
                        if(response.code()==200){
                        DBWorker dbWorker= new DBWorker();
                        dbWorker.deletePass(item);
                        onPostExecute(1);
                        }else if(response.code()==409){
                            onPostExecute(-2);
                        }else{
                            onPostExecute(-1);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostAdapter> call, Throwable t) {
                        onPostExecute(-1);
                    }
                });


            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            switch (code){
                case 1:
                    callback.onLoad(item, mode);
                    break;
                case -2:
                    callback.onLoad(-1,mode);
                    break;
                case -1:
                    callback.onLoad(-2,mode);
                    break;
            }


        }
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
