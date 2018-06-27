package com.webant.password.manager.Adapters.Model;

public class ParamsItem {
    private int id=0;
    private String title="";
    private String value="";
    public ParamsItem(int id,String name){
        this.id=id;
        this.title=name;

    }
    public String getName() {
        return title;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.title = name;
    }

    public int getId() {
        return id;
    }
}
