package com.example.dikiy.passwordmain.Presenters;

import com.example.dikiy.passwordmain.FolderActivity;
import com.example.dikiy.passwordmain.MainActivity;
import com.example.dikiy.passwordmain.Model.FolderModel;
import com.example.dikiy.passwordmain.Model.MainModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 25.03.2018.
 */

public class FolderPresenter {
    private FolderActivity view;
    private final FolderModel model;

    public FolderPresenter(FolderModel model) {

        this.model = model;

    }

    public void attachView(FolderActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }


    public void viewIsReady() {

    }
    public void createFolder(String name,String way){
        model.CreateFolder(new FolderModel.CreateFolderCallback() {
            @Override
            public void onLoad(int stat) {
                view.execute(stat);
            }
        },name,way);
    }
}
