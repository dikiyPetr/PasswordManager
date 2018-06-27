package com.webant.password.manager.Adapters.Model;

/**
 * Created by dikiy on 28.04.2018.
 */

public class CutItem {
    int id;
    boolean type;
    int storageid;
    public  CutItem(int id,boolean type,int storageid){
        this.id=id;
        this.type=type;
        this.storageid=storageid;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStorageid() {
        return storageid;
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
