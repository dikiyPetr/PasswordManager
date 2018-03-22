package com.example.dikiy.passwordmain.Adapters.Get;

import android.content.Intent;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPass_Item {

    @SerializedName("pass")
    @Expose
    private Boolean pass=false;
    @SerializedName("id")
    @Expose
    private Integer id=0;
    @SerializedName("users")
    @Expose
    private List<Object> users = new ArrayList<>();
    @SerializedName("name")
    @Expose
    private String name=null;
    @SerializedName("folder")
    @Expose
    private GetPass_Item_Folder folder=null;
    @SerializedName("login")
    @Expose
    private String login=null;
    @SerializedName("url")
    @Expose
    private String url=null;
    @SerializedName("tag")
    @Expose
    private List<Object> tag = new ArrayList<>();
    @SerializedName("group")
    @Expose
    private List<Object> group = new ArrayList<>();

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Object> getUsers() {
        return users;
    }

    public void setUsers(List<Object> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFolder() {
        if(folder==null){return 0;}
        return folder.getId();
    }

    public void setFolder(GetPass_Item_Folder folder) {
        this.folder = folder;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Object> getTag() {
        return tag;
    }

    public void setTag(List<Object> tag) {
        this.tag = tag;
    }

    public List<Object> getGroup() {
        return group;
    }

    public void setGroup(List<Object> group) {
        this.group = group;
    }

}