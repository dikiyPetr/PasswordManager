package com.webant.password.manager.Adapters.Model;

public class EditTegsItems {
    int status = 0;
    String name = "";

    public EditTegsItems(String name) {
        status = 1;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public int getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean setStatus(int status) {
        if ((this.status == 1 && status == 1) || (this.status == -1 && status == -1))
            return false;
        this.status = this.status+status;
        return true;
    }
}
