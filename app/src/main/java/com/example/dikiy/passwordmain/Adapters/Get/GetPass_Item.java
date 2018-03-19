package com.example.dikiy.passwordmain.Adapters.Get;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPass_Item {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pass")
    @Expose
    private boolean pass;
    @SerializedName("folder")
    @Expose
    private List<GetPass_Item_Folder> folder = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }
    public boolean getPass() {
        return pass;
    }
    public List<GetPass_Item_Folder> getFolder() {
        return folder;
    }

    public void setFolder(List<GetPass_Item_Folder> children) {
        this.folder = children;
    }

}
