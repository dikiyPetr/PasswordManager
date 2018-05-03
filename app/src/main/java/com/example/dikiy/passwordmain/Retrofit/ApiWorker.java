package com.example.dikiy.passwordmain.Retrofit;

import com.example.dikiy.passwordmain.Adapters.Get.GetFolder;
import com.example.dikiy.passwordmain.Adapters.Get.GetTag_Item;
import com.example.dikiy.passwordmain.Adapters.Post.PostAdapter;
import com.example.dikiy.passwordmain.DBase.LoadText;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

/**
 * Created by dikiy on 14.03.2018.
 */

public class    ApiWorker {
    public static int createFolder(String name,int parent){
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        if(parent!=0) {
            jsonObject.addProperty("parent", String.valueOf(parent));
        }
        PostLogin postLogin = ApiUtils.getAPIService();
        Response response = null;
        try {
            response = postLogin.CreateFolder(map,jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null && response.code()==200) {
            return response.code();
        }
        if(response!=null) {
            return response.code();
        }else
        {
            return 0;
        }
    }
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
        return response.code();
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
        if(response!=null) {
            return response.code();
        }else
        {
            return 0;
        }
    }

    public static int refreshToken(){
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
        if(response!=null) {
            return response.code();
        }else
        {
            return 0;
        }
    }
    public static int deleteFolder(int id){

        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));

        PostLogin postLogin = ApiUtils.getAPIService();
        Response response=null;
        try {
            response = postLogin.DeleteFolder(id,map).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(response!=null && response.code()==200) {
            PostAdapter adapter = (PostAdapter) response.body();
            LoadText.refreshToken(adapter);
            return response.code();
        }


        if(response!=null) {
            return response.code();
        }else
        {
            return 0;
        }
    }
    public int setFolder(){
        JsonObject jsonObject = new JsonObject();


        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));


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
        if(response!=null) {
            return response.code();
        }else
        {
            return 0;
        }
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
    public static int addTagInPass(int idpass, int idtag){
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("tag", String.valueOf(idtag));

        PostLogin postLogin = ApiUtils.getAPIService();
        Response response = null;
        try {
            response = postLogin.AddTagInPass(idpass,map,jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null && response.code()==200) {
            return response.code();
        }
        if(response!=null) {
            return response.code();
        }else
        {
            return 0;
        }

    }
    public static int addTagInFolder(int idpass, int idtag){
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("tag", String.valueOf(idtag));

        PostLogin postLogin = ApiUtils.getAPIService();
        Response response = null;
        try {
            response = postLogin.AddTagInFolder(idpass,map,jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null && response.code()==200) {
            return response.code();
        }
        if(response!=null) {
            return response.code();
        }else
        {
            return 0;
        }

    }
    public static int updatePass( int i,String name,String url,String login, String password) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("url", url);
        jsonObject.addProperty("pass", password);
        jsonObject.addProperty("login", login);


        PostLogin postLogin = ApiUtils.getAPIService();
        Response response = null;
        try {
            response = postLogin.UpdatePass(i,map,jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null && response.code()==200) {
            return response.code();
        }
        if(response!=null) {
            return response.code();
        }else
        {
            return 0;
        }
    }
    public static int updateFolder( int i,String name) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);



        PostLogin postLogin = ApiUtils.getAPIService();
        Response response = null;
        try {
            response = postLogin.UpdateFolder(i,map,jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null && response.code()==200) {
            return response.code();
        }
        if(response!=null) {
            return response.code();
        }else
        {
            return 0;
        }
    }
    public static int createPass(String name,String parent, String url, String pass,String login,String description,List<String> tags,List<String> groups) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        if(!parent.equals("0")) {
            jsonObject.addProperty("folder", String.valueOf(parent));
        }
        jsonObject.addProperty("url", url);
        jsonObject.addProperty("pass", pass);
        jsonObject.addProperty("login", login);
        JsonArray jsonArray= new JsonArray();
        for(int i=0;i<tags.size();i++){
            jsonArray.add(tags.get(i).toString());
        }
        jsonObject.add("tags", jsonArray);
        JsonArray jsonArray1 = new JsonArray();
        for(int i=0;i<groups.size();i++){
            jsonArray1.add(groups.get(i).toString());
        }
        jsonObject.add("groups", jsonArray1);

        jsonObject.addProperty("description", description);
        jsonObject.addProperty("secretKey", LoadText.getText("pass"));
        PostLogin postLogin = ApiUtils.getAPIService();
        Response response = null;
        try {
            response = postLogin.CreatePass(map,jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null && response.code()==200) {
            return response.code();
        }
        if(response!=null) {
            return response.code();
        }else
        {
            return 0;
        }
    }

    public static Integer addGroupInPass(int idpass, int idgroup) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("group", idgroup);
        PostLogin postLogin = ApiUtils.getAPIService();
        Response response = null;
        try {
            response = postLogin.AddGroupInPass(idpass,map,jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null && response.code()==200) {
            return response.code();
        }
        if(response!=null) {
            return response.code();
        }else
        {
            return 0;
        }
    }
    public static Integer addGroupInFolder(int idpass, int idgroup) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("group", idgroup);
        PostLogin postLogin = ApiUtils.getAPIService();
        Response response = null;
        try {
            response = postLogin.AddGroupInFolder(idpass,map,jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null && response.code()==200) {
            return response.code();
        }
        if(response!=null) {
            return response.code();
        }else
        {
            return 0;
        }
    }

    public static Integer addTag( String s) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", s);
        PostLogin postLogin = ApiUtils.getAPIService();
        Response response = null;
        try {
            response = postLogin.AddTag(map,jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null && response.code()==200) {
            GetTag_Item item = (GetTag_Item) response.body();
            return    item.getId();
        }
        if(response!=null) {
            return 0;
        }else
        {
            return 0;
        }
    }
    public static Integer addGroup( String s) {
        final Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer "+LoadText.getText("access_token"));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", s);
        PostLogin postLogin = ApiUtils.getAPIService();
        Response response = null;
        try {
            response = postLogin.AddGroup(map,jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null && response.code()==200) {
            GetTag_Item item = (GetTag_Item) response.body();
            return    item.getId();
        }
        if(response!=null) {
            return 0;
        }else
        {
            return 0;
        }
    }
}
