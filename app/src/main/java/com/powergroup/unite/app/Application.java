package com.powergroup.unite.app;

import com.facebook.FacebookSdk;

/**
 * Created by bummy on 4/1/17.
 */

/*
create your own instance of Application so you can grab the context from anywhere
 */
public class Application extends android.app.Application {
    private static Application instance;

    //make it static so it's available everywhere
    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //this is also where you should initialize things like firebase or facebook
        FacebookSdk.sdkInitialize(this);
    }
}
