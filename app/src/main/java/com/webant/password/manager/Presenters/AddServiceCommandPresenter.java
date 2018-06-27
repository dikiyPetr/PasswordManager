package com.webant.password.manager.Presenters;


import com.webant.password.manager.Adapters.Get.GetService_Items_Commands;
import com.webant.password.manager.AddServiceCommandActivity;
import com.webant.password.manager.ConverterErrorResponse;
import com.webant.password.manager.Model.AddServiceCommandModel;
import com.webant.password.manager.Model.ModelCallback;

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

    public void createCommand(String name, String service, String method, String methodUsed, List<String> paramsBody, List<String> paramsUrl, String templateBody, String templateUrl) {
        model.CreateServiceCommand(new ModelCallback.Callback(){
            @Override
            public void onLoad() {
                view.execute();
            }

            @Override
            public void onFail() {
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
                view.cancelRefresh();
            }

            @Override
            public void onError(int code,String message) {
                new ConverterErrorResponse(code,message).sendMessage(view.getApplicationContext());
                view.cancelRefresh();
            }
        }, view.getApplicationContext(), name, service, method, methodUsed, paramsBody, paramsUrl, templateBody, templateUrl);
    }

    public void loadCommand(final String id) {
        model.loadCommand(new AddServiceCommandModel.LoadCommandCallback() {
            @Override
            public void onLoad(GetService_Items_Commands item) {
                view.setDefaultData(item);
            }

            @Override
            public void onFail() {
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
                view.cancelRefresh();
                view.execute();
            }

            @Override
            public void onError(int code,String message) {
                new ConverterErrorResponse(code,message).sendMessage(view.getApplicationContext());
                view.cancelRefresh();
                view.execute();
            }
        }, view.getApplicationContext(), id);
    }

    public void edit(String name, final String service, String method, String methodUsed, String templateBody, String templateUrl) {
        model.edit(new AddServiceCommandModel.LoadCommandCallback() {
            @Override
            public void onLoad(GetService_Items_Commands item) {
                view.setDefaultData(item);
            }

            @Override
            public void onFail() {
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
                view.cancelRefresh();
            }

            @Override
            public void onError(int code, String message) {
                new ConverterErrorResponse(code,message).sendMessage(view.getApplicationContext());
                view.cancelRefresh();
            }

        }, view.getApplicationContext(), service, name, method, methodUsed, templateBody, templateUrl);
    }
}
