package com.example.dikiy.passwordmain.Retrofit;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dikiy.passwordmain.Adapters.Get.GetFolder;
import com.example.dikiy.passwordmain.Adapters.Get.GetFolder_Item;
import com.example.dikiy.passwordmain.Adapters.Post.PostAdapter;
import com.example.dikiy.passwordmain.DBase.DBWorker;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.ItemModel.MainItem;
import com.example.dikiy.passwordmain.Retrofit.ApiUtils;
import com.example.dikiy.passwordmain.Retrofit.PostLogin;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dikiy on 14.03.2018.
 */

public class ApiWorker {
    public static int getRandomId() {
        JsonObject jsonObject= new JsonObject();
        JsonArray jsonArray= new JsonArray();
        jsonArray.add("password");
        jsonArray.add("refresh_token");
        jsonObject.add("allowed_grant_types",jsonArray);
        jsonObject.addProperty("name","");
        PostLogin postLogin = ApiUtils.getAPIService();
        Response response = null;
        try {
            response = postLogin.GetRandomId(jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(response!=null && response.code()==200) {
            PostAdapter adapter = (PostAdapter) response.body();
            LoadText.newId(adapter);
        return response.code();
        }
        return 0;
    }
    public ApiWorker(){

    }
    public static int Login(String name,String pass) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("client_id", LoadText.getText("client_id"));
        jsonObject.addProperty("username", name);
        jsonObject.addProperty("grant_type","password");
        jsonObject.addProperty("password", pass);
        jsonObject.addProperty("client_secret",LoadText.getText("client_secret"));
        PostLogin postLogin = ApiUtils.getAPIService();
        Response response = null;
        try {
            response = postLogin.Login(jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null && response.code()==200) {
            PostAdapter postAdapter = (PostAdapter) response.body();
            LoadText.refreshToken(postAdapter);
            return response.code();
        }
        return 0;

    }
    public static int RefreshToken(){
       JsonObject jsonObject = new JsonObject();
       jsonObject.addProperty("client_id", LoadText.getText("client_id"));
       jsonObject.addProperty("grant_type", "refresh_token");
       jsonObject.addProperty("refresh_token", LoadText.getText("refresh_token"));
       jsonObject.addProperty("client_secret",  LoadText.getText("client_secret"));
        PostLogin postLogin = ApiUtils.getAPIService();
        Response response = null;
        try {
            response = postLogin.Login(jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(response!=null && response.code()==200) {
            PostAdapter adapter = (PostAdapter) response.body();
            LoadText.refreshToken(adapter);
            return response.code();
        }
        return 0;
    }

    public GetFolder getFolder(){
//        final Map<String, String> map = new HashMap<>();
//        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));
//        PostLogin postLogin = ApiUtils.getAPIService();
//
//        final GetFolder[] response1 = {null};
//        postLogin.GetFolder(map).enqueue(new Callback<GetFolder>() {
//            @Override
//            public void onResponse(Call<GetFolder> call, Response<GetFolder> response) {
//                ApiWorker.this.notify();
//            }
//
//            @Override
//            public void onFailure(Call<GetFolder> call, Throwable t) {
//
//            }

//        });
        

        return null;
    }
        public static PostAdapter CheckLogin(){
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));
        PostLogin postLogin = ApiUtils.getAPIService();
        Response response = null;
        try {
            response = postLogin.CheckLogin(map).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null) {
            PostAdapter postAdapter = (PostAdapter) response.body();
            PostAdapter adapter = (PostAdapter) response.body();
            LoadText.refreshToken(adapter);
            return postAdapter;
        }
        return null;
    }
}
