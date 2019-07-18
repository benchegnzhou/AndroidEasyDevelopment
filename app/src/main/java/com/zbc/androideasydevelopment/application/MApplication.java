package com.zbc.androideasydevelopment.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zbc.androideasydevelopment.BuildConfig;


/**
 * Created by benchengzhou on 2019/7/15  10:45 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： MApplication
 * 备    注：
 */

public class MApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initSdk();
        baseInit();
    }

    private void baseInit() {
        MApplicationInfo.getInstance().init(this,getApplicationContext());
    }

    private void initSdk() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();


//        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.LOG_DEBUG;
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
