package com.example.dikiy.passwordmain.PasswordActivityPac;

import com.example.dikiy.passwordmain.RecyclerView.RecyclerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 16.02.2018.
 */

public class PasswordList {
    String name;
    String url;
    String login;
    String password;
    List<RecyclerItem> movieList= new ArrayList<>();
    List<RecyclerItem> movieList2 = new ArrayList<>();
    String log;
    public PasswordList(String name,String url,String login,String password,List<RecyclerItem> movieList,List<RecyclerItem> movieList2,String log) {
        this.name = name;
        this.url =url;
        this.login =login;
        this.password = password;
        this.movieList.addAll(movieList);
        this.movieList2.addAll(movieList2);
        this.log=log;
    }

    public String getName() {
        return name;
    }

    public List<RecyclerItem> getMovieList() {
        return movieList;
    }

    public List<RecyclerItem> getMovieList2() {
        return movieList2;
    }

    public String getLog() {
        return log;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMovieList(List<RecyclerItem> movieList) {
        this.movieList = movieList;
    }

    public void setMovieList2(List<RecyclerItem> movieList2) {
        this.movieList2 = movieList2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
