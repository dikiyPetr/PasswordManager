package com.example.dikiy.passwordmain.Adapters.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFolder_Item_Group {


    @SerializedName("id")
    @Expose
    private Integer id= null;

    @SerializedName("name")
    @Expose
    private String name = null;


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}