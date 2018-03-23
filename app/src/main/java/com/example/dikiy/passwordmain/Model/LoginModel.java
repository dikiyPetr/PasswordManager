package com.example.dikiy.passwordmain.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Retrofit.ApiWorker;

/**
 * Created by dikiy on 14.03.2018.
 */

public class LoginModel {
    public void Login(LoginModel.LoginCallback callback,String name,String pass) {
        LoginModel.LoginTask loginTask = new LoginModel.LoginTask(callback,name,pass);
        loginTask.execute();
    }
    public interface LoginCallback {
        void onLoad(int stat);
    }
    class LoginTask extends AsyncTask<Void, Void, Integer> {
    private String name;
    private String pass;
        private final  LoginModel.LoginCallback callback;

        LoginTask(LoginModel.LoginCallback callback,String name,String pass) {
            this.callback = callback;
            this.name= name;
            this.pass=pass;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            Log.v("112313212313",LoadText.getText("client_id"));
                if(LoadText.getText("client_id").length()<5){if( ApiWorker.getRandomId()!=200){return 0;}}
                int code =ApiWorker.Login(name,pass);
            Log.v("http", String.valueOf(code));
                if(code==200){
                    return 2;
                }else if(code==400){
                    if( ApiWorker.getRandomId()!=200){return 0;}
                    if(ApiWorker.Login(name,pass)==200){return 2;}
                }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer stat) {
            if (callback != null) {
                Log.v("http","code "+stat);
                callback.onLoad(stat);
            }
        }


    }
}
