package com.example.dikiy.passwordmain.Retrofit;



import com.example.dikiy.passwordmain.Adapters.Get.GetFolder;
import com.example.dikiy.passwordmain.Adapters.Get.GetFolder_Item;
import com.example.dikiy.passwordmain.Adapters.Get.GetPass;
import com.example.dikiy.passwordmain.Adapters.Get.GetPass_Item;
import com.example.dikiy.passwordmain.Adapters.Get.GetStorage;
import com.example.dikiy.passwordmain.Adapters.Get.GetTag;
import com.example.dikiy.passwordmain.Adapters.Get.GetTag_Item;
import com.example.dikiy.passwordmain.Adapters.Post.PostAdapter;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dikiy on 23.01.2018.
 */

public interface    PostLogin {

    @Headers( {"Content-Type: application/json",
            "Accept: */*"
    })
    @POST("/oauth/v2/token")
    Call<PostAdapter> Login(@Body JsonObject post);

    @POST("/api/v1/folders")
    Call<PostAdapter> CreateFolder(@HeaderMap Map<String, String> headers,@Body JsonObject post);

    @POST("/api/v1/passes")
    Call<PostAdapter> CreatePass(@HeaderMap Map<String, String> headers,@Body JsonObject post);

    @POST("/api/v1/clients")
    Call<PostAdapter> GetRandomId(@Body JsonObject post);

    @DELETE("/api/v1/folders/{id}")
    Call<PostAdapter> DeleteFolder(@Path("id") int id,@HeaderMap Map<String, String> headers);

    @DELETE("/api/v1/passes/{id}")
    Call<PostAdapter> DeletePass(@Path("id") int id,@HeaderMap Map<String, String> headers);

    @GET("/api/v1/tags?limit=999999999")
    Call<GetTag> GetTag(@HeaderMap Map<String, String> headers);

    @GET("/api/v1/groups?limit=999999999")
    Call<GetTag> GetGroup(@HeaderMap Map<String, String> headers);


    @GET("/api/v1/user/current")
    Call<PostAdapter> CheckLogin(@HeaderMap Map<String, String> headers);


    @GET("/api/v1/folders?limit=999999999")
    Call<GetFolder> GetFolder(@HeaderMap Map<String, String> headers);

    @GET("/api/v1/passes?limit=999999999")
    Call<GetPass> GetPass(@HeaderMap Map<String, String> headers);

    @GET("/api/v1/storages")
    Call<GetStorage> GetClue( @HeaderMap Map<String, String> headers);

    @GET("/api/v1/passes/{id}.json")
    Call<GetPass_Item> GetPass(@Path("id") int id,@HeaderMap Map<String, String> headers);

    @PUT("/api/v1/passes/{id}")
    Call<GetPass_Item> UpdatePass(@Path("id") int id,@HeaderMap Map<String, String> headers,@Body JsonObject post);

    @POST("/api/v1/passes/tag/{id}")
    Call<GetPass_Item> AddTagInPass(@Path("id") int id, @HeaderMap Map<String, String> map, @Body JsonObject jsonObject);

    @POST("/api/v1/passes/group/{id}")
    Call<GetPass_Item> AddGroupInPass(@Path("id") int id, @HeaderMap Map<String, String> map, @Body JsonObject jsonObject);

    @POST("/api/v1/tags")
    Call<GetTag_Item> AddTag(@HeaderMap Map<String, String> map,@Body JsonObject jsonObject);

    @POST("/api/v1/groups")
    Call<GetTag_Item> AddGroup(@HeaderMap Map<String, String> map,@Body JsonObject jsonObject);

    @HTTP(method = "DELETE", path = "/api/v1/passes/group/{id}", hasBody = true)
    Call<GetPass_Item> DeleteGroupInPass(@Path("id") int id, @Body JsonObject jsonObject,@HeaderMap Map<String, String> headers);

    @HTTP(method = "DELETE", path = "/api/v1/passes/tag/{id}", hasBody = true)
    Call<GetPass_Item> DeleteTagInPass(@Path("id") int id, @Body JsonObject jsonObject,@HeaderMap Map<String, String> headers);

    @PUT("/api/v1/passes/{id}")
    Call<GetPass_Item> MovePass(@Path("id") int id, @HeaderMap Map<String, String> headers, @Body JsonObject post);

    @PUT("/api/v1/folders/{id}")
    Call<GetFolder_Item> UpdateFolder(@Path("id") int id, @HeaderMap Map<String, String> headers, @Body JsonObject post);

    @POST("/api/v1/folders/tag/{id}")
    Call<GetFolder_Item> AddTagInFolder(@Path("id") int id, @HeaderMap Map<String, String> map, @Body JsonObject jsonObject);

    @POST("/api/v1/folders/group/{id}")
    Call<GetFolder_Item> AddGroupInFolder(@Path("id") int id, @HeaderMap Map<String, String> map, @Body JsonObject jsonObject);

    @HTTP(method = "DELETE", path = "/api/v1/folders/group/{id}", hasBody = true)
    Call<GetFolder_Item> DeleteGroupInFolder(@Path("id") int id, @Body JsonObject jsonObject,@HeaderMap Map<String, String> headers);

    @HTTP(method = "DELETE", path = "/api/v1/folders/tag/{id}", hasBody = true)
    Call<GetFolder_Item> DeleteTagInFolder(@Path("id") int id, @Body JsonObject jsonObject,@HeaderMap Map<String, String> headers);

    @PUT("/api/v1/folders/{id}")
    Call<GetFolder_Item > MoveFolder(@Path("id") int id, @HeaderMap Map<String, String> headers, @Body JsonObject post);

}
