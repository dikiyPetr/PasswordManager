package com.example.dikiy.passwordmain.Model;

import android.os.AsyncTask;

import com.example.dikiy.passwordmain.Adapters.Get.GetService;
import com.example.dikiy.passwordmain.Adapters.Get.GetService_Items;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Retrofit.ApiUtils;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceModel {
    public void LoadService(LoadServiceCallback callback) {
        LoadServiceTask loadUsersTask = new LoadServiceTask(callback);
        loadUsersTask.execute();

    }
    public interface LoadServiceCallback {
        void onLoad(List<GetService_Items> list);
        void onFail();
        void onError();
    }
    class LoadServiceTask extends AsyncTask<Void, Void, List<GetService_Items>> {
        private final LoadServiceCallback callback;
        int stat=0;
        LoadServiceTask(LoadServiceCallback callback) {
            this.callback = callback;


        }
        @Override
        protected List<GetService_Items> doInBackground(Void... voids) {
            final Map<String, String> map = new HashMap<>();
            map.put("Authorization", "Bearer "+ LoadText.getText("access_token"));

            PostLogin postLogin = ApiUtils.getAPIService();
        postLogin.GetService(map).enqueue(new Callback<GetService>() {
            @Override
            public void onResponse(Call<GetService> call, Response<GetService> response) {
                if(response.code()==200){

                    stat=1;
                    onPostExecute(response.body().getItems());}else{
                    stat =-1;
                    onPostExecute(null);
                }
            }

            @Override
            public void onFailure(Call<GetService> call, Throwable t) {
                stat=-1;
                onPostExecute(null);
            }
        });
            return null;
        }

        @Override
        protected void onPostExecute(List<GetService_Items> list) {
            if(stat!=0)
                switch (stat){
                    case 1:callback.onLoad(list);break;
                    default:callback.onFail();break;

                }
        }
    }

}
