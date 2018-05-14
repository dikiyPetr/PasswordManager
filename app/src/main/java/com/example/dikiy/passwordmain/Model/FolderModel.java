package com.example.dikiy.passwordmain.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Get.GetFolder_Item;
import com.example.dikiy.passwordmain.Adapters.Get.GetPass_Item;
import com.example.dikiy.passwordmain.DBase.DBWorker;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.ItemModel.MainItem;
import com.example.dikiy.passwordmain.Retrofit.ApiUtils;
import com.example.dikiy.passwordmain.Retrofit.ApiWorker;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;
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
    public void CreateFolder(CreateFolderCallback callback,String name,int way) {
        CreateFolderTask loadUsersTask = new CreateFolderTask(callback,name,way);
        loadUsersTask.execute();

    }
    public interface CreateFolderCallback {
        void onLoad(int stat);
    }
    class CreateFolderTask extends AsyncTask<Void, Void, Integer> {
        private final CreateFolderCallback callback;
        private String name;
        private int id;
        CreateFolderTask(CreateFolderCallback callback,String name,int way) {
            this.callback = callback;
           this.name = name;
           this.id=way;
        }
        @Override
        protected Integer doInBackground(Void... voids) {

            callback.onLoad( ApiWorker.createFolder(name,id));
            return null;


        }
    }



    public void RemoveTag(RemoveTagCallback callback, String s, int itemId, boolean b) {
      RemoveTagTask removeTagTask = new RemoveTagTask(callback,s,itemId,b);
        removeTagTask.execute();
    }
    public  interface RemoveTagCallback {
        void onLoad();
    }
    class RemoveTagTask extends AsyncTask<Void, Void, Integer> {
        RemoveTagCallback callback;
        String s;
        int id;
        boolean b;
        public RemoveTagTask(RemoveTagCallback callback, String s, int id, boolean b) {
            this.callback=callback;
            this.s=s;
            this.id=id;
            this.b=b;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            DBWorker dbWorker=new DBWorker();
            int itemId=0;
            if(!b){
                itemId=dbWorker.getTagId(s);
                final Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer "+ LoadText.getText("access_token"));

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("tag", String.valueOf(itemId));

                PostLogin postLogin = ApiUtils.getAPIService();
                Log.v("asdasdasdxcaz",id+" "+itemId);
                postLogin.DeleteTagInFolder(id,jsonObject,map).enqueue(new Callback<GetFolder_Item>() {
                    @Override
                    public void onResponse(Call<GetFolder_Item> call, Response<GetFolder_Item> response) {
                        onPostExecute(1);
                    }

                    @Override
                    public void onFailure(Call<GetFolder_Item> call, Throwable t) {

                    }
                });
            }else{
                itemId=dbWorker.getGroupId(s);
                final Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer "+ LoadText.getText("access_token"));

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("group", String.valueOf(itemId));

                PostLogin postLogin = ApiUtils.getAPIService();
                Log.v("asdasdasdxcaz",id+" "+itemId);
                postLogin.DeleteGroupInFolder(id,jsonObject,map).enqueue(new Callback<GetFolder_Item>() {
                    @Override
                    public void onResponse(Call<GetFolder_Item> call, Response<GetFolder_Item> response) {
                        onPostExecute(1);
                    }

                    @Override
                    public void onFailure(Call<GetFolder_Item> call, Throwable t) {

                    }
                });

            }


            return 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if(integer==1){callback.onLoad();}
        }
    }


    public void AddTag(AddTagCallback callback, boolean b, String s, int passid) {
       AddTagTask addTagTask = new AddTagTask(callback,b,s,passid);
        addTagTask.execute();
    }
    public  interface AddTagCallback {
        void onLoad(String s);
    }
    class AddTagTask extends AsyncTask<Void, Void, Integer> {
     AddTagCallback callback;
        boolean type;
        String s;
        int passid;
        public AddTagTask(AddTagCallback callback, boolean b, String s, int passid) {
            this.callback=callback;
            this.s=s;
            type=b;
            this.passid=passid;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            DBWorker dbWorker=new DBWorker();
            int itemId=0;
            Log.v("123123123asdqq","1");
            if(!type){
                Log.v("123123123asdqq","2");
                itemId=dbWorker.getTagId(s);
                if(itemId!=0){

                    onPostExecute(ApiWorker.addTagInFolder(passid,itemId));
                }else {
                    itemId =ApiWorker.addTag(s);
                    if(itemId!=0){
                        onPostExecute(ApiWorker.addTagInFolder(passid,itemId));}
                }

            }else{
                itemId=dbWorker.getGroupId(s);
                if(itemId!=0){

                    onPostExecute(ApiWorker.addGroupInFolder(passid,itemId));
                }else {
                    itemId =ApiWorker.addGroup(s);
                    if(itemId!=0){
                        onPostExecute(ApiWorker.addGroupInFolder(passid,itemId));}
                }

            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if(integer==200){
                callback.onLoad(s);
            }else if(integer==0){

            }
        }
    }






    public void UpdateFolder(UpdateFolderCallback callback, int i, String name) {
        UpdateFolderTask updatePassTask = new UpdateFolderTask(callback,i,name);
        updatePassTask.execute();
    }
    public  interface UpdateFolderCallback {
        void onLoad(int code);
    }
    class UpdateFolderTask extends AsyncTask<Void, Void, Integer> {
        int id =0;
        String name = "";
        private final UpdateFolderCallback callback;

        public UpdateFolderTask(UpdateFolderCallback callback, int i, String name) {
            this.callback=callback;
            this.id=i;
            this.name = name ;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            Log.v("123123123aasc","123");
            ApiWorker.updateFolder(id,name);
            DBWorker dbWorker= new DBWorker();
            dbWorker.updateFolder(id,name);
            callback.onLoad(200);
            return 0;
        }

        @Override
        protected void onPostExecute(Integer s) {

        }
    }

}
