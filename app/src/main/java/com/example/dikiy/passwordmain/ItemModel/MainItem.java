package com.example.dikiy.passwordmain.ItemModel;

public class MainItem {
    String name;
    boolean type;
    int id;





    public MainItem(String name,int id,boolean type) {
        this.name = name;
        this.id=id;
        this.type=type;
    }


    public void setType(boolean type) {
        this.type = type;
    }
    public boolean getType(){
        return type;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }


}