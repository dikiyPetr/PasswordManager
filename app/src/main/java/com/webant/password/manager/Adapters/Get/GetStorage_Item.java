package com.webant.password.manager.Adapters.Get;

import com.webant.password.manager.Adapters.Get.GetFolder_Item;
import com.webant.password.manager.Adapters.Get.GetPass_Item;

/**
 * Created by dikiy on 25.04.2018.
 */

public class GetStorage_Item {

    private Integer id=0;
    private GetPass_Item pass;
    private String clue;
    private GetFolder_Item folder = new GetFolder_Item();

    public GetFolder_Item getFolder() {
        return folder;
    }


    public String getClue() {
        return clue;
    }

    public void setClue(String clue) {
        this.clue = clue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GetPass_Item getPass() {
        return pass;
    }

    public void setPass(GetPass_Item pass) {
        this.pass = pass;
    }
}
