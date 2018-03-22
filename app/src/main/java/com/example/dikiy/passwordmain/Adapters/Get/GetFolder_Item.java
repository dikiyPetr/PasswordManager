package com.example.dikiy.passwordmain.Adapters.Get;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFolder_Item {

    @SerializedName("id")
    @Expose
    private Integer id=0;
    @SerializedName("name")
    @Expose
    private String name=null;
    @SerializedName("parent")
    @Expose
    private GetId parent =null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent() {
        if(parent==null){return 0;}
        return parent.getId();
    }

    public void setParent(GetId parent) {
        this.parent = parent;
    }

}