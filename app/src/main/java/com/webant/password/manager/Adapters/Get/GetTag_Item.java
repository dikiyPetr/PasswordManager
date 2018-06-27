package com.webant.password.manager.Adapters.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTag_Item {


    @SerializedName("id")
    @Expose
    private Integer id=0;

    @SerializedName("name")
    @Expose
    private String name="";


    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}