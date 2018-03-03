package com.example.dikiy.passwordmain.Main;

import com.example.dikiy.passwordmain.MainActivity;
import com.example.dikiy.passwordmain.MainRecycler.MainItem;


import java.util.List;

/**
 * Created by dikiy on 14.02.2018.
 */

public class MainPresenter {
    private MainActivity view;
    private final MainModel model;

    public MainPresenter(MainModel model) {
        this.model = model;
    }

    public void attachView(MainActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }


    public void viewIsReady() {

        loadUsers();
    }

    public void loadUsers() {
        model.refreshBd(new MainModel.RefreshBDCallback() {
            @Override
            public void onLoad() {
                model.loadUsers(new MainModel.LoadUserCallback() {
                    @Override
                    public void onLoad(List<MainItem> users) {
                        view.showUsers(users);
                    }
                });
            }
        });

}}
