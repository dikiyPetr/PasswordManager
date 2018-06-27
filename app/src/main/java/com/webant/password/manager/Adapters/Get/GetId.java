package com.webant.password.manager.Adapters.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dikiy on 27.02.2018.
 */

public class GetId {
    @SerializedName("id")
    @Expose
    private Integer id=0;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
