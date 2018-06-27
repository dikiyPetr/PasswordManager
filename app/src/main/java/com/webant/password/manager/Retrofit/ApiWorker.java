package com.webant.password.manager.Retrofit;

import android.content.Context;

import com.webant.password.manager.Adapters.Get.GetFolder;
import com.webant.password.manager.Adapters.Get.GetTag_Item;
import com.webant.password.manager.Adapters.Post.PostAdapter;
import com.webant.password.manager.Adapters.Post.PostRegister;
import com.webant.password.manager.DBase.LoadText;
import com.webant.password.manager.MyConstants;
import com.webant.password.manager.Retrofit.PostLogin;
import com.webant.password.manager.Retrofit.RetrofitClient;
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

public class ApiWorker {

    public static boolean postRefreshToken(Context context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("client_id", LoadText.getText(context, "client_id"));
        jsonObject.addProperty("grant_type", "refresh_token");
        jsonObject.addProperty("refresh_token", LoadText.getText(context, "refresh_token"));
        jsonObject.addProperty("client_secret", LoadText.getText(context, "client_secret"));
        PostLogin postLogin = RetrofitClient.getClient(context).create(PostLogin.class);
        Response response = null;
        try {
            response = postLogin.Login(jsonObject).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.isSuccessful()) {
            PostAdapter adapter = (PostAdapter) response.body();
            LoadText.refreshToken(context, adapter);
            return true;
        } else if (response != null && response.code() == MyConstants.RESPONSE_UNAUTHORIZED)
            return false;
        return true;
    }

}
