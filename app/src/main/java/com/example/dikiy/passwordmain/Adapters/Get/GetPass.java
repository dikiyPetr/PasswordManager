package com.example.dikiy.passwordmain.Adapters.Get;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPass {

    @SerializedName("items")
    @Expose
    private List<GetPass_Item> items = new ArrayList<>();
    @SerializedName("count")
    @Expose
    private Integer count=0;


    public List<GetPass_Item> getItems() {
        return items;
    }

    public void setItems(List<GetPass_Item> items) {
        this.items = items;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


}