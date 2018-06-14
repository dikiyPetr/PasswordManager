package com.example.dikiy.passwordmain.Presenters;

import com.example.dikiy.passwordmain.Adapters.Get.GetService_Items_Commands;
import com.example.dikiy.passwordmain.Model.ServiceCommandModel;
import com.example.dikiy.passwordmain.ServiceCommandActivity;

import java.util.List;

public class ServiceCommandPresenter {
    private ServiceCommandActivity view;
    private final ServiceCommandModel model;

    public ServiceCommandPresenter(ServiceCommandModel model) {

        this.model = model;

    }

    public void attachView(ServiceCommandActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }



    public void loadCommand(String id){
        model.LoadCommand(new ServiceCommandModel.LoadCommandCallback() {


            @Override
            public void onLoad(List<GetService_Items_Commands> list) {
                view.load(list);
            }

            @Override
            public void onFail() {
                view.fail();
            }

            @Override
            public void onError() {

            }
        },view.getApplicationContext(),id);
    }
}
