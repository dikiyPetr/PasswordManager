package com.example.dikiy.passwordmain.Presenters;

import com.example.dikiy.passwordmain.Adapters.Get.GetService_Items_Commands;
import com.example.dikiy.passwordmain.AddServiceCommandActivity;
import com.example.dikiy.passwordmain.Model.AddServiceCommandModel;
import com.example.dikiy.passwordmain.Model.ModelCallback;

import java.util.List;

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

    public void createCommand(String name, String service, String method, List<String> paramsBody,List<String> paramsUrl, String templateBody,String templateUrl){
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
            },view.getApplicationContext(),name,service,method,paramsBody,paramsUrl,templateBody,templateUrl);
    }

    public void loadCommand(final String id) {
        model.loadCommand(new AddServiceCommandModel.LoadCommandCallback() {
            @Override
            public void onLoad(GetService_Items_Commands item) {
                view.setDefaultData(item);
            }

            @Override
            public void onFail() {
                loadCommand(id);
            }

            @Override
            public void onError() {
                loadCommand(id);
                view.error();
            }
        },view.getApplicationContext(),id);
    }

    public void edit(String name, final String service, String method, String templateBody, String templateUrl) {
        model.edit(new AddServiceCommandModel.LoadCommandCallback() {
            @Override
            public void onLoad(GetService_Items_Commands item) {
                view.setDefaultData(item);
            }

            @Override
            public void onFail() {
                loadCommand(service);
            }

            @Override
            public void onError() {
                loadCommand(service);
                view.error();
            }
        },view.getApplicationContext(),service,name,method,templateBody,templateUrl);
    }
}
