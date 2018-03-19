package com.example.dikiy.passwordmain.DBase;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Get.GetFolder_Item;
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
    public void folder(GetFolder_Item item){
        int id=item.getId();
        String name=item.getName();
        List<Integer> idpass=item.getPass();
        List<Integer> idfolder=item.getParent();

        Cursor cursor = mDb.rawQuery("select id from folders where id="+id+"", null);
        if(cursor.isAfterLast()){

            Log.v("12345","INSERT into folders values ("+id+","+name+",'"+idpass.toString()+"','"+idfolder.toString()+"')");
            mDb.execSQL("INSERT into folders values ("+id+",'"+name+"','"+idpass.toString()+"','"+idfolder.toString()+"')");
        }else{
            Log.v("12345","update folders set name='"+name+"',children='"+idfolder+"',pass='"+idpass+"' where id="+id);
            mDb.execSQL("update folders set name='"+name+"',children='"+idfolder+"',pass='"+idpass+"' where id="+id);
        }
        cursor.close();
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
//        Cursor cursor = mDb.rawQuery("select children,pass from folders where id="+id, null);
//        if(cursor.getColumnCount()!=0){
//        cursor.moveToFirst();
//       List<String> folderList= new ArrayList<>();
//       folderList.addAll(stringToList(cursor.getString(0)));
//       List<String> passList= new ArrayList<>();
//       passList.addAll(stringToList(cursor.getString(1)));
//       Log.v("12324123123",passList.size()+" "+folderList.size());
//       for(int i=0;i<folderList.size();i++){
//           list.add(new MainItem(folderList.get(i)));
//       }
//       for(int i=0;i<passList.size();i++){
//           list.add(new MainItem(passList.get(i)));
//       }
//
//
//        cursor.close();}
        return list;
    }
    public void setData(  ArrayList<GetFolder_Item> list ,boolean type){
        if(type){
            mDb.execSQL("delete from folders");
            for (int i=0;i<list.size();i++) {
                mDb.execSQL("insert into passwords  values (" + list.get(i).getId()+ "," + list.get(i).getParent() + "," + login + "," + pass + "," + url + ")");

            }
        }else {
            mDb.execSQL("delete from passwords");
        }
    }


}
