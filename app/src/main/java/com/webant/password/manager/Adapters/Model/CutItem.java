package com.webant.password.manager.Adapters.Model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dikiy on 28.04.2018.
 */

@SuppressLint("ParcelCreator")
public class CutItem implements Parcelable {
    int id;
    boolean type;
    int storageid;
    public  CutItem(int id,boolean type,int storageid){
        this.id=id;
        this.type=type;
        this.storageid=storageid;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStorageid() {
        return storageid;
    }

    public void setType(boolean type) {
        this.type = type;
    }
    public boolean getType(){
        return type;
    }
    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
