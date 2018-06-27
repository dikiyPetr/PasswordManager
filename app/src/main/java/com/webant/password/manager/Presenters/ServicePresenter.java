package com.webant.password.manager.Presenters;

import com.webant.password.manager.Adapters.Get.GetService_Items;
import com.webant.password.manager.ConverterErrorResponse;
import com.webant.password.manager.Model.ModelCallback;
import com.webant.password.manager.Model.ServiceModel;
import com.webant.password.manager.ServiceActivity;

import java.util.List;

public class ServicePresenter {
    private ServiceActivity view;
    private final ServiceModel model;

    public ServicePresenter(ServiceModel model) {

        this.model = model;

    }

    public void attachView(ServiceActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }

    public void getService() {
        view.setRefresh(true);
        model.LoadService(new ServiceModel.LoadServiceCallback() {

            @Override
            public void onLoad(List<GetService_Items> list) {
                view.refresh(list);
            }

            @Override
            public void onFail() {
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
                view.setRefresh(false);
            }

            @Override
            public void onError(int code,String message) {
                new ConverterErrorResponse(code,message).sendMessage(view.getApplicationContext());
                view.setRefresh(false);
            }
        }, view.getApplicationContext());
    }

    public void viewIsReady() {
        getService();
    }

    public void delete(int p) {
        view.setRefresh(true);
        model.deleteService(new ModelCallback.Callback() {
            @Override
            public void onLoad() {
                getService();
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
