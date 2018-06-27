package com.webant.password.manager.Adapters.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPass_Item_Folder {

    @SerializedName("id")
    @Expose
    private Integer id=0;
    @SerializedName("name")
    @Expose
    private String name=null;
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


}
