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
        void onLoad();
        void onFail();
        void onError(int code);
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
                    return code;
                }else if(code==400){
                    if( ApiWorker.getRandomId()!=201){return 0;}

                    if(ApiWorker.Login(name,pass)==200){return 200;}
                }

            return code;
        }

        @Override
        protected void onPostExecute(Integer stat) {
           switch (stat){
               case 200: callback.onLoad();
               break;
               case 0:callback.onFail();
               break;
               default:callback.onError(stat);
           }
        }


    }
    public void Register(RegisterCallback callback,String name,String login,String pass) {
        RegisterTask registerTask = new RegisterTask(callback,name,login,pass);
        registerTask.execute();
    }
    public interface RegisterCallback {
        void onLoad();
        void onFail();
        void onError(int code);
    }
    class RegisterTask extends AsyncTask<Void, Void, Integer> {
        RegisterCallback callback;
        String name,login,pass;
        public RegisterTask(RegisterCallback callback, String name,String login, String pass) {
        this.callback=callback;
        this.name=name;
        this.login=login;
        this.pass=pass;

        }

        @Override
        protected Integer doInBackground(Void... params) {
            int code=ApiWorker.register(name,login,pass);
            return code;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            switch (integer){
                case 200:
                    callback.onLoad();
                    break;
                case 0:
                    callback.onFail();
                     break;

                     default:
                         callback.onError(integer);
                         break;
            }
        }
    }
}
