package com.webant.password.manager.Adapters.Model;

public class DeleteItems {
    int id;
    boolean mode;
    public DeleteItems(int id,boolean mode){
        this.id=id;
        this.mode=mode;
    }

    public int getId() {
        return id;
    }
    public boolean getMode(){
        return mode;
    }
}
