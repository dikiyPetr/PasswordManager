package com.example.dikiy.passwordmain.MainActivityPac;

import android.os.AsyncTask;

import com.example.dikiy.passwordmain.MainRecycler.MainItem;
import com.example.dikiy.passwordmain.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 16.02.2018.
 */

public class MainModel {
    public void loadUsers(LoadUserCallback callback) {
        LoadUsersTask loadUsersTask = new LoadUsersTask(callback);
        loadUsersTask.execute();
    }


    interface LoadUserCallback {
        void onLoad(List<MainItem> users);
    }
    class LoadUsersTask extends AsyncTask<Void, Void, List<MainItem>> {

        private final LoadUserCallback callback;

        LoadUsersTask(LoadUserCallback callback) {
            this.callback = callback;
        }

        @Override
        protected List<MainItem> doInBackground(Void... params) {

            List<MainItem> items = new ArrayList<>();
            items.add(new MainItem("folder","","",0, R.drawable.folder));
            items.add(new MainItem("folder","","",0,R.drawable.folder));
            items.add(new MainItem("folder","","",0,R.drawable.folder));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            items.add(new MainItem("key","tag","",R.drawable.mainicong,R.drawable.keyb));
            return  items;
        }

        @Override
        protected void onPostExecute(List<MainItem> users) {
            if (callback != null) {
                callback.onLoad(users);
            }
        }
    }
}
