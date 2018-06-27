package com.webant.password.manager.Adapters.Model;

import android.content.Intent;

public class SelectedShareItems {
    int id=0;
    String key="";
    public SelectedShareItems(int id, String key){
        this.id=id;
        this.key=key;
    }

    public String getKey() {
        return key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
