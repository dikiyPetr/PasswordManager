package com.example.dikiy.passwordmain.DBase;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ListAdapter;

import com.example.dikiy.passwordmain.Adapters.Get.GetFolder_Item;
import com.example.dikiy.passwordmain.Adapters.Get.GetPass_Item;
import com.example.dikiy.passwordmain.Adapters.Get.GetTag_Item;
import com.example.dikiy.passwordmain.Adapters.Model.FlyVItem;
import com.example.dikiy.passwordmain.Adapters.Model.SearchItem;
import com.example.dikiy.passwordmain.GetContext;
import com.example.dikiy.passwordmain.ItemModel.MainItem;

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
    public DBWorker(){
        mDBHelper = new Database(GetContext.getContext());
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
    public GetFolder_Item getFolder(int id){
        GetFolder_Item item = null;

        Log.v("12zxcdadads","select * from folders where id="+id);
        Cursor cursor = mDb.rawQuery("select * from folders where id="+id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            item = new GetFolder_Item(cursor.getString(1),cursor.getString(3),cursor.getString(4));
            cursor.moveToNext();
        }
        cursor.close();
        return item;
    }
    public void folder(GetFolder_Item item){
//        int id=item.getId();
//        String name=item.getName();
//        List<Integer> idpass=item.getPass();
//        List<Integer> idfolder=item.getParent();
//
//        Cursor cursor = mDb.rawQuery("select id from folders where id="+id+"", null);
//        if(cursor.isAfterLast()){
//
//            Log.v("12345","INSERT into folders values ("+id+","+name+",'"+idpass.toString()+"','"+idfolder.toString()+"')");
//            mDb.execSQL("INSERT into folders values ("+id+",'"+name+"','"+idpass.toString()+"','"+idfolder.toString()+"')");
//        }else{
//            Log.v("12345","update folders set name='"+name+"',children='"+idfolder+"',pass='"+idpass+"' where id="+id);
//            mDb.execSQL("update folders set name='"+name+"',children='"+idfolder+"',pass='"+idpass+"' where id="+id);
//        }
//        cursor.close();
    }
    public void initTag(List<GetTag_Item> items){

        mDb.execSQL("delete from tags");
        for (int i=0;i<items.size();i++) {
            Log.v("DBcheck", String.valueOf(items.get(i).getId()));
            Log.v("DBcheck", String.valueOf(items.get(i).getName()));

            Log.v("DBlog3","insert into tags values ("+ items.get(i).getId()+",'"+items.get(i).getName()+"')");
            mDb.execSQL("insert into tags values ("+ items.get(i).getId()+",'"+items.get(i).getName()+"')" );

        }
    }
    public void initGroup(List<GetTag_Item> items){
        mDb.execSQL("delete from groups");
        for (int i=0;i<items.size();i++) {
            Log.v("DBcheck", String.valueOf(items.get(i).getId()));
            Log.v("DBcheck", String.valueOf(items.get(i).getName()));

            Log.v("DBlog3","insert into groups values ("+ items.get(i).getId()+",'"+items.get(i).getName()+"')");
            mDb.execSQL("insert into groups values ("+ items.get(i).getId()+",'"+items.get(i).getName()+"')" );

        }

    }
    public void deleteFolder(int id){
        mDb.execSQL("DELETE FROM folders WHERE id="+id);
    }
    public void deletePass(int id){
        mDb.execSQL("DELETE FROM passwords WHERE id="+id);
    }
    public void password(int id, int folder_id,String login,String pass,String url){
        Cursor cursor = mDb.rawQuery("select id from passwords where id="+id+"", null);

        if(cursor.getColumnCount()==0){
            mDb.execSQL("insert into passwords  values ("+id+","+folder_id+","+login+","+pass+","+url+")");
        }else{
            mDb.execSQL("update passwords set folder_id="+folder_id+",login="+login+",pass="+pass+",url="+url+" where id="+id);
        }
        cursor.close();
    }
    public void initFolder(List<GetFolder_Item> items){
        for(int i=0;i<items.size();i++){
            GetFolder_Item item=items.get(i);
            folder(item);
        }


    }

    private List<String> stringToList(String s){

        List<String> list = new ArrayList<>(Arrays.asList(s.substring(1,s.length()-1).split(",")));
        return list;
    }
    public  List<MainItem> loadData(int id){
        List<MainItem> list =new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select * from folders where folder="+id, null);
        Log.v("LoadLog", String.valueOf(cursor.getCount()));
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            list.add(new MainItem(cursor.getString(1), Integer.parseInt(cursor.getString(0)),true,""));
            cursor.moveToNext();
        }
        cursor.close();
        cursor = mDb.rawQuery("select * from passwords where folder="+id, null);
        Log.v("LoadLog", String.valueOf(cursor.getCount()));
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String tag=cursor.getString(7);
            if(tag.length()>2){
                tag.substring(1,tag.length()-1);
            }
            list.add(new MainItem(cursor.getString(2), Integer.parseInt(cursor.getString(0)),false,tag));
            cursor.moveToNext();
        }
        cursor.close();
        Log.v("LoadLog", String.valueOf(list.size()));
        return list;
    }
    public  List<SearchItem> getSomePass(String s){
        List<SearchItem> list =new ArrayList<>();
        Log.v("12zxcdadads","select id,name from passwords where name LIKE '%"+s+"%' order by name");
        Cursor cursor = mDb.rawQuery("select id,name from passwords where name LIKE '%"+s+"%' order by name", null);
            Log.v("12zxcdadads", String.valueOf(cursor.getCount()));
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            list.add(new SearchItem(Integer.valueOf(cursor.getString(0)),cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public FlyVItem getDataToFlyV(int id){

        String url = "";
        String login = "";
        String pass = "";
        Cursor cursor=mDb.rawQuery("select login,url from passwords where id="+id,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            login=cursor.getString(0);
            url=cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();
        cursor=mDb.rawQuery("select password from pass where id="+id,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            pass=cursor.getString(0);

            cursor.moveToNext();
        }
        cursor.close();

      return new FlyVItem(url,login,pass);
    }

    public int getTagId(String s){
        int i = 0;
        Log.v("123123123asdqq","select id from tags where name='"+s+"'");
        Cursor cursor=mDb.rawQuery("select id from tags where name='"+s+"'",null);
        cursor.moveToFirst();
        Log.v("123123123asdqq","3");

        while (!cursor.isAfterLast()){
            Log.v("123123123asdqq","4");
            i= Integer.parseInt(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return i;
    }

    public int getGroupId(String s){
        int i=0;
        Cursor cursor=mDb.rawQuery("select id from groups where name='"+s+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            i= Integer.parseInt(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return i;
    }
    public void setDataPass(ArrayList<GetPass_Item> list ){

            mDb.execSQL("delete from passwords");
            for (int i=0;i<list.size();i++) {
                Log.v("DBcheck", String.valueOf(list.get(i).getId()));
                Log.v("DBcheck", String.valueOf(list.get(i).getPass().toString()));
                Log.v("DBcheck", String.valueOf(list.get(i).getName()));
                Log.v("DBcheck", String.valueOf(list.get(i).getFolder()));
                Log.v("DBcheck",String.valueOf(list.get(i).getLogin()));
                Log.v("DBcheck",String.valueOf(list.get(i).getUrl()));
                Log.v("DBcheck",String.valueOf(list.get(i).getDescription()));
                Log.v("DBcheck",String.valueOf(list.get(i).getGroup()));
                Log.v("DBcheck",String.valueOf(list.get(i).getTag()));

                Log.v("DBlog1","insert into passwords  values (" + list.get(i).getId()+ ",'" + "" + "','" + list.get(i).getName() + "'," + list.get(i).getFolder() + ",'" + list.get(i).getLogin() + "','" + list.get(i).getUrl() +"','"+list.get(i).getDescription()+"','"+list.get(i).getTag()+"','"+list.get(i).getGroup()+"')");
                mDb.execSQL("insert into passwords  values (" + list.get(i).getId()+ ",'" + "" + "','" + list.get(i).getName() + "'," + list.get(i).getFolder() + ",'" + list.get(i).getLogin() + "','" + list.get(i).getUrl() +"','"+list.get(i).getDescription()+"','"+list.get(i).getTag()+"','"+list.get(i).getGroup()+"')");

            }

    }
    public void updatePass(int id, String name, String url, String login, String password){

        mDb.execSQL("UPDATE passwords SET pass='" + password + "',name='" + name + "',login='" + login + "',url='" + url + "' where id=" + id);
    }
    public void updateFolder(int id, String name){

        mDb.execSQL("UPDATE folders SET name='" + name + "' where id=" + id);
    }
    public boolean UpdatePass(int id,String pass){
        Cursor cursor = mDb.rawQuery("select password from pass where id="+id, null);
        cursor.moveToFirst();
        if(cursor.getCount()==0){
            cursor.close();
            mDb.execSQL("insert into pass (id,password) values ("+id+",'"+pass+"')");
            return true;
        }
        if(pass.equals(cursor.getString(0))){
            cursor.close();
            return false;
        }else {
            Log.v("DBupdate", "UPDATE pass SET pass='" + pass + "' where id=" + id);
            mDb.execSQL("UPDATE pass SET password='" + pass + "' where id=" + id);
            cursor.close();
            return true;
        }
    }
    public String getPassword(int id){
        String s = "";
        Log.v("DBget", "select password from pass where id="+id);
        Cursor cursor = mDb.rawQuery("select password from pass where id="+id, null);
        cursor.moveToFirst();
        if(cursor.getCount()==1){
        s=cursor.getString(0);
        }
        cursor.close();
            return s;
    }

    public GetPass_Item getPass(int id){

        Cursor cursor = mDb.rawQuery("select * from passwords where id="+id, null);
        Log.v("kkfdaj12938sa", String.valueOf(id));
        cursor.moveToFirst();
        Log.v("kkfdaj12938sa",cursor.getString(1));
        Log.v("kkfdaj12938sa",cursor.getString(2));
        Log.v("kkfdaj12938sa",cursor.getString(5));
        Log.v("kkfdaj12938sa",cursor.getString(4));
        Log.v("kkfdaj12938sa",cursor.getString(6));
        Log.v("kkfdaj12938sa",cursor.getString(7));
        Log.v("kkfdaj12938sa",cursor.getString(8));

        GetPass_Item item= new GetPass_Item(cursor.getString(1),cursor.getString(2),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8  ));
        cursor.close();
        Log.v("123sda1223",item.toString());
return item;
    }
    public void setDataFolder(  ArrayList<GetFolder_Item> list ){

            mDb.execSQL("delete from folders");
            for (int i=0;i<list.size();i++) {
                Log.v("DBcheck", String.valueOf(list.get(i).getId()));
                Log.v("DBcheck", list.get(i).getName());
                Log.v("DBcheck", String.valueOf(list.get(i).getParent()));

                Log.v("DBlog2","insert into folders  values (" + list.get(i).getId()+ ",'" + list.get(i).getName() + "'," + list.get(i).getParent() +",'"+list.get(i).getTag()+"','"+list.get(i).getGroup()+"')");

                mDb.execSQL("insert into folders  values (" + list.get(i).getId()+ ",'" + list.get(i).getName() + "'," + list.get(i).getParent() +",'"+list.get(i).getTag().toString()+"','"+list.get(i).getGroup().toString()+"')");

            }
        }

    public List<String> getGroup() {
        List<String> list=new ArrayList<>();
        Cursor cursor = mDb.rawQuery("select name from groups order by name", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Log.v("adasdzcwwgs",cursor.getString(0));
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getTag() {
        List<String> list=new ArrayList<>();
            Cursor cursor = mDb.rawQuery("select name from tags order by name", null);

            cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Log.v("adasdzcwwgs",cursor.getString(0));
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public String getTag(int id) {
        String item;
        Cursor cursor = mDb.rawQuery("select name from tags where id="+id, null);

        cursor.moveToFirst();

          item=cursor.getString(0);
        cursor.close();
        return item;
    }

    public String getGroup(int id) {
        String item;
        Cursor cursor = mDb.rawQuery("select name from groups where id="+id, null);

        cursor.moveToFirst();

        item=cursor.getString(0);
        cursor.close();
        return item;
    }

    public List<String> getTagName(List<String> list) {
        List<String> name=new ArrayList<>();
                Log.v("1asdasdadask2aa",list.toString());
                Log.v("1asdasdadask2aa","select name from tags where id in("+list.toString().substring(1,list.toString().length()-1)+")");
                Cursor cursor = mDb.rawQuery("select name from tags where id in("+list.toString().substring(1,list.toString().length()-1)+")", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
        name.add(cursor.getString(0));
        cursor.moveToNext();
            }
            cursor.close();


        return name;
    }

    public List<String> getGroupName(List<String> list) {
        List<String> name=new ArrayList<>();
        Log.v("1asdasdadask2aa",list.toString());
        Log.v("1asdasdadask2aa","select name from groups where id in("+list.toString().substring(1,list.toString().length()-1)+")");
        Cursor cursor = mDb.rawQuery("select name from groups where id in("+list.toString().substring(1,list.toString().length()-1)+")", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            name.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();


        return name;
    }
}
