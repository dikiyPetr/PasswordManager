package com.example.dikiy.passwordmain.Presenters;

import com.example.dikiy.passwordmain.AddServiceActivity;
import com.example.dikiy.passwordmain.Model.AddServiceModel;

public class AddServicePresenter {
    private AddServiceActivity view;
    private final AddServiceModel model;

    public AddServicePresenter(AddServiceModel model) {

        this.model = model;

    }

    public void attachView(AddServiceActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }


    public void createService( String name, String url, String login,String pass,String token,String token_name) {
        model.createService(new AddServiceModel.CreateServiceCallback() {
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
        },view.getApplicationContext(),name,url,login,pass,token,token_name);
    }
}
