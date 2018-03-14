package com.example.dikiy.passwordmain.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chike on 12/3/2016.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient1 = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
            httpClient1.addInterceptor(logging);
            httpClient1.connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES);

//            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient1.addInterceptor(new TokenRefresherInterceptor()).build();
httpClient1.build();


             retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient1.build())
                    .build();

        }
        return retrofit;
    }
}
