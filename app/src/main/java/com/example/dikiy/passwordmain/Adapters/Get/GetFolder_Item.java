package com.example.dikiy.passwordmain.Adapters.Get;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetFolder_Item {

    @SerializedName("id")
    @Expose
    private Integer id = 0;
    @SerializedName("name")
    @Expose
    private String name = "";
    @SerializedName("parent_id")
    @Expose
    private String parent;
    @SerializedName("tag")
    @Expose
    private List<GetTag_Item> tag = new ArrayList<>();
    @SerializedName("group")
    @Expose
    private List<GetTag_Item> group = new ArrayList<>();

    private List<String> tags = new ArrayList<>();
    private List<String> groups = new ArrayList<>();

    public GetFolder_Item(String name, String tag, String group) {

        this.name = name;
        tags.addAll(Arrays.asList(tag.split(",")));
        groups.addAll(Arrays.asList(group.split(",")));


    }

    public GetFolder_Item() {

    }

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

        if (parent == null) {

            return 0;
        }
        return Integer.valueOf(parent);
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getTag() {
        String s = "";
        for (int i = 0; i < tag.size(); i++) {
            s = s + tag.get(i).getId().toString() + ",";
        }
        if (s.length() != 0) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }


    public List<String> getGroups() {
        return groups;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getGroup() {
        String s = "";
        for (int i = 0; i < group.size(); i++) {
            s = s + group.get(i).getId().toString() + ",";
        }
        if (s.length() != 0) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    public void setGroup(List<GetTag_Item> group) {
        this.group = group;
    }

    public void setTag(List<GetTag_Item> tag) {
        this.tag = tag;
    }
}