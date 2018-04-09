package com.example.dikiy.passwordmain.Model;

import android.os.AsyncTask;

import com.example.dikiy.passwordmain.ItemModel.MainItem;
import com.example.dikiy.passwordmain.Retrofit.ApiWorker;

import java.util.List;

/**
 * Created by dikiy on 25.03.2018.
 */

public class FolderModel {
    public void CreateFolder(CreateFolderCallback callback,String name,String way) {
        CreateFolderTask loadUsersTask = new CreateFolderTask(callback,name,way);
        loadUsersTask.execute();

    }
    public interface CreateFolderCallback {
        void onLoad(int stat);
    }
    class CreateFolderTask extends AsyncTask<Void, Void, Integer> {
        private final CreateFolderCallback callback;
        private String name;
        private String id;
        CreateFolderTask(CreateFolderCallback callback,String name,String way) {
            this.callback = callback;
           this.name = name;
           this.id=way;
        }
        @Override
        protected Integer doInBackground(Void... voids) {
            ApiWorker.createFolder(name,id);
            callback.onLoad(200);
            return null;


        }
    }
}
