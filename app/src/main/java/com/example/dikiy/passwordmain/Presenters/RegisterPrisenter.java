package com.example.dikiy.passwordmain.Presenters;

import com.example.dikiy.passwordmain.Model.LoginModel;
import com.example.dikiy.passwordmain.Model.MainModel;
import com.example.dikiy.passwordmain.RegisterActivity;

public class RegisterPrisenter {
    private RegisterActivity view;
    private final LoginModel model;

    public RegisterPrisenter(LoginModel model) {
        this.model = model;
    }

    public void attachView(RegisterActivity registerActivity) {
        view = registerActivity;
    }

   public void register(String username,String email,String password){
        model.PostNewUser(new LoginModel.CallbackRegister() {
            @Override
            public void onLoad() {
                view.execute();
            }

            @Override
            public void onError(String s) {
                view.error(s);
            }
        },view.getApplicationContext(),username,email,password);
   }
}
