package com.example.dikiy.passwordmain.Retrofit;

/**
 * Created by Chike on 12/4/2016.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://pass-manager.dev.webant.ru/";

    public static PostLogin getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(PostLogin.class);

    }

}
