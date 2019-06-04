package com.njupt.cicada.restaurantreservation.base;

import android.content.Context;


import com.cicada.library.base.BaseApplication;

import net.nashlegend.anypref.AnyPref;

/**
 * @author guocongcong
 * @Date 2018/6/7
 * @Describe
 */

public class App extends BaseApplication {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //初始化SharedPreferences库
        AnyPref.init(this);
    }

}
