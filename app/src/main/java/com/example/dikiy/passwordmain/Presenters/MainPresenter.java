package com.example.dikiy.passwordmain.Presenters;

import com.example.dikiy.passwordmain.Model.MainModel;
import com.example.dikiy.passwordmain.MainActivity;
import com.example.dikiy.passwordmain.ItemModel.MainItem;


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

        refreshUsers();
    }
    public Boolean backWay(){
        if(way.size()!=1) {
            way.remove(way.size() - 1);
            loadUsers();
        return true;
        }
        return false;
    }
    public void nextWay(int id){
        way.add(id);
        loadUsers();
    }
    public void refreshUsers() {
        model.refreshBd(new MainModel.RefreshBDCallback() {
            @Override
            public void onLoad(Boolean b) {
                if(b){
             loadUsers();
                } else{
                    view.fail();
                }
            }
        });
    }
    public void loadUsers() {

                model.loadUsers(new MainModel.LoadUserCallback() {
                    @Override
                    public void onLoad(List<MainItem> users) {

                        view.showUsers(users);
                    }
                },way.get(way.size()-1));

}}
