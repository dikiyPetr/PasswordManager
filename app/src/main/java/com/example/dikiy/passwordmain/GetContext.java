package com.example.dikiy.passwordmain;

import android.app.Application;
import android.content.Context;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by dikiy on 13.02.2018.
 */

public class GetContext extends Application{
    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
