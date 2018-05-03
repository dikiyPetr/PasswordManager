package com.example.dikiy.passwordmain.Adapters.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dikiy on 25.04.2018.
 */

public class GetStorage_Item {


    @SerializedName("clue")
    @Expose
    private String clue;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pass")
    @Expose
    private GetPass_Item pass;

    public String getClue() {
        return clue;
    }

    public void setClue(String clue) {
        this.clue = clue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GetPass_Item getPass() {
        return pass;
    }

    public void setPass(GetPass_Item pass) {
        this.pass = pass;
    }
}
