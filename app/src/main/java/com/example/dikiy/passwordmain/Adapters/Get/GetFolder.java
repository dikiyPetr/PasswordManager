package com.example.dikiy.passwordmain.Adapters.Get;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class  GetFolder {


    @SerializedName("count")
    @Expose
    private Integer count=0;

    @SerializedName("items")
    @Expose
    private List<GetFolder_Item> items = null;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }



    public List<GetFolder_Item> getItems() {
        return items;
    }

    public void setItems(List<GetFolder_Item> items) {
        this.items = items;
    }

}