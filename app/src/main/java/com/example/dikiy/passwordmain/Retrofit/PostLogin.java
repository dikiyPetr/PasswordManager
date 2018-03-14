package com.example.dikiy.passwordmain.Retrofit;



import com.example.dikiy.passwordmain.Adapters.Get.GetFolder;
import com.example.dikiy.passwordmain.Adapters.Post.PostAdapter;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by dikiy on 23.01.2018.
 */

public interface PostLogin {

    @Headers( {"Content-Type: application/json",
            "Accept: */*"
    })
    @POST("/oauth/v2/token")
    Call<PostAdapter> Login(@Body JsonObject post);

    @POST("/api/v1/clients")
    Call<PostAdapter> GetRandomId(@Body JsonObject post);




    @GET("/api/v1/user/current")
    Call<PostAdapter> CheckLogin(@HeaderMap Map<String, String> headers);
    @GET("/api/v1/folders")
    Call<GetFolder> GetFolder(@HeaderMap Map<String, String> headers);

}
