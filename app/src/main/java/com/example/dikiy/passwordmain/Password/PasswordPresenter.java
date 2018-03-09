package com.example.dikiy.passwordmain.Password;

import com.example.dikiy.passwordmain.PasswordActivity;

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
    public void clickAddPassword(){addPassword();}

    private void addPassword() {
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
