package com.example.dikiy.passwordmain.Presenters;

import com.example.dikiy.passwordmain.Adapters.Get.GetService_Items;
import com.example.dikiy.passwordmain.Model.ServiceModel;
import com.example.dikiy.passwordmain.ServiceActivity;

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

    public void getService(){
        model.LoadService(new ServiceModel.LoadServiceCallback() {

            @Override
            public void onLoad(List<GetService_Items> list) {
                view.refresh(list);
            }

            @Override
            public void onFail() {
                view.fail();
            }

            @Override
            public void onError() {

            }
        });
    }

    public void viewIsReady() {
        getService();
    }
}
