package com.codelang.mainprojects;

import android.app.Application;

import com.codelang.servicecomm.service.Router;

/**
 * @author wangqi
 * @since 2018/1/21 13:23
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Router.getInstance().registerApplication("com.codelang.newsprojects.app.NewsApplication");
        Router.getInstance().registerApplication("com.codelang.homeprojects.app.HomeApplication");
    }
}
