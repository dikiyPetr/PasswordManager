package com.example.dikiy.passwordmain.Presenters;

import android.graphics.ColorSpace;

import com.example.dikiy.passwordmain.Model.PasswordModel;
import com.example.dikiy.passwordmain.ItemModel.PasswordList;
import com.example.dikiy.passwordmain.PasswordActivity;

import java.lang.reflect.Array;
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


    public void viewIsReady(boolean b,int i) {
       if(b) {
           loadUsers(i);
       }
       }
    public void clickAddPassword(){addPassword();}

    private void addPassword() {
    }

    public void loadUsers(int i) {
        model.loadUsers(new PasswordModel.LoadUserCallback() {

            @Override
            public void onLoad(String pass) {
                view.loadPass(pass);
            }

            @Override
            public void onError(int code) {
                if(code==0){
                    view.callError("internet ne internet");
                }else if(code==1){
                    view.callError("secretKet ne secretKey");
                }else {
                    view.callError("");
                }
            }
        },i);
    }
    public void createPass(String name, int folder, String url, String pass, String login, String description, List<String> tags, List<String> groups){
        model.CreatePass(new PasswordModel.CreatePassCallback() {
            @Override
            public void onLoad(int stat) {
                view.execute();
            }
        }, name, String.valueOf(folder),url, pass, login, description,tags,groups);
    }

    public void updatePass(Integer i, String s, String s1, String s2, String s3) {
        model.UpdatePass(new PasswordModel.UpdatePassCallback() {
            @Override
            public void onLoad(int code) {

            }
        },i, s, s1, s2, s3);
    }

    public void addTagOrGroup(final boolean tag, String s, int passid) {

            model.AddTag(new PasswordModel.AddTagCallback() {
                @Override
                public void onLoad(String s) {
                    view.setRefreshStatus();
                }
            },tag,s,passid);


    }

    public void removeTagOrGroup(String s,int id,boolean b) {
        model.RemoveTag(new PasswordModel.RemoveTagCallback() {
            @Override
            public void onLoad() {
                view.setRefreshStatus();
            }
        },s,id,b);
    }
}
