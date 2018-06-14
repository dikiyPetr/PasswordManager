package com.example.dikiy.passwordmain.DBase;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Post.PostAdapter;

/**
 * Created by dikiy on 25.01.2018.
 */

public class LoadText extends Activity{


   public static void setText( Context context,String name, String value) {
       SharedPreferences preferences = context.getSharedPreferences("Data",MODE_PRIVATE);

       if (preferences != null) {
           SharedPreferences.Editor editor = preferences.edit();
           editor.putString(name, value);
           Log.v("123456",name+value);
           editor.commit();
       }


    }

    public static String getText(Context context, String key) {
        String value = null;
        SharedPreferences preferences =  context.getSharedPreferences("Data",MODE_PRIVATE);
        if (preferences != null) {
            value = preferences.getString(key, null);
            Log.v("123457",key);
        }
        if(value==null){
            value="";
        }
        return value;

    }

    public static void setNull(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Data",MODE_PRIVATE);

        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("access_token", null);
            editor.putString("refresh_token", null);
            editor.putString("pass",null);
            editor.putString("pin",null);
            editor.commit();
        }
    }
    public static void refreshToken(Context context,PostAdapter adapter) {
        SharedPreferences preferences = context.getSharedPreferences("Data",MODE_PRIVATE);

        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("access_token", adapter.getAccess_token());
            editor.putString("refresh_token", adapter.getRefresh_token());
            editor.commit();
        }
    }
    public static void newRandomId(Context context,PostAdapter adapter) {
        SharedPreferences preferences = context.getSharedPreferences("Data",MODE_PRIVATE);

        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("client_secret", adapter.getSecret());
            editor.putString("client_id", adapter.getId()+"_"+adapter.getRandom_id());
            editor.commit();
        }
    }
    public static String getMasterPass(Context context) {
        String value = null;
        SharedPreferences preferences =  context.getSharedPreferences("Data",MODE_PRIVATE);
        if (preferences != null) {
            value = preferences.getString("master_pass", null);
        }
        if(value==null){
            value="";
        }
        return value;
    }
    public static void setMasterPass(Context context,String pass) {
        SharedPreferences preferences = context.getSharedPreferences("Data",MODE_PRIVATE);

        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("master_pass", pass);
            editor.commit();
        }
    }

}
