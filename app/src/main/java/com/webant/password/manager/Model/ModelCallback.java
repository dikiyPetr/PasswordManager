package com.webant.password.manager.Model;


public class ModelCallback {
    public interface Callback {
        void onLoad();

        void onFail();

        void onError(int code,String s);
    }
    public interface CallbackCheckField {
        void onLoad();

        void onFail();

        void onError();

        void onFieldError();
    }
    public interface CallbackNoError {
        void onLoad();

        void onFail();
    }
    public interface CallbackId {
        void onLoad(int i);

        void onFail();

        void onError();
    }
}
