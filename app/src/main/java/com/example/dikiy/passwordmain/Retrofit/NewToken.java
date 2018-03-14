package com.example.dikiy.passwordmain.Retrofit;

import android.util.Log;

import com.example.dikiy.passwordmain.DBase.LoadText;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Response;


public class NewToken {

//static String refreshToken() {
////       synchronized (TokenRefresherInterceptor.class){
//
//       PostLogin postLogin = ApiUtils.getAPIService();
//       JsonObject jsonObject = new JsonObject();
//       jsonObject.addProperty("client_id", LoadText.getText("client_id"));
//       jsonObject.addProperty("grant_type", "refresh_token");
//       jsonObject.addProperty("refresh_token", LoadText.getText("refresh_token"));
//       jsonObject.addProperty("client_secret",  LoadText.getText("client_secret"));
//       Log.v("123", jsonObject.toString());
//       Response refreshTokenResponse = null;
//       try {
//              LoadText.setText("wait","1");
//              refreshTokenResponse = postLogin  .Login(jsonObject).execute();
//
//       } catch (IOException e) {
//              e.printStackTrace();
//       }
//       Log.v("123458", String.valueOf(refreshTokenResponse.code()));
//       String s = null;
//
//    if(refreshTokenResponse.code()==200){
//       AdapterLogin adapterLogin=new AdapterLogin();
//       adapterLogin= (AdapterLogin) refreshTokenResponse.body();
//
//       LoadText.setText("access_token",adapterLogin.getAccess_token());
//       LoadText.setText("refresh_token",adapterLogin.getRefresh_token());
//       LoadText.setText("wait","0");
//
//       Newqwe newqwe = new Newqwe();
//        newqwe.registerCallBack();
//       return adapterLogin.getAccess_token();
//       }
////       else if(){
////           LoadText.setText("access_token"," ");
////           return null;
////       }
//
//       return null;
////}
//
//}
//

}

