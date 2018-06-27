package com.webant.password.manager.Adapters.Model;

public class ShareGetFolder {
    String folder;
    int id;

    public ShareGetFolder(String id, String folder) {
        this.id=Integer.valueOf(id);
        this.folder=folder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
