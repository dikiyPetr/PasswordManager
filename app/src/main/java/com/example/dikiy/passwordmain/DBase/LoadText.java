package com.example.dikiy.passwordmain.DBase;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Post.PostAdapter;
import com.example.dikiy.passwordmain.GetContext;

/**
 * Created by dikiy on 25.01.2018.
 */

public class LoadText extends Activity{


   public static void setText( String name, String value) {
       SharedPreferences preferences = GetContext.getContext().getSharedPreferences("Data",MODE_PRIVATE);

       if (preferences != null) {
           SharedPreferences.Editor editor = preferences.edit();
           editor.putString(name, value);
           Log.v("123456",name+value);
           editor.commit();
       }


    }

    public static String getText( String key) {
        String value = null;
        SharedPreferences preferences =  GetContext.getContext().getSharedPreferences("Data",MODE_PRIVATE);
        if (preferences != null) {
            value = preferences.getString(key, null);
            Log.v("123457",key);
        }
        if(value==null){
            value="";
        }
        return value;

    }

    public static void setNull() {
        SharedPreferences preferences = GetContext.getContext().getSharedPreferences("Data",MODE_PRIVATE);

        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("access_token", null);
            editor.putString("refresh_token", null);

            editor.commit();
        }
    }
    public static void refreshToken(PostAdapter adapter) {
        SharedPreferences preferences = GetContext.getContext().getSharedPreferences("Data",MODE_PRIVATE);

        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("access_token", adapter.getAccess_token());
            editor.putString("refresh_token", adapter.getRefresh_token());
            editor.commit();
        }
    }
    public static void newId(PostAdapter adapter) {
        SharedPreferences preferences = GetContext.getContext().getSharedPreferences("Data",MODE_PRIVATE);

        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("client_secret", adapter.getSecret());
            editor.putString("client_id", adapter.getId()+"_"+adapter.getRandom_id());
            editor.commit();
        }
    }

}
