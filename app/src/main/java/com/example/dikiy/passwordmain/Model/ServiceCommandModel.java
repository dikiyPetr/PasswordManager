package com.example.dikiy.passwordmain.Model;

import android.os.AsyncTask;

import com.example.dikiy.passwordmain.Adapters.Get.GetCommand;
import com.example.dikiy.passwordmain.Adapters.Get.GetService;
import com.example.dikiy.passwordmain.Adapters.Get.GetService_Items_Commands;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.Retrofit.ApiUtils;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceCommandModel {
    public void LoadCommand(LoadCommandCallback callback) {
        LoadCommandTask loadCommandTask = new LoadCommandTask(callback);
        loadCommandTask.execute();

    }
    public interface LoadCommandCallback {
        void onLoad(List<GetService_Items_Commands> list);
        void onFail();
        void onError();
    }
    class LoadCommandTask extends AsyncTask<Void, Void, List<GetService_Items_Commands> > {
        private final LoadCommandCallback callback;
        int stat=0;
        LoadCommandTask(LoadCommandCallback callback) {
            this.callback = callback;


        }
        @Override
        protected List<GetService_Items_Commands> doInBackground(Void... voids) {
            final Map<String, String> map = new HashMap<>();
            map.put("Authorization", "Bearer "+ LoadText.getText("access_token"));

            PostLogin postLogin = ApiUtils.getAPIService();
            postLogin.GetCommand(map).enqueue(new Callback<GetCommand>() {
                @Override
                public void onResponse(Call<GetCommand> call, Response<GetCommand> response) {
if(response.code()==200){
    stat=1;
    onPostExecute(response.body().getItems());
}else{
    stat=-1;
    onPostExecute(null);
}
                }

                @Override
                public void onFailure(Call<GetCommand> call, Throwable t) {
                    stat=-1;
                    onPostExecute(null);
                }
            });

return null;
        }

        @Override
        protected void onPostExecute(List<GetService_Items_Commands>  list) {
            if(stat!=0)
            switch (stat){
                case 1:callback.onLoad(list);break;
                default:callback.onFail();break;
            }
        }
    }
}
