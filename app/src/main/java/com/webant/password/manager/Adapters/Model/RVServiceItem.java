package com.webant.password.manager.Adapters.Model;

public class RVServiceItem {
    int id;
    String name;
    String url;
    public RVServiceItem(int id,String name,String url){
        this.id=id;
        this.name=name;
        this.url=url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
