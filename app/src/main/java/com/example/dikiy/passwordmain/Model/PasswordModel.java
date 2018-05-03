package com.example.dikiy.passwordmain.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Get.GetPass_Item;
import com.example.dikiy.passwordmain.Adapters.Get.GetStorage;
import com.example.dikiy.passwordmain.Adapters.Post.PostAdapter;
import com.example.dikiy.passwordmain.DBase.DBWorker;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Retrofit.ApiUtils;
import com.example.dikiy.passwordmain.Retrofit.ApiWorker;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;
import com.google.gson.JsonObject;

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

public class PasswordModel
{
    public void RemoveTag(RemoveTagCallback callback, String s,int itemId,boolean b) {
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
        public RemoveTagTask(RemoveTagCallback callback, String s, int id,boolean b) {
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
                postLogin.DeleteTagInPass(id,jsonObject,map).enqueue(new Callback<GetPass_Item>() {
                    @Override
                    public void onResponse(Call<GetPass_Item> call, Response<GetPass_Item> response) {
                        onPostExecute(1);
                    }

                    @Override
                    public void onFailure(Call<GetPass_Item> call, Throwable t) {

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
                postLogin.DeleteGroupInPass(id,jsonObject,map).enqueue(new Callback<GetPass_Item>() {
                    @Override
                    public void onResponse(Call<GetPass_Item> call, Response<GetPass_Item> response) {
                        onPostExecute(1);
                    }

                    @Override
                    public void onFailure(Call<GetPass_Item> call, Throwable t) {

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


    public void AddTag(AddTagCallback callback,boolean b,String s,int passid) {
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
        public AddTagTask(AddTagCallback callback, boolean b, String s,int passid) {
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

                    onPostExecute(ApiWorker.addTagInPass(passid,itemId));
                }else {
                    itemId =ApiWorker.addTag(s);
                    if(itemId!=0){
                    onPostExecute(ApiWorker.addTagInPass(passid,itemId));}
                }

            }else{
                itemId=dbWorker.getGroupId(s);
                if(itemId!=0){

                    onPostExecute(ApiWorker.addGroupInPass(passid,itemId));
                }else {
                    itemId =ApiWorker.addGroup(s);
                    if(itemId!=0){
                        onPostExecute(ApiWorker.addGroupInPass(passid,itemId));}
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






    public void UpdatePass(UpdatePassCallback callback,int i,String name,String url,String login, String password) {
        UpdatePassTask updatePassTask = new UpdatePassTask(callback,i,name,url,login,password);
        updatePassTask.execute();
    }
    public  interface UpdatePassCallback {
        void onLoad(int code);
    }
    class UpdatePassTask extends AsyncTask<Void, Void, Integer> {
        int id =0;
        String name = "";
        String url = "";
        String login = "";
        String password = "";
        private final UpdatePassCallback callback;

        public UpdatePassTask(UpdatePassCallback callback, int i,String name,String url,String login, String password) {
            this.callback=callback;
            this.id=i;
            this.name = name ;
            this.url = url ;
            this.login = login ;
            this.password=password;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            Log.v("123123123aasc","123");
        ApiWorker.updatePass(id,name,url,login,password);
        DBWorker dbWorker= new DBWorker();
        dbWorker.updatePass(id,name,url,login,password);
        callback.onLoad(200);
            return 0;
        }

        @Override
        protected void onPostExecute(Integer s) {

        }
    }




    public void loadUsers(PasswordModel.LoadUserCallback callback,int i) {
      LoadUsersTask loadUsersTask = new PasswordModel.LoadUsersTask(callback,i);
        loadUsersTask.execute();
    }
    public  interface LoadUserCallback {
        void onLoad(String pass);
        void onError(int code);
    }
    class LoadUsersTask extends AsyncTask<Void, Void, String> {
        int id =0;
        int code=0;
        private final PasswordModel.LoadUserCallback callback;

        LoadUsersTask(PasswordModel.LoadUserCallback callback,int i) {
            this.callback = callback;
            this.id =i;
        }

        @Override
        protected String doInBackground(Void... params) {
            final Map<String, String> map = new HashMap<>();
            map.put("Authorization", "Bearer "+ LoadText.getText("access_token"));
            map.put("secretKey",LoadText.getText("pass"));
           PostLogin postLogin = ApiUtils.getAPIService();
            Log.v("123123",LoadText.getText("pass")+ id);
            postLogin.GetClue(map).enqueue(new Callback<GetStorage>() {
                @Override
                public void onResponse(Call<GetStorage> call, Response<GetStorage> response) {

                    for(int i=0;i<response.body().getItems().size();i++){

                       if(response.body().getItems().get(i).getPass().getId()==id){
                           PostLogin postLogin1 = ApiUtils.getAPIService();
                           Log.v("123123aa", response.body().getItems().get(i).getClue());
                           map.remove("secretKey");
                           map.put("secretKey",response.body().getItems().get(i).getClue());
                           postLogin1.GetPass(id,map).enqueue(new Callback<GetPass_Item>() {
                               @Override
                               public void onResponse(Call<GetPass_Item> call, Response<GetPass_Item> response) {
                                Log.v("123123aa", String.valueOf(response.body().getPass()));

                                    DBWorker dbWorker=new DBWorker();
                                    if(dbWorker.UpdatePass(id,response.body().getPass())){
                                    code=1;
                                onPostExecute(response.body().getPass());
                                    }else{
                                        code=4;
                                        onPostExecute(response.body().getPass());

                                    }


                               }

                               @Override
                               public void onFailure(Call<GetPass_Item> call, Throwable t) {
                                   code=2;
                                   onPostExecute("");
                               }
                           });
                           break;
                       }
                    }


                }

                @Override
                public void onFailure(Call<GetStorage> call, Throwable t) {
                    Log.v("123123aa","2");
                    code=2;
                    onPostExecute("");
                }
            });

            code=0;
           return  "";
        }

        @Override
        protected void onPostExecute(String users) {
            switch (code){
                case 1:
                    callback.onLoad(users);
                    break;
                case 2:
                    callback.onError(0);
                    break;
                case 3:
                    callback.onError(1);
                    break;
                case 4:
                    callback.onError(2);
                    break;
            }

        }
    }


    public void CreatePass(CreatePassCallback callback,String name,String folder, String url, String pass,String login,String description,List<String> tags,List<String> groups) {
        CreatePassTask createPassTask = new CreatePassTask(callback,name,folder, url,  pass,login, description,tags,groups);
        createPassTask.execute();
    }
    public  interface CreatePassCallback {
        void onLoad(int stat);
    }
    class CreatePassTask extends AsyncTask<Void, Void, Integer> {
        private final CreatePassCallback callback;
        String name,folder,url, pass,login,description;
        List<String> tags=new ArrayList<>();
        List<String> groups= new ArrayList<>();
        CreatePassTask(CreatePassCallback callback,String name,String folder, String url, String pass,String login,String description,List<String> tags,List<String> groups) {
            this.name=name;
            this.folder=folder;
            this.url=url;
            this.pass=pass;
            this.login=login;
            this.description=description;
            this.callback = callback;
            this.groups.addAll(tags);
            this.tags.addAll(groups );
        }
        @Override
        protected Integer doInBackground(Void... voids) {
            ApiWorker.createPass(name,folder,url,pass,login,description,groups,tags);
            callback.onLoad(200);
            return 0;
        }
    }}
