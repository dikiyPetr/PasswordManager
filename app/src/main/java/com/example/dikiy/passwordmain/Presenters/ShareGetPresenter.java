package com.example.dikiy.passwordmain.Presenters;

import com.example.dikiy.passwordmain.Adapters.Get.GetPass_Item;
import com.example.dikiy.passwordmain.Adapters.Model.ShareGetItems;
import com.example.dikiy.passwordmain.Model.ModelCallback;
import com.example.dikiy.passwordmain.Model.ShareGetModel;
import com.example.dikiy.passwordmain.ShareGetActivity;

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


    public void viewIsReady(List<String> list) {
        model.passwordGet(new ShareGetModel.CallbackPassword() {
            @Override
            public void onLoad(GetPass_Item item, int id) {

                view.addCard(item,id);
            }

            @Override
            public void onError(String s) {

            }
        },view.getApplicationContext(),list);
    }

    public void addPassword(ShareGetItems shareGetItem,int id) {
        model.passwordAdd(new ModelCallback.CallbackId() {
            @Override
            public void onLoad(int i) {
                view.deleteCart(i);
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onError() {

            }
        },view.getApplicationContext(),shareGetItem, id);
    }
}
