package com.example.dikiy.passwordmain.Adapters.Get;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFolder {

    @SerializedName("_query_time")
    @Expose
    private Double queryTime;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("_count_query_time")
    @Expose
    private Double countQueryTime;
    @SerializedName("items")
    @Expose
    private List<GetFolder_Item> items = null;

    public Double getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Double queryTime) {
        this.queryTime = queryTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getCountQueryTime() {
        return countQueryTime;
    }

    public void setCountQueryTime(Double countQueryTime) {
        this.countQueryTime = countQueryTime;
    }

    public List<GetFolder_Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<GetFolder_Item> items) {
        this.items = items;
    }

}