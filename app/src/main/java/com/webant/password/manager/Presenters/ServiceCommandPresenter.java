package com.webant.password.manager.Presenters;

import com.webant.password.manager.Adapters.Get.GetService_Items_Commands;
import com.webant.password.manager.ConverterErrorResponse;
import com.webant.password.manager.Model.ModelCallback;
import com.webant.password.manager.Model.ServiceCommandModel;
import com.webant.password.manager.ServiceCommandActivity;

import java.util.List;

public class ServiceCommandPresenter {
    private ServiceCommandActivity view;
    private final ServiceCommandModel model;
    private String thisId;

    public ServiceCommandPresenter(ServiceCommandModel model) {
        this.model = model;
    }

    public void attachView(ServiceCommandActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }

    public void loadCommand(String id) {
        view.setRefresh(true);
        thisId = id;
        model.LoadCommand(new ServiceCommandModel.LoadCommandCallback() {
            @Override
            public void onLoad(List<GetService_Items_Commands> list) {
                view.load(list);
            }

            @Override
            public void onFail() {
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
                view.setRefresh(false);
            }

            @Override
            public void onError(int code, String message) {
                new ConverterErrorResponse(code, message).sendMessage(view.getApplicationContext());
                view.setRefresh(false);
            }
        }, view.getApplicationContext(), id);
    }

    public void delete(int p) {
        view.setRefresh(true);
        model.deleteCommand(new ModelCallback.Callback() {
            @Override
            public void onLoad() {
                loadCommand(thisId);
            }

            @Override
            public void onFail() {
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
                view.setRefresh(false);
            }

            @Override
            public void onError(int code, String s) {
                new ConverterErrorResponse(code,s).sendMessage(view.getApplicationContext());
                view.setRefresh(false);
            }
        }, view.getApplicationContext(), p);
    }
}
