package com.example.dikiy.passwordmain.Presenters;

import com.example.dikiy.passwordmain.Model.ModelCallback;
import com.example.dikiy.passwordmain.Model.PasswordModel;
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


    public void viewIsReady(boolean b,int i) {
       }
    public void clickAddPassword(){addPassword();}

    private void addPassword() {
    }

    public void createPass(String name, int folder, String url, String pass, String login, String description, List<String> tags, List<String> groups){
        model.CreatePass(new PasswordModel.CreatePassCallback() {
            @Override
            public void onLoad() {
                view.execute();
            }

            @Override
            public void onFail(String s) {
                view.callError(s);
            }
        },view.getApplicationContext(), name, String.valueOf(folder),url, pass, login, description,tags,groups);
    }

    public void updatePass(Integer i, String s, String s1, String s2, String s3) {
        model.UpdatePass(new PasswordModel.UpdatePassCallback() {
            @Override
            public void onLoad() {

                view.modeEdit(0);
            }

            @Override
            public void onError(String s) {
                view.callError(s);
                view.modeEdit(1);
            }
        },view.getApplicationContext(),i, s, s1, s2, s3);
    }

    public void addTagOrGroup(final boolean tag, String s, int passid) {

            model.AddTagOrGroup(new PasswordModel.AddTagCallback() {
                @Override
                public void onLoad(String s) {
                    view.setRefreshStatus();
                }
            },view.getApplicationContext(),tag,s,passid);


    }

    public void removeTagOrGroup(String s,int id,boolean b) {
        model.RemoveTag(new PasswordModel.RemoveTagCallback() {
            @Override
            public void onLoad() {
                view.setRefreshStatus();
            }
        },view.getApplicationContext(),s,id,b);
    }

    public void givePass(int folder,String email) {
        model.givePass(new ModelCallback.Callback() {
            @Override
            public void onLoad() {
                view.sendMesageToUser();
            }

            @Override
            public void onFail() {
                view.fail();
            }

            @Override
            public void onError(String s) {
                view.error(s);
            }
        },view.getApplicationContext(),folder,email);
    }
}
