package com.example.dikiy.passwordmain.Adapters.Model;

/**
 * Created by dikiy on 28.04.2018.
 */

public class FlyVItem {
    String url;
    String login;
    String pass;
    public FlyVItem( String url,String login,String pass){
        this.url=url;
        this.login=login;
        this.pass=pass;
    }

    public String getPass() {
        return pass;
    }

    public String getUrl() {
        return url;
    }

    public String getLogin() {
        return login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
