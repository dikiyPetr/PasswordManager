package com.webant.password.manager.Presenters;


import com.webant.password.manager.Adapters.Model.CutItem;
import com.webant.password.manager.Adapters.Model.DeleteItems;
import com.webant.password.manager.Adapters.Model.SelectedShareItems;
import com.webant.password.manager.ConverterErrorResponse;
import com.webant.password.manager.MainActivity;
import com.webant.password.manager.Model.MainModel;
import com.webant.password.manager.Model.ModelCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 14.02.2018.
 */

public class MainPresenter {
    private MainActivity view;
    private final MainModel model;
    private List<Integer> way = new ArrayList<>();

    public MainPresenter(MainModel model) {
        way.add(0);
        this.model = model;
    }

    public void attachView(MainActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }

    public void viewIsReady() {
        getUser();
        refreshList();
    }

    private void getUser() {
        model.GetUser(new MainModel.RefreshBDCallback() {
            @Override
            public void onLoad() {
                view.setUserInfo();
            }

            @Override
            public void onFail() {
            }
        }, view.getApplicationContext());
    }

    public Boolean backWay() {
        if (way.size() != 1) {
            way.remove(way.size() - 1);
            view.showItems();
            return true;
        }
        return false;
    }

    public void nextWay(int id, String name) {
        way.add(id);
        view.changeWay(name);
        view.showItems();
    }

    public int getWay() {
        return way.get(way.size() - 1);
    }

    public void refreshList() {
        model.refreshBd(new ModelCallback.Callback() {
            @Override
            public void onLoad() {
                view.showItems();
            }

            @Override
            public void onFail() {
                view.showItems();
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
            }

            @Override
            public void onError(int code, String s) {
                view.refreshClose();
                new ConverterErrorResponse(code, s).sendMessage(view.getApplicationContext());
            }
        }, view.getApplicationContext());
    }


    public void moveItem(final List<CutItem> cutItems, int folderId) {
        view.refreshStart();
        final int[] i = {0};
        model.moveItem(new MainModel.MoveItemCallback() {
            @Override
            public void onLoad() {
                i[0]++;
                if (i[0] == cutItems.size())
                    refreshList();

            }
        }, view.getApplicationContext(), cutItems, folderId);
    }

    public void givePass(final List<SelectedShareItems> list, final String mail) {
        model.sendPassToUser(new ModelCallback.Callback() {
            @Override
            public void onLoad() {
                view.sendPass(list, mail);
            }

            @Override
            public void onFail() {
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
            }

            @Override
            public void onError(int code, String s) {
                new ConverterErrorResponse(code, s).sendMessageSharePass(view.getApplicationContext());
            }
        }, view.getApplicationContext(), list, mail);
    }

    public void deleteItem(List<DeleteItems> deleteItems) {
        view.refreshStart();
        model.deleteItem(new ModelCallback.Callback() {
            @Override
            public void onLoad() {
                view.refreshClose();
                view.showItems();
            }

            @Override
            public void onFail() {
                view.refreshClose();
                ConverterErrorResponse.connectionFailed(view.getApplicationContext());
            }

            @Override
            public void onError(int code, String s) {
                view.refreshClose();
                new ConverterErrorResponse(code, s).deleteItemsMessage(view.getApplicationContext());
            }

        }, view.getApplicationContext(), deleteItems);
    }
}
