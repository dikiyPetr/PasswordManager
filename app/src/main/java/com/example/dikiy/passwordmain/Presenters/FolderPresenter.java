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
    public void createFolder(String name,int way){
        model.CreateFolder(new FolderModel.CreateFolderCallback() {
            @Override
            public void onLoad(int stat) {
                view.execute(stat);
            }
        },name,way);
    }

    public void addTagOrGroup(Boolean tag, String s, int thisId) {
        model.AddTag(new FolderModel.AddTagCallback() {
            @Override
            public void onLoad(String s) {

            }
        },tag,s,thisId);
    }

    public void removeTagOrGroup(String s, int thisId, Boolean tag) {
        model.RemoveTag(new FolderModel.RemoveTagCallback() {
            @Override
            public void onLoad() {

            }
        },s,thisId,tag);
    }
}
