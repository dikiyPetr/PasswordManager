package com.example.dikiy.passwordmain.Retrofit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.dikiy.passwordmain.DBase.LoadText;
import com.example.dikiy.passwordmain.MyConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dikiy on 25.01.2018.
 */
public class TokenRefresherInterceptor implements Interceptor {
    private Context context;

    TokenRefresherInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String thisToken = request.header("Authorization");
        if (response.code() == MyConstants.RESPONSE_UNAUTHORIZED) {
            synchronized (this) {
                if (thisToken.equals("Bearer " + LoadText.getText(context, "access_token"))) {
                    ApiWorker.postRefreshToken(context);
                }
            }
            if (LoadText.getText(context, "access_token").length() < 5) {
                return response;
            }
            return newResponse(request);
        }
        return response;
    }

    private Response newResponse(Request request) throws IOException {
        Request request1 = request.newBuilder().removeHeader("Authorization")
                .addHeader("Authorization", "Bearer " + LoadText.getText(context, "access_token")).build();
        OkHttpClient client = new OkHttpClient();
        return client.newCall(request1).execute();
    }
}


