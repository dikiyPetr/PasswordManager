package com.example.dikiy.passwordmain.Adapters.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTag {


    @SerializedName("count")
    @Expose
    private Integer count=0;

    @SerializedName("items")
    @Expose
    private List<GetTag_Item> items = null;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }



    public List<GetTag_Item> getItems() {
        return items;
    }

    public void setItems(List<GetTag_Item> items) {
        this.items = items;
    }

}