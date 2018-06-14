package com.example.dikiy.passwordmain.Adapters.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dikiy on 25.04.2018.
 */

public class GetStorage {
    @SerializedName("items")
    @Expose
    private List<GetStorage_Item> items = null;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("_time")
    @Expose
    private Double time;




    public List<GetStorage_Item> getItems() {
        return items;
    }

    public void setItems(List<GetStorage_Item> items) {
        this.items = items;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
