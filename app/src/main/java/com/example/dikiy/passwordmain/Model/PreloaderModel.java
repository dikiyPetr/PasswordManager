package com.example.dikiy.passwordmain.Model;

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
        void onLoad(boolean stat);
    }
    class CheckTokenTask extends AsyncTask<Void, Void, Boolean> {

        private final  PreloaderModel.CheckLoadCallback callback;

        CheckTokenTask(PreloaderModel.CheckLoadCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            //проверка аторизации

            if (LoadText.getText("access_token").length()>5){
                //обновление токена

                if( ApiWorker.refreshToken()==200){

                    return true;
                }else{
                    return false;
                }

            }else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean stat) {
            if (callback != null) {
                callback.onLoad(stat);
            }
        }


    }
}
