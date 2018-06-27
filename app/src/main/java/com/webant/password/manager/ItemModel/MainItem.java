package com.webant.password.manager.ItemModel;

public class MainItem {
    String name;
    boolean type;
    int storageId;
    int amountParent=0;
    int id;
    boolean stat=false;
    String tag="";




    public MainItem(String name,int id,boolean type,String tag,int storageId,int amountParent) {
        this.name = name;
        this.id=id;
        this.type=type;
        this.tag=tag;
        this.storageId=storageId;
        this.amountParent=amountParent;
    }

    public MainItem(int id, boolean type) {
        this.id=id;
        this.type=type;
    }

    public int getAmountParent() {
        return amountParent;
    }

    public int getStorageId() {
        return storageId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
    public boolean getStat(){
        return stat;
    }

    public void setStat(boolean stat) {
        this.stat = stat;
    }

    public boolean switchStat() {
       if(stat){stat=false;}else{stat=true;};
       return stat;
    }
}