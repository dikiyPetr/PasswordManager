package com.webant.password.manager.Presenters;


import com.webant.password.manager.ConverterErrorResponse;
import com.webant.password.manager.FolderActivity;
import com.webant.password.manager.Model.FolderModel;
import com.webant.password.manager.Model.ModelCallback;

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

    public void createFolder(String name, int way, List<String> tags) {
        model.CreateFolder(new ModelCallback.Callback(){
            @Override
            public void onLoad() {
                view.execute();
            }

            @Override
            public void onFail() {
                view.cancelRefesh();
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
            }

            @Override
            public void onError(int code, String s) {
                view.cancelRefesh();
                view.setError(new ConverterErrorResponse(code,s).createItem(view.getApplicationContext()));
            }
        }, view.getApplicationContext(), name, way, tags);
    }

    public void updateFolder(int id, final String name, List<String> tagsListEdit, List<Integer> tagsListEditMode) {
        model.update(new ModelCallback.Callback() {
            @Override
            public void onLoad() {
                view.execute();
            }

            @Override
            public void onFail() {
                view.cancelRefesh();
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
            }

            @Override
            public void onError(int code,String s) {
                view.cancelRefesh();
                new ConverterErrorResponse(code,s).sendMessage(view.getApplicationContext());
            }
        }, view.getApplicationContext(), id, name, tagsListEdit, tagsListEditMode);
    }
}
