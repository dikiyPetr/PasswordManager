package com.example.dikiy.passwordmain.Adapters.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetService {
    @SerializedName("items")
    @Expose
    private List<GetService_Items> items = new ArrayList<>();
    @SerializedName("count")
    @Expose
    private Integer count=0;


    public List<GetService_Items> getItems() {
        return items;
    }

    public void setItems(List<GetService_Items> items) {
        this.items = items;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
