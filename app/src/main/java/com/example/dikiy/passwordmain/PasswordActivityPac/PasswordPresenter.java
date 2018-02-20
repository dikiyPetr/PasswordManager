package com.example.dikiy.passwordmain.PasswordActivityPac;

import com.example.dikiy.passwordmain.MainActivityPac.MainModel;
import com.example.dikiy.passwordmain.MainRecycler.MainItem;
import com.example.dikiy.passwordmain.PasswordActivity;

import java.util.List;

/**
 * Created by dikiy on 16.02.2018.
 */

public class PasswordPresenter {
    private PasswordActivity view;
    private final PasswordModel model;

    public PasswordPresenter(PasswordModel model) {
        this.model = model;
    }

    public void attachView(PasswordActivity usersActivity) {
        view = usersActivity;
    }

    public void detachView() {
        view = null;
    }


    public void viewIsReady() {
        loadUsers();
    }

    public void loadUsers() {
        model.loadUsers(new PasswordModel.LoadUserCallback() {
            @Override
            public void onLoad(PasswordList users) {
                view.showlist2(users);
                view.showlist1(users);
            }

        });
    }
}
