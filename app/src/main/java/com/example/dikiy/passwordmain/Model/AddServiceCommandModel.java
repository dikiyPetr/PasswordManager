package com.example.dikiy.passwordmain.Model;

import android.os.AsyncTask;

import com.example.dikiy.passwordmain.Retrofit.ApiWorker;

public class AddServiceCommandModel {
    public void CreateServiceCommand(CreateServiceCommandCallback callback,String name, String service,String command, String method, String params, String template) {
        CreateServiceCommandTask createServiceCommandTask = new CreateServiceCommandTask(callback,name,service,command,method,params,template);
        createServiceCommandTask.execute();

    }
    public interface CreateServiceCommandCallback {
        void onLoad();
        void onFail();
        void onError();
    }
    class CreateServiceCommandTask extends AsyncTask<Void, Void, Integer> {
        private final CreateServiceCommandCallback callback;
        String name,service,command,method,params,template;
        CreateServiceCommandTask(CreateServiceCommandCallback callback, String name, String service,String command, String method,String params,String template) {
            this.callback = callback;
            this.name=name;
            this.service=service;
            this.command=command;
            this.method=method;
            this.params=params;
            this.template=template;

        }
        @Override
        protected Integer doInBackground(Void... voids) {
            int code= ApiWorker.addServiceCommand(name,service,command,method,params,template);

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
