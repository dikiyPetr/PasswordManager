package com.webant.password.manager.DBase;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.webant.password.manager.Adapters.Get.GetFolder_Item;
import com.webant.password.manager.Adapters.Get.GetPass_Item;
import com.webant.password.manager.Adapters.Get.GetStorage;
import com.webant.password.manager.Adapters.Get.GetStorage_Item;
import com.webant.password.manager.Adapters.Get.GetTag_Item;
import com.webant.password.manager.Adapters.Model.SearchItem;
import com.webant.password.manager.Adapters.Model.SelectedShareItems;
import com.webant.password.manager.Adapters.Model.ShareGetFolder;
import com.webant.password.manager.ItemModel.MainItem;
import com.webant.password.manager.crypto.NewAes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dikiy on 01.03.2018.
 */

public class DBWorker {
    private Database mDBHelper;
    private SQLiteDatabase mDb;

    public DBWorker(Context context) {
        mDBHelper = new Database(context);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        mDb.enableWriteAheadLogging();

    }

    public GetFolder_Item getFolder(int id) {
        GetFolder_Item item = null;
        Cursor cursor = mDb.rawQuery("select * from folders where id=" + id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            item = new GetFolder_Item(cursor.getString(1), cursor.getString(3), cursor.getString(4));
            cursor.moveToNext();
        }
        cursor.close();
        return item;
    }

    public List<ShareGetFolder> getFolders() {
        List<ShareGetFolder> items = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select * from folders", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            items.add(new ShareGetFolder(cursor.getString(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
        return items;
    }

    public void initTag(List<GetTag_Item> items) {
        mDb.execSQL("delete from tags");
        for (int i = 0; i < items.size(); i++) {
            mDb.execSQL("insert into tags values (" + items.get(i).getId() + ",'" + items.get(i).getName() + "')");

        }
    }

    public void initGroup(List<GetTag_Item> items) {
        mDb.execSQL("delete from groups");
        for (int i = 0; i < items.size(); i++) {
            mDb.execSQL("insert into groups values (" + items.get(i).getId() + ",'" + items.get(i).getName() + "')");

        }

    }

    public void deleteFolder(int id) {
        mDb.execSQL("DELETE FROM folders WHERE id=" + id);
    }

    public void deletePass(int id) {
        mDb.execSQL("DELETE FROM passwords WHERE id=" + id);
    }

    public void password(int id, int folder_id, String login, String pass, String url) {
        Cursor cursor = mDb.rawQuery("select id from passwords where id=" + id + "", null);
        if (cursor.getColumnCount() == 0) {
            mDb.execSQL("insert into passwords  values (" + id + "," + folder_id + "," + login + "," + pass + "," + url + ")");
        } else {
            mDb.execSQL("update passwords set folder_id=" + folder_id + ",login=" + login + ",pass=" + pass + ",url=" + url + " where id=" + id);
        }
        cursor.close();
    }


    public ArrayList<MainItem> loadData(int id) {
        ArrayList<MainItem> list = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select * from folders where folder=" + id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int thisId = Integer.parseInt(cursor.getString(0));
            String tag = cursor.getString(3);
            if (tag != null)
                if (tag.length() > 2) {
                    tag.substring(1, tag.length() - 1);
                }
            Cursor folderCursor = mDb.rawQuery("select * from passwords where folder=" + thisId, null);
            int folderContent = folderCursor.getCount();
            folderCursor = mDb.rawQuery("select * from folders where folder=" + thisId, null);
            folderContent += folderCursor.getCount();
            folderCursor.close();
            list.add(new MainItem(cursor.getString(1), Integer.parseInt(cursor.getString(0)), true, tag, 0, folderContent,this));
            cursor.moveToNext();
        }
        cursor.close();
        cursor = mDb.rawQuery("select * from passwords where folder=" + id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new MainItem(cursor.getString(2), Integer.parseInt(cursor.getString(0)), false, cursor.getString(5), Integer.parseInt(cursor.getString(10)), 0,this));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    public List<SearchItem> getSomePass(String s) {
        List<SearchItem> list = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select id,name from passwords where name LIKE '%" + s + "%' order by name", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new SearchItem(Integer.valueOf(cursor.getString(0)), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    public int getTagId(String s) {
        int i = -1;
        Cursor cursor = mDb.rawQuery("select id from tags where name='" + s + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            i = Integer.parseInt(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return i;
    }

    public void setDataPass(GetStorage storage) {
        List<GetStorage_Item> list = storage.getItems();
        mDb.execSQL("delete from passwords");
        for (int i = 0; i < list.size(); i++) {
            mDb.execSQL("insert into passwords  values (" + list.get(i).getPass().getId() + ",'" + list.get(i).getPass().getPass() + "','" + list.get(i).getPass().getName() + "'," + list.get(i).getFolder().getId() + ",'" + list.get(i).getPass().getLogin() + "','" + list.get(i).getPass().getUrl() + "','" + list.get(i).getPass().getDescription() + "','" + list.get(i).getPass().getTag() + "','" + list.get(i).getPass().getGroup() + "','" + list.get(i).getClue() + "','" + list.get(i).getId() + "')");
        }

    }

    public void updatePass(int id, String name, String url, String login, String password) {

        mDb.execSQL("UPDATE passwords SET pass='" + password + "',name='" + name + "',login='" + login + "',url='" + url + "' where id=" + id);
    }

    public GetPass_Item getPass(int id) {
        Cursor cursor = mDb.rawQuery("select * from passwords where id=" + id, null);
        cursor.moveToFirst();
        GetPass_Item item = new GetPass_Item(cursor.getString(1), cursor.getString(2), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
        cursor.close();
        return item;
    }

    public void setDataFolder(ArrayList<GetFolder_Item> list) {

        mDb.execSQL("delete from folders");
        for (int i = 0; i < list.size(); i++) {
            mDb.execSQL("insert into folders  values (" + list.get(i).getId() + ",'" + list.get(i).getName() + "'," + list.get(i).getParent() + ",'" + list.get(i).getTag().toString() + "','" + list.get(i).getGroup().toString() + "')");

        }
    }

    public List<String> getGroup() {
        List<String> list = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select name from groups order by name", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getTag() {
        List<String> list = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select name from tags order by name", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getTagName(List<String> list) {
        List<String> name = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select name from tags where id in(" + list.toString().substring(1, list.toString().length() - 1) + ")", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();


        return name;
    }

    public List<String> getGroupName(List<String> list) {
        List<String> name = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select name from groups where id in(" + list.toString().substring(1, list.toString().length() - 1) + ")", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();


        return name;
    }

    public void setNull() {
        mDb.execSQL("delete from folders");
        mDb.execSQL("delete from pass");
    }

    public List<SelectedShareItems> getPasswordOfFolder(int id, String masterPass) {
        List<SelectedShareItems> list = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select id from folders where folder =" + id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.addAll(getPasswordOfFolder(Integer.parseInt(cursor.getString(0)), masterPass));
            cursor.moveToNext();
        }
        cursor.close();
        Cursor cursor1 = mDb.rawQuery("select id,clue from passwords where folder =" + id, null);
        cursor1.moveToFirst();
        while (!cursor1.isAfterLast()) {
            list.add(new SelectedShareItems(Integer.valueOf(cursor1.getString(0)), NewAes.decrypt(cursor1.getString(1), masterPass)));
            cursor1.moveToNext();
        }
        cursor1.close();
        return list;
    }

    public SelectedShareItems getShareParams(int id, String masterPass) {
        SelectedShareItems item = null;
        Cursor cursor = mDb.rawQuery("select clue from passwords where id =" + id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            item = new SelectedShareItems(id, NewAes.decrypt(cursor.getString(0), masterPass));
            cursor.moveToNext();
        }
        cursor.close();
        return item;
    }

    public boolean findPass(Integer id) {
        Cursor cursor = mDb.rawQuery("select id from passwords where id =" + id, null);
        if (cursor.getCount() != 0)
            return true;
        else
            return false;
    }
}
