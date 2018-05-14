package com.example.dikiy.passwordmain.Model;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Retrofit.ApiWorker;

/**
 * Created by dikiy on 14.03.2018.
 */

public class PreloaderModel {
    public void CheckToken(PreloaderModel.CheckLoadCallback callback) {
        PreloaderModel.CheckTokenTask loadUsersTask = new PreloaderModel.CheckTokenTask(callback);
        loadUsersTask.execute();
    }
    public interface CheckLoadCallback {
        void onLoad(int stat);
    }
    class CheckTokenTask extends AsyncTask<Void, Void, Integer> {

        private final  PreloaderModel.CheckLoadCallback callback;

        CheckTokenTask(PreloaderModel.CheckLoadCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            //проверка аторизации

            if (LoadText.getText("access_token").length()>5){
                //обновление токена
                int code= ApiWorker.refreshToken();
               switch (code){
                   case 200:
                       return 1;


                   case 401:
                       return 0;


                   case 0:
                       return 2;

               }

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
