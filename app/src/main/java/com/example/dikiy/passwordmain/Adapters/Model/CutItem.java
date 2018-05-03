package com.example.dikiy.passwordmain.Adapters.Model;

/**
 * Created by dikiy on 28.04.2018.
 */

public class CutItem {
    int id;
    boolean type;
    public  CutItem(int id,boolean type){
        this.id=id;
        this.type=type;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(boolean type) {
        this.type = type;
    }
    public boolean getType(){
        return type;
    }
    public int getId() {
        return id;
    }

}
