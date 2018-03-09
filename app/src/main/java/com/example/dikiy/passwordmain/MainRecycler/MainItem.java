package com.example.dikiy.passwordmain.MainRecycler;

public class MainItem {
    String name;
    String tag;
    String data;
    int id;
    int child;
    int st;
    int imageres;


    public MainItem(String name, String tag, String data, int st,int id, int imageres,int child) {
        this.name = name;
        this.tag = tag;
        this.data= data;
        this.st = st;
        this.child = child;
        this.imageres = imageres;
        this.id=id;
    }

    public MainItem(String name) {
        this.name = name;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
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

    public int getSt() {
        return st;
    }

    public String getData() {
        return data;
    }

    public String getTag() {
        return tag;
    }

    public void setimageres(int id) {
        this.imageres= id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSt(int st) {
        this.st = st;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getImageres() {
        return imageres;
    }
}