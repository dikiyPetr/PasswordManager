package com.example.dikiy.passwordmain.Model;

import android.os.AsyncTask;

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

               if( ApiWorker.getRandomId()==200){
                if(ApiWorker.Login(name,pass)==200){
                    return 2;
                }
                return 1;
               }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer stat) {
            if (callback != null) {
                callback.onLoad(stat);
            }
        }


    }
}
