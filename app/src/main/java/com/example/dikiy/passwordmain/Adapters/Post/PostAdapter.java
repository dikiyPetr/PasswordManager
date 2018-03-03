package com.example.dikiy.passwordmain.Adapters.Post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dikiy on 27.02.2018.
 */

public class PostAdapter {
    @SerializedName("access_token")
    @Expose
    private String access_token;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("random_id")
    @Expose
    private String random_id;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("refresh_token")
    @Expose
    private String refresh_token;

    public void setId(String id) {
        this.id = id;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setRandom_id(String random_id) {
        this.random_id = random_id;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getId() {
        return id;
    }

    public String getRandom_id() {
        return random_id;
    }

    public String getSecret() {
        return secret;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }
}
