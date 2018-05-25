package com.example.dikiy.passwordmain.Adapters.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetService_Items_Commands {
    @SerializedName("id")
    @Expose
    private int id=0;
    @SerializedName("name")
    @Expose
    private String name="";
    @SerializedName("command")
    @Expose
    private String command="";
    @SerializedName("method")
    @Expose
    private String method="";

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getCommand() {
        return command;
    }

    public String getMethod() {
        return method;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
