package com.example.dikiy.passwordmain.Adapters.Get;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetService_Items_Commands {
    private int id=0;
    private String name="";
    private String method="";
    @SerializedName("params_url")
    @Expose
    private List<ParamsUrl> params_url=new ArrayList<>();
    @SerializedName("params_body")
    @Expose
    private List<ParamsBody> params_body=new ArrayList<>();
    private String template_url="";
    private String template_body="";
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public String getTemplate_body() {
        return template_body;
    }

    public String getTemplate_url() {
        return template_url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setTemplate_body(String template_body) {
        this.template_body = template_body;
    }

    public void setTemplate_url(String template_url) {
        this.template_url = template_url;
    }

    public List<String> getParams_body() {
        List<String> list=new ArrayList<>();
        Log.v("testLog",params_body.size()+"!");
        for(int i=0;i<params_body.size();i++){
            list.add(params_body.get(i).getTitle());
        }

        return list;
    }

    public List<String> getParams_url() {
        List<String> list=new ArrayList<>();
        Log.v("testLog",params_url.size()+"!");
        for(int i=0;i<params_url.size();i++){
            list.add(params_url.get(i).getTitle());
        }
        return list;
    }

    public class ParamsUrl{
        private int id=0;
        private String title="";

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }
    public class ParamsBody{
        private int id=0;
        private String title="";

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }
    }
}

