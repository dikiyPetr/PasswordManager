package com.example.dikiy.passwordmain.Adapters.Get;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFolder_Item {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pass")
    @Expose
    private ArrayList<GetFolder_Item_Pass> pass = null;
    @SerializedName("children")
    @Expose
    private ArrayList<GetFolder_Item_Children> children = null;

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

    public List<Integer> getPass() {


        List<Integer> ret=new ArrayList<>();
        for(int i=0;i<pass.size();i++){
            ret.add(pass.get(i).getId());
        }

        return ret;
    }

    public void setPass(ArrayList<GetFolder_Item_Pass> pass) {
        this.pass = pass;
    }

    public List<Integer> getChildren() {
        List<Integer> ret=new ArrayList<>();
        for(int i=0;i<children.size();i++){
            ret.add(children.get(i).getId());
        }

        return ret;
    }

    public void setChildren(ArrayList<GetFolder_Item_Children> children) {
        this.children = children;
    }

}