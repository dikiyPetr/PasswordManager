package com.webant.password.manager.ItemModel;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.webant.password.manager.DBase.DBWorker;

import java.util.Arrays;
import java.util.List;

@SuppressLint("ParcelCreator")
public class MainItem implements Parcelable {
    String name;
    boolean type;
    int storageId;
    int amountParent = 0;
    int id;
    boolean stat = false;
    String tag = "";


    public MainItem(String name, int id, boolean type, String tag, int storageId, int amountParent, DBWorker dbWorker) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.tag = tag;
        this.storageId = storageId;
        this.amountParent = amountParent;
        if (!tag.equals("") && type) {
            List<String> list = dbWorker.getTagName(Arrays.asList(tag.split(",")));
            List<String> tags = Arrays.asList(list.toString().substring(1, list.toString().length() - 1).split(","));
            String stringTag = "";
            for (int i1 = 0; i1 < tags.size(); i1++)
                if (!tags.get(i1).isEmpty())
                    stringTag += "#" + tags.get(i1) + " ";
            this.tag = stringTag;
        }
    }

    public MainItem(int id, boolean type) {
        this.id = id;
        this.type = type;
    }

    public int getAmountParent() {
        return amountParent;
    }

    public int getStorageId() {
        return storageId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public boolean getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public boolean getStat() {
        return stat;
    }

    public void setStat(boolean stat) {
        this.stat = stat;
    }

    public boolean switchStat() {
        if (stat) {
            stat = false;
        } else {
            stat = true;
        }
        ;
        return stat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}