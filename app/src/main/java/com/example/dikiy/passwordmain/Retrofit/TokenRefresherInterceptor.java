package com.example.dikiy.passwordmain.Retrofit;

import android.util.Log;

import com.example.dikiy.passwordmain.DBase.LoadText;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dikiy on 25.01.2018.
 */
public class  TokenRefresherInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request(); // получили запрос который вы отправили на сервер.
        Log.v("123sad123asd", String.valueOf(request.body()));
        Response response = chain.proceed(request); //тут выполняется запрос и результат в response.

        String thistoken=request.header("Authorization");
        if (response.code()==401){
        Log.v("1231231231313132","1");
            synchronized (this){
                if(thistoken.equals("Bearer "+ LoadText.getText("access_token"))) {
                   ApiWorker.refreshToken();
                    Log.v("1231231231313132","2");
                }
            }
            Log.v("1231231231313132","3");
            Log.v("123458", LoadText.getText("access_token"));
            if(LoadText.getText("access_token").length()<5){
                Log.v("1234565323","1");
//             set();
                return response;
            }
            return newResponse(request);
        }
        return response;

    }
    Response newResponse(Request request) throws IOException {
        Request request1 = request.newBuilder().removeHeader("Authorization").addHeader("Authorization", "Bearer " + LoadText.getText("access_token")).build();
        OkHttpClient client = new OkHttpClient();
        Response response1 = client.newCall(request1).execute();
        return response1;
    }


}


