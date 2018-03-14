package com.example.dikiy.passwordmain.Retrofit;

import android.util.Log;

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
        Response response = chain.proceed(request); //тут выполняется запрос и результат в response.
       Log.v("12231231","123");
////       if(response.code()==401){
////
////           String sr=chain.request().header("Authorization");
////           String sl=LoadText.getText("access_token");
////
////            synchronized (ApiUtils.getAPIService()){
////
////            }
////
////
////           if(sr.equals("Bearer "+sl)) {
//////если токен обновляется
////               if(LoadText.getText("wait").equals("1")) {
////                for(int i=0;i<20;i++) {
////                    try {Thread.sleep(600);} catch (InterruptedException e) {e.printStackTrace();}
////                    if(!LoadText.getText("wait").equals("1")){break;}
////                }
////                if(LoadText.getText("wait").equals("1")){
////                    LoadText.setText("wait","0");
////                    return new Response.Builder().body(ResponseBody.create(MediaType.parse("-"), "error")).protocol(Protocol.HTTP_2).code(503).request(request).message("time error").build();
////                }else {
////                    return newResponse(request);
////                }
////            }
////
////            else{
////                   NewToken.refreshToken();
////                   return newResponse(request);
////               }
////           }else if(sl!=null){
////               return newResponse(request);
////           }else{
////               LoadText.setNull();
////           }
////       }
//       String thistoken=request.header("Authorization");
//       if (response.code()==401){
//
//           synchronized (this){
//               if(thistoken.equals("Bearer "+LoadText.getText("access_token"))) {
//                   NewToken.refreshToken();
//               }
//           }
//           Log.v("123458", LoadText.getText("access_token"));
//           if(LoadText.getText("access_token").length()<5){
//               Log.v("1234565323","1");
////             set();
//               return response;
//           }
//          return newResponse(request);
//       }
//        return response;
//
//    }
//    Response newResponse(Request request) throws IOException {
//                   Request request1 = request.newBuilder().removeHeader("Authorization").addHeader("Authorization", "Bearer " + LoadText.getText("access_token")).build();
//                   OkHttpClient client = new OkHttpClient();
//                   Response response1 = client.newCall(request1).execute();
//                   return response1;
//    }
       return response;
   }

}
