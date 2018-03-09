package com.example.dikiy.passwordmain.Password;

import android.os.AsyncTask;

import com.example.dikiy.passwordmain.RecyclerView.RecyclerItem;

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
    public void addPassword(PasswordModel.AddPasswordCallback callback) {
        PasswordModel.AddPasswordTask addPasswordTask = new PasswordModel.AddPasswordTask(callback);
        addPasswordTask.execute();
    }
    interface LoadUserCallback {
        void onLoad(PasswordList users);
    }

    interface AddPasswordCallback {
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
    class AddPasswordTask extends AsyncTask<Void, Void, PasswordList>{
        private final PasswordModel.AddPasswordCallback callback;

        AddPasswordTask(PasswordModel.AddPasswordCallback callback) {
            this.callback = callback;
        }
        @Override
        protected PasswordList doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(PasswordList users) {
            callback.onLoad(null);
        }
    }
}
