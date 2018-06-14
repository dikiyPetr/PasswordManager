package com.example.dikiy.passwordmain.Presenters;

import com.example.dikiy.passwordmain.FolderActivity;
import com.example.dikiy.passwordmain.Model.FolderModel;

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
    public void createFolder(String name, int way, List<String> tags, List<String> groups){
        model.CreateFolder(new FolderModel.CreateFolderCallback() {
            @Override
            public void onLoad() {
                view.execute();
            }
        },view.getApplicationContext(),name,way,tags,groups);
    }

    public void addTagOrGroup(Boolean tag, String s, int thisId) {
        model.AddTagOrGroup(new FolderModel.AddTagCallback() {
            @Override
            public void onLoad() {
                view.setRefreshStatus();
            }
        },view.getApplicationContext(),tag,s,thisId);
    }

    public void removeTagOrGroup(String s, int thisId, Boolean tag) {
        model.RemoveTag(new FolderModel.RemoveTagCallback() {
            @Override
            public void onLoad() {
                view.setRefreshStatus();
            }
        },view.getApplicationContext(),s,thisId,tag);
    }

}
