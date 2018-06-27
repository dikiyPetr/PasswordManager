package com.webant.password.manager.Adapters.Get;

import android.util.Log;

import com.webant.password.manager.Adapters.Get.GetTag_Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetPass_Item {
    private Integer id = 0;
    private List<Object> users = new ArrayList<>();
    private String name = "";
    private String login = "";
    private String pass = "";
    private String url = "";
    private List<GetTag_Item> tag = new ArrayList<>();
    private List<GetTag_Item> group = new ArrayList<>();
    private String description = "";

    private List<String> tags = new ArrayList<>();
    private List<String> groups = new ArrayList<>();
    private String clue;
    public GetPass_Item(String pass, String name, String login, String url, String description, String tag, String group,String clue) {
        this.pass = pass;
        this.name = name;
        this.url = url;
        this.login = login;
        this.clue=clue;
        tags.addAll(Arrays.asList(tag.split(",")));
        groups.addAll(Arrays.asList(group.split(",")));
        this.description = description;


    }

    public String getClue() {
        return clue;
    }

    public void setClue(String clue) {
        this.clue = clue;
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
        String s = "";
        if (tag != null) {
            for (int i = 0; i < tag.size(); i++) {
                Log.v("testLog",tag.get(i).getId().toString());
                s = s + tag.get(i).getId().toString() + ",";
            }
            if (s.length() != 0) {
                s = s.substring(0, s.length() - 1);
            }
        }

        return s;
    }


    public String getGroup() {
        String s = "";
        if (group != null) {
            for (int i = 0; i < group.size(); i++) {
                s = s + group.get(i).getId().toString() + ",";
            }
            if (s.length() != 0) {
                s = s.substring(0, s.length() - 1);
            }
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