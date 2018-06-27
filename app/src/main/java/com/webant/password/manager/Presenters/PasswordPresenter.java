package com.webant.password.manager.Presenters;


import com.webant.password.manager.Adapters.Get.GetService_Items;
import com.webant.password.manager.Adapters.Get.GetService_Items_Commands;
import com.webant.password.manager.ConverterErrorResponse;
import com.webant.password.manager.Model.ModelCallback;
import com.webant.password.manager.Model.PasswordModel;
import com.webant.password.manager.Model.ServiceCommandModel;
import com.webant.password.manager.Model.ServiceModel;
import com.webant.password.manager.PasswordActivity;

import java.util.List;

/**
 * Created by dikiy on 16.02.2018.
 */

public class PasswordPresenter {
    private PasswordActivity view;
    private final PasswordModel model;

    public PasswordPresenter(PasswordModel model) {
        this.model = model;
    }

    public void attachView(PasswordActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }

    public void createPass(String name, int folder, String url, String pass, String login, String description, List<String> tags, List<String> groups) {
        model.CreatePass(new PasswordModel.CreatePassCallback() {
            @Override
            public void onLoad(int id) {
                if (view.getCommandId() == 0)
                    view.execute();
                else
                    setParam(view.getCommandId(), id);
            }

            @Override
            public void onError(int code, String message) {
                view.setError(new ConverterErrorResponse(code, message).createItem(view.getApplicationContext()));
                view.setRefresh();
            }

            @Override
            public void onFail() {
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
                view.setRefresh();
            }
        }, view.getApplicationContext(), name, String.valueOf(folder), url, pass, login, description, tags, groups);
    }

    public void updatePass(Integer i, String s, String s1, String s2, String s3, List<String> tagsListEdit, List<Integer> tagsListEditMode) {
        model.UpdatePass(new ModelCallback.Callback() {
            @Override
            public void onLoad() {
                view.setExecute();
            }

            @Override
            public void onFail() {
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
                view.modeEdit(1);
            }

            @Override
            public void onError(int code, String s) {
                new ConverterErrorResponse(code, s).sendMessage(view.getApplicationContext());
                view.modeEdit(1);
            }
        }, view.getApplicationContext(), i, s, s1, s2, s3, tagsListEdit, tagsListEditMode);
    }


    public void givePass(int folder, final String email) {
        model.givePass(new ModelCallback.Callback() {
            @Override
            public void onLoad() {
                view.sendMesageToUser(email);
            }

            @Override
            public void onFail() {
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
            }

            @Override
            public void onError(int code, String s) {
                new ConverterErrorResponse(code, s).sendMessageSharePass(view.getApplicationContext());
            }
        }, view.getApplicationContext(), folder, email);
    }

    public void loadService() {
        model.LoadService(new ServiceModel.LoadServiceCallback() {
            @Override
            public void onLoad(List<GetService_Items> list) {
                view.loadService(list);
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onError(int code, String message) {

            }
        }, view.getApplicationContext());
    }

    public void loadCommand(int id) {
        model.LoadCommand(new ServiceCommandModel.LoadCommandCallback() {
            @Override
            public void onLoad(List<GetService_Items_Commands> list) {
                view.loadCommand(list);
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onError(int code, String message) {

            }
        }, view.getApplicationContext(), String.valueOf(id));
    }

    public void setParam(int commandId, int id) {

        model.setParams(new ModelCallback.CallbackNoError() {
            @Override
            public void onLoad() {
                view.execute();
            }

            @Override
            public void onFail() {
            }
        }, view.getApplicationContext(), commandId, id, view.getUrlItems(), view.getBodyItems());
    }
}
