package com.adouble.recorddemo;

import android.app.Application;

/**
 * Created by double on 16-6-14.
 * Project: WayTone
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;

    }

}
