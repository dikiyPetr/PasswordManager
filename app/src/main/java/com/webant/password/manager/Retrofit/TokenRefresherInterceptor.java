package com.webant.password.manager.Retrofit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.webant.password.manager.DBase.LoadText;
import com.webant.password.manager.LoginActivity;
import com.webant.password.manager.MyConstants;
import com.webant.password.manager.Retrofit.ApiWorker;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

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
                    if(!ApiWorker.postRefreshToken(context)){
                        Toast.makeText(context,"UnLogin",Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(context, LoginActivity.class);
                        intent.setFlags(FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
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


