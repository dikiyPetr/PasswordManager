package com.example.dikiy.passwordmain.Adapters.Get;

import android.content.Intent;
import android.view.KeyEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPass_Item {

    @SerializedName("pass")
    @Expose
    private String pass;
    @SerializedName("id")
    @Expose
    private Integer id=0;
    @SerializedName("users")
    @Expose
    private List<Object> users = new ArrayList<>();
    @SerializedName("name")
    @Expose
    private String name="";
    @SerializedName("folder")
    @Expose
    private GetPass_Item_Folder folder=null;
    @SerializedName("login")
    @Expose
    private String login="";
    @SerializedName("url")
    @Expose
    private String url="";
    @SerializedName("description")
    @Expose
    private String description="";
    @SerializedName("tag")
    @Expose
    private List<GetTag_Item> tag = new ArrayList<>();
    @SerializedName("group")
    @Expose
    private List<GetTag_Item> group = new ArrayList<>();

    private List<String> tags=new ArrayList<>();
    private List<String> groups = new ArrayList<>();
    public GetPass_Item(String pass,String name,String login,String url,String description,String tag,String group){
    this.pass=pass;
    this.name=name;
        this.url=url;
    this.login=login;
        tags.addAll(Arrays.asList(tag.split(",")));
        groups.addAll(Arrays.asList(group.split(",")));
        this.description=description;



    }

    public List<String> getGroups() {
        return groups;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
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
    public void setTag(List<GetTag_Item> tag) {

        this.tag = tag;
    }
    public String getTag() {
        String s="";
        for(int i=0;i<tag.size();i++){
            s=s+tag.get(i).getId().toString()+",";
        }
        if(s.length()!=0) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }



    public String getGroup() {
        String s="";
        for(int i=0;i<group.size();i++){
            s=s+group.get(i).getId().toString()+",";
        }
        if(s.length()!=0) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    public void setGroup(List<GetTag_Item> group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}