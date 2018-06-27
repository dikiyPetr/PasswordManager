package com.webant.password.manager.Adapters.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webant.password.manager.Adapters.Get.GetService_Items_Commands;

import java.util.ArrayList;
import java.util.List;

public class GetCommand {
    @SerializedName("items")
    @Expose
    private List<GetService_Items_Commands> items = new ArrayList<>();
    @SerializedName("count")
    @Expose
    private Integer count=0;

    public Integer getCount() {
        return count;
    }

    public List<GetService_Items_Commands> getItems() {
        return items;
    }

    public void setItems(List<GetService_Items_Commands> items) {
        this.items = items;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
