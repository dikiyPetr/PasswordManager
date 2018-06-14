package com.example.dikiy.passwordmain.Adapters.Model;

import com.example.dikiy.passwordmain.Adapters.Get.GetFolder_Item;

import java.util.ArrayList;
import java.util.List;

public class ShareGetItems {
    int id=0;
    String name="";
    String url="";
    String login="";
    String clue="";
    int way=0;
    public ShareGetItems(int id,String name,String url,String login,String clue){
        this.id=id;
        this.name=name;
        this.url=url;
        this.login=login;
        this.clue=clue;
    }

    public void setWay(int way) {
        this.way = way;
    }

    public int getWay() {
        return way;
    }

    public String getClue() {
        return clue;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    public String getLogin() {
        return login;
    }

    public String getUrl() {
        return url;
    }

    public void setClue(String clue) {
        this.clue = clue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
