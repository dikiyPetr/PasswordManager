package com.webant.password.manager.Adapters.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.webant.password.manager.Adapters.Get.GetService_Items_Commands;

import java.util.ArrayList;
import java.util.List;

public class GetService_Items {
    @SerializedName("id")
    @Expose
    private int id=0;
    @SerializedName("name")
    @Expose
    private String name="";
    @SerializedName("url")
    @Expose
    private String url="";
    @SerializedName("login")
    @Expose
    private String login="";
    @SerializedName("pass")
    @Expose
    private String pass="";
    @SerializedName("token")
    @Expose
    private String token="";
    @SerializedName("token_name")
    @Expose
    private String   token_name="";
    @SerializedName("commands")
    @Expose
    private List<GetService_Items_Commands> commands=new ArrayList<>();
    @SerializedName("token_bool")
    @Expose
    private boolean token_bool=false;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public List<GetService_Items_Commands> getCommands() {
        return commands;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getToken() {
        return token;
    }

    public String getToken_name() {
        return token_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommands(List<GetService_Items_Commands> commands) {
        this.commands = commands;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setToken_bool(boolean token_bool) {
        this.token_bool = token_bool;
    }

    public void setToken_name(String token_name) {
        this.token_name = token_name;
    }
}
