package com.example.dikiy.passwordmain.Adapters.Model;

/**
 * Created by dikiy on 13.04.2018.
 */

public class SearchItem {
    String name;
    int id;
    public SearchItem(int id,String name ){

        this.id=id;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
