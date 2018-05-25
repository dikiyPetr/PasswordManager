package com.example.dikiy.passwordmain.Model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.dikiy.passwordmain.Retrofit.ApiWorker;

public class AddServiceModel {
        public void CreateService(CreateServiceCallback callback, String name, String url, String login,String pass,String token,String token_name) {
        CreateServiceTask loadUsersTask = new CreateServiceTask(callback,name,url,login,pass,token,token_name);
        loadUsersTask.execute();

    }
    public interface CreateServiceCallback {
        void onLoad();
        void onFail();
        void onError();
    }
    class CreateServiceTask extends AsyncTask<Void, Void, Integer> {
        private final CreateServiceCallback callback;
        String name,url,token,login,pass,token_name;
        CreateServiceTask(CreateServiceCallback callback,  String name, String url, String login,String pass,String token,String token_name) {
            this.callback = callback;
            this.name=name;
            this.url=url;
            this.login=login;
            this.pass=pass;
            this.token=token;
            this.token_name=token_name;

        }
        @Override
        protected Integer doInBackground(Void... voids) {
            int code=ApiWorker.addService(name,url,login,pass,token,token_name);
            Log.v("HttpServiceLog", String.valueOf(code));
            return code;


        }

        @Override
        protected void onPostExecute(Integer integer) {
            switch (integer){
                case 200: callback.onLoad();
                    break;
                case 0:callback.onFail();
                    break;
                default:callback.onError();
                    break;
            }
        }
    }
}
