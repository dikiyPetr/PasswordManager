package com.webant.password.manager.Presenters;

import android.view.View;

import com.webant.password.manager.Adapters.Get.GetPass_Item;
import com.webant.password.manager.Adapters.Model.ShareGetItems;
import com.webant.password.manager.ConverterErrorResponse;
import com.webant.password.manager.Model.ModelCallback;
import com.webant.password.manager.Model.ShareGetModel;
import com.webant.password.manager.ShareGetActivity;

import java.util.List;

public class ShareGetPresenter {
    private ShareGetActivity view;
    private final ShareGetModel model;

    public ShareGetPresenter(ShareGetModel model) {
        this.model = model;
    }

    public void attachView(ShareGetActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }


    public void viewIsReady(final List<String> list) {
        view.setRefresh(true);
        model.getMyPasswor(new ModelCallback.Callback() {
            @Override
            public void onLoad() {
                getSharedPass(list);
            }

            @Override
            public void onFail() {
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
                view.setRefresh(false);
            }

            @Override
            public void onError(int code,String s) {
               new ConverterErrorResponse(code,s).sendMessage(view.getApplicationContext());
                view.setRefresh(false);
            }
        }, view.getApplicationContext());
    }

    public void getSharedPass(List<String> list) {
        model.getSharedPassword(new ShareGetModel.CallbackPassword() {
            @Override
            public void onLoad(GetPass_Item item, int id) {
                view.addCard(item, id);
            }

            @Override
            public void onError(String s) {
                view.setRefresh(false);
            }
        }, view.getApplicationContext(), list);
    }

    public void addPassword(ShareGetItems shareGetItem, int id) {
        view.setRefresh(true);
        model.passwordAdd(new ModelCallback.CallbackId() {
            @Override
            public void onLoad(int i) {
                view.deleteCart(i);
            }

            @Override
            public void onFail() {
                view.fail();
            }

            @Override
            public void onError() {
                view.error();
            }
        }, view.getApplicationContext(), shareGetItem, id);
    }
}
