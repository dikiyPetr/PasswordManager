package com.example.dikiy.passwordmain.Adapters.Get;

/**
 * Created by dikiy on 27.02.2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFolder_Item_Pass {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("users")
    @Expose
    private Integer users;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("pass")
    @Expose
    private String pass;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("description")
    @Expose
    private String description;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getUsers() {
        return users;
    }

    public String getDescription() {
        return description;
    }

    public String getGroup() {
        return group;
    }

    public String getTag() {
        return tag;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setUsers(Integer users) {
        this.users = users;
    }
}