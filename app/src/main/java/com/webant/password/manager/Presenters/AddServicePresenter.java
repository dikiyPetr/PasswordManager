package com.webant.password.manager.Presenters;


import com.webant.password.manager.Adapters.Get.GetService_Items;
import com.webant.password.manager.AddServiceActivity;
import com.webant.password.manager.ConverterErrorResponse;
import com.webant.password.manager.Model.AddServiceModel;
import com.webant.password.manager.Model.ModelCallback;

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


    public void createService(String name, String url, String login, String pass, String token, String token_name) {
        model.createService(new ModelCallback.Callback() {
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
            public void onError(int code, String message) {
                new ConverterErrorResponse(code, message).sendMessage(view.getApplicationContext());
                view.cancelRefresh();
            }
        }, view.getApplicationContext(), name, url, login, pass, token, token_name);
    }

    public void editService(int id, String name, String url, String login, String pass, String token, String token_name) {
        model.editService(new ModelCallback.Callback() {
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
            public void onError(int code, String s) {
                new ConverterErrorResponse(code, s).sendMessage(view.getApplicationContext());
                view.cancelRefresh();
            }
        }, view.getApplicationContext(), id, name, url, login, pass, token, token_name);
    }

    public void loadService(int id) {
        model.loadService(new AddServiceModel.LoadServiceCallback() {
            @Override
            public void onLoad(GetService_Items item) {
                view.loadDefault(item);
            }

            @Override
            public void onFail() {
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
                view.execute();
            }

            @Override
            public void onError(int code, String s) {
                new ConverterErrorResponse(code, s).sendMessage(view.getApplicationContext());
                view.execute();
            }
        }, view.getApplicationContext(), id);
    }
}
