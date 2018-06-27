package com.webant.password.manager.Retrofit;


import com.google.gson.JsonObject;
import com.webant.password.manager.Adapters.Get.GetCommand;
import com.webant.password.manager.Adapters.Get.GetFolder;
import com.webant.password.manager.Adapters.Get.GetFolder_Item;
import com.webant.password.manager.Adapters.Get.GetPass_Item;
import com.webant.password.manager.Adapters.Get.GetService;
import com.webant.password.manager.Adapters.Get.GetService_Items;
import com.webant.password.manager.Adapters.Get.GetService_Items_Commands;
import com.webant.password.manager.Adapters.Get.GetStorage;
import com.webant.password.manager.Adapters.Get.GetTag;
import com.webant.password.manager.Adapters.Get.GetTag_Item;
import com.webant.password.manager.Adapters.Get.Users;
import com.webant.password.manager.Adapters.Post.PostAdapter;
import com.webant.password.manager.Adapters.Post.PostRegister;

import org.json.JSONObject;

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

public interface PostLogin {

    @Headers({"Content-Type: application/json",
            "Accept: */*"
    })
    @POST("/oauth/v2/token")
    Call<PostAdapter> Login(@Body JsonObject post);

    @POST("/api/v1/folders")
    Call<PostAdapter> CreateFolder(@HeaderMap Map<String, String> headers, @Body JsonObject post);

    @POST("/api/v1/passes")
    Call<GetPass_Item> CreatePass(@HeaderMap Map<String, String> headers, @Body JsonObject post);

    @POST("/api/v1/clients")
    Call<PostAdapter> GetRandomId(@Body JsonObject post);

    @DELETE("/api/v1/folders/{id}")
    Call<PostAdapter> DeleteFolder(@Path("id") int id, @HeaderMap Map<String, String> headers);

    @DELETE("/api/v1/passes/{id}")
    Call<PostAdapter> DeletePass(@Path("id") int id, @HeaderMap Map<String, String> headers);

    @GET("/api/v1/tags?limit=999999999")
    Call<GetTag> GetTag(@HeaderMap Map<String, String> headers);

    @GET("/api/v1/groups?limit=999999999")
    Call<GetTag> GetGroup(@HeaderMap Map<String, String> headers);

    @GET("/api/v1/folders?limit=999999999")
    Call<GetFolder> GetFolder(@HeaderMap Map<String, String> headers);

    @GET("/api/v1/storages?limit=999999999")
    Call<GetStorage> GetPass(@HeaderMap Map<String, String> headers);

    @GET("/api/v1/passes/{id}.json")
    Call<GetPass_Item> GetPass(@Path("id") String id, @HeaderMap Map<String, String> headers);

    @PUT("/api/v1/passes/{id}")
    Call<GetPass_Item> UpdatePass(@Path("id") int id, @HeaderMap Map<String, String> headers, @Body JsonObject post);

    @POST("/api/v1/passes/tag/{id}")
    Call<GetPass_Item> AddTagInPass(@Path("id") int id, @Body JsonObject jsonObject, @HeaderMap Map<String, String> map);

    @HTTP(method = "DELETE", path = "/api/v1/passes/tag/{id}", hasBody = true)
    Call<GetPass_Item> DeleteTagInPass(@Path("id") int id, @Body JsonObject jsonObject, @HeaderMap Map<String, String> headers);

    @PUT("/api/v1/storages/{id}")
    Call<GetPass_Item> MovePass(@Path("id") int id, @HeaderMap Map<String, String> headers, @Body JsonObject post);

    @PUT("/api/v1/folders/{id}")
    Call<GetFolder_Item> UpdateFolder(@Path("id") int id, @HeaderMap Map<String, String> headers, @Body JsonObject post);

    @POST("/api/v1/folders/tag/{id}")
    Call<GetFolder_Item> AddTagInFolder(@Path("id") int id, @Body JsonObject jsonObject, @HeaderMap Map<String, String> map);

    @HTTP(method = "DELETE", path = "/api/v1/folders/tag/{id}", hasBody = true)
    Call<GetFolder_Item> DeleteTagInFolder(@Path("id") int id, @Body JsonObject jsonObject, @HeaderMap Map<String, String> headers);

    @PUT("/api/v1/folders/{id}")
    Call<GetFolder_Item> MoveFolder(@Path("id") int id, @HeaderMap Map<String, String> headers, @Body JsonObject post);

    @POST("/api/v1/services")
    Call<GetService_Items> AddService(@HeaderMap Map<String, String> map, @Body JsonObject jsonObject);

    @GET("/api/v1/services")
    Call<GetService> GetServices(@HeaderMap Map<String, String> map);

    @GET("/api/v1/service/commands")
    Call<GetCommand> GetCommands(@HeaderMap Map<String, String> headers, @Query("service") int id);

    @POST("/api/v1/service/commands")
    Call<GetService_Items_Commands> AddServiceCommand(@HeaderMap Map<String, String> map, @Body JsonObject jsonObject);

    @POST("/api/v1/users")
    Call<PostRegister> Register(@Body JsonObject jsonObject);

    @POST("/api/v1/passes/send/{id}")
    Call<PostRegister> SendPass(@HeaderMap Map<String, String> map, @Body JsonObject jsonObject, @Path("id") int id);

    @GET("/api/v1/user/current")
    Call<Users> GetThisUser(@HeaderMap Map<String, String> headers);

    @POST("/api/v1/storages")
    Call<Users> AddPasswordInStorage(@HeaderMap Map<String, String> headers, @Body JsonObject jsonObject);

    @GET("/api/v1/service/commands/{id}")
    Call<GetService_Items_Commands> GetCommand(@HeaderMap Map<String, String> headers, @Path("id") String id);

    @PUT("/api/v1/service/commands/{id}")
    Call<GetService_Items_Commands> EditCommand(@Path("id") int id, @HeaderMap Map<String, String> headers, @Body JsonObject post);

    @DELETE("/api/v1/services/{id}")
    Call<GetService_Items> DeleteService(@Path("id") int id, @HeaderMap Map<String, String> headers);

    @DELETE("/api/v1/service/commands/{id}")
    Call<GetService_Items_Commands> DeleteCommand(@Path("id") int id, @HeaderMap Map<String, String> headers);

    @POST("/api/v1/params")
    Call<PostAdapter> SetParams(@HeaderMap Map<String, String> headers, @Body JsonObject jsonObject);

    @PUT("/api/v1/services/{id}")
    Call<GetService_Items>  EditService(@Path("id") int id, @HeaderMap Map<String, String> headers, @Body JsonObject post);

    @GET("/api/v1/services/{id}")
    Call<GetService_Items> GetService(@Path("id") int id, @HeaderMap Map<String, String> headers);
}
