package com.example.dikiy.passwordmain.Presenters;

import com.example.dikiy.passwordmain.AddServiceCommandActivity;
import com.example.dikiy.passwordmain.Model.AddServiceCommandModel;

public class AddServiceCommandPresenter {
    private AddServiceCommandActivity view;
    private final AddServiceCommandModel model;

    public AddServiceCommandPresenter(AddServiceCommandModel model) {

        this.model = model;

    }

    public void attachView(AddServiceCommandActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }

    public void createCommand(String name, String service,String command, String method, String params, String template){
            model.CreateServiceCommand(new AddServiceCommandModel.CreateServiceCommandCallback() {
                @Override
                public void onLoad() {
                    view.execute();
                }

                @Override
                public void onFail() {
                    view.fail();
                }

                @Override
                public void onError() {
                    view.error();
                }
            },name,service,command,method,params,template);
    }
}
