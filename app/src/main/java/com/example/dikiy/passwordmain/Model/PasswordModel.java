package com.example.dikiy.passwordmain.Model;

import android.os.AsyncTask;

import com.example.dikiy.passwordmain.ItemModel.PasswordList;
import com.example.dikiy.passwordmain.RecyclerView.RecyclerItem;
import com.example.dikiy.passwordmain.Retrofit.ApiWorker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 16.02.2018.
 */

public class PasswordModel
{
    public void loadUsers(PasswordModel.LoadUserCallback callback) {
        PasswordModel.LoadUsersTask loadUsersTask = new PasswordModel.LoadUsersTask(callback);
        loadUsersTask.execute();
    }
    public  interface LoadUserCallback {
        void onLoad(PasswordList users);
    }
    class LoadUsersTask extends AsyncTask<Void, Void, PasswordList> {

        private final PasswordModel.LoadUserCallback callback;

        LoadUsersTask(PasswordModel.LoadUserCallback callback) {
            this.callback = callback;
        }

        @Override
        protected PasswordList doInBackground(Void... params) {

            List<RecyclerItem> movieList= new ArrayList<>();
            movieList.add(new RecyclerItem("tag"));
            List<RecyclerItem> movieList2= new ArrayList<>();
            movieList.add(new RecyclerItem("tag"));
            PasswordList list = new PasswordList("name","url","login","password",movieList,movieList2,"log");

           return  list;
        }

        @Override
        protected void onPostExecute(PasswordList users) {
            if (callback != null) {
                callback.onLoad(users);
            }
        }
    }


    public void CreatePass(CreatePassCallback callback,String name,String folder, String url, String pass,String login,String description) {
        CreatePassTask createPassTask = new CreatePassTask(callback,name,folder, url,  pass,login, description);
        createPassTask.execute();
    }
    public  interface CreatePassCallback {
        void onLoad(int stat);
    }
    class CreatePassTask extends AsyncTask<Void, Void, Integer> {
        private final CreatePassCallback callback;
        String name,folder,url, pass,login,description;
        CreatePassTask(CreatePassCallback callback,String name,String folder, String url, String pass,String login,String description) {
            this.name=name;
            this.folder=folder;
            this.url=url;
            this.pass=pass;
            this.login=login;
            this.description=description;
            this.callback = callback;
        }
        @Override
        protected Integer doInBackground(Void... voids) {
            ApiWorker.createPass(name,folder,url,pass,login,description);
            callback.onLoad(200);
            return 0;
        }
    }}
