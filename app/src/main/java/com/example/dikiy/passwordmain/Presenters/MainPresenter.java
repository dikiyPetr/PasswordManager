package com.example.dikiy.passwordmain.Presenters;

import android.content.Context;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Model.CutItem;
import com.example.dikiy.passwordmain.Model.MainModel;
import com.example.dikiy.passwordmain.MainActivity;
import com.example.dikiy.passwordmain.Model.ModelCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 14.02.2018.
 */

public class MainPresenter {
    private MainActivity view;
    private final MainModel model;
    private List<Integer> way=new ArrayList<>();
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
    },view.getApplicationContext());
    }

    public Boolean backWay(){
        if(way.size()!=1) {
            way.remove(way.size() - 1);
            loadUsers();
        return true;
        }
        return false;
    }
    public void nextWay(int id,String name){
        way.add(id);
        view.changeWay(name);
        loadUsers();
    }
    public int getWay(){
        return way.get(way.size()-1);
    }
    public void refreshList() {

        model.refreshBd(new MainModel.RefreshBDCallback() {
            @Override
            public void onLoad() {
                loadUsers();
            }

            @Override
            public void onFail() {
                view.failRefresh();
            }
        },view.getApplicationContext());
    }
    public void editItem(){


    }
    public void loadUsers() {
        view.refreshClose();
        view.showUsers();
}

    public void deleteItem(int id,boolean type) {
        view.refreshStart();
      model.deleteItem(new MainModel.DeleteItemCallback() {
          @Override
          public void onLoad() {
              view.refreshClose();

                  loadUsers();


          }
      },view.getApplicationContext(),id,type);

    }

        public void moveItem(final List<CutItem> cutItems, int folderId) {
        view.refreshStart();
            final int[] i = {0};
        model.moveItem(new MainModel.MoveItemCallback() {
            @Override
            public void onLoad() {
                i[0]++;
                if(i[0]==cutItems.size())
                refreshList();

            }
        },view.getApplicationContext(),cutItems,folderId);
    }

    public void addPasswordInStorage(String id,String clue) {
        model.addPasswordInStorage(new ModelCallback.Callback() {
            @Override
            public void onLoad() {

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onError(String s) {

            }
        }, view.getApplicationContext(),id,clue);
    }
}
