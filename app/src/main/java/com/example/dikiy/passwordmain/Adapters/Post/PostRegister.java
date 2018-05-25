package com.example.dikiy.passwordmain.Adapters.Post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostRegister {
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
