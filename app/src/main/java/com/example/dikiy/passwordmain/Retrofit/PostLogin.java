package com.example.dikiy.passwordmain.Retrofit;



import com.example.dikiy.passwordmain.Adapters.Get.GetFolder;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dikiy on 23.01.2018.
 */

public interface PostLogin {


    @GET("/api/v1/folders")
    Call<GetFolder> GetFolder();

}
