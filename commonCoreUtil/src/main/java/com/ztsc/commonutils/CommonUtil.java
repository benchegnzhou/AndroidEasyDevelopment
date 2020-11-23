package com.ztsc.commonutils;


import android.app.Application;
import android.content.Context;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.ztsc.commonutils.errorcatch.CrashHandler;
import com.ztsc.commonutils.utilconfig.Config;

/**
 * 项目通用工具类同统一初始化入口
 */
public class CommonUtil {
    private Context mContext;
    private Config mConfig;

    private CommonUtil() {

    }

    public static CommonUtil getInstance() {
        return instance2.commonUtil;
    }

    private static class instance2 {
        private static CommonUtil commonUtil = new CommonUtil();
    }

    /**
     * 工具类统一初始化入口
     */
    public void init(Application context) {
        init(context, new Config());
    }

    /**
     * 工具类统一初始化入口
     * 创建一个自定义配置的logcat
     */
    public CommonUtil init(Application context, Config config) {
        mContext = context;
        mConfig = config;
        try {
            Utils.init(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loggerinit();
        return this;
    }

    /**
     * Logger初始化
     * https://github.com/orhanobut/logger
     */
    private void loggerinit() {
        PrettyFormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)         // （可选）要显示的方法行数。 默认2
                .methodOffset(5)        // （可选）隐藏内部方法调用到偏移量。 默认5
                .tag(mConfig.LogTag)//（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                try {
                    return mConfig.LogOpen;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.isLoggable(priority, tag);
            }
        });
    }


    /**
     * 设置初始化化启用崩溃本地日志抓取和日志上报
     */
    public CommonUtil enableCrash() {
        if (mContext == null) {
            throw new NullPointerException("this mContext should not be null,you may try call init method first");
        }
        CrashHandler.getInstance().init(mContext);
        return this;
    }

    /**
     * 获取已经初始化
     * 如果这个类没有被初始化，应当则会报错要求用户进行初始化
     *
     * @return
     */
    public Context getApplicationContext() {
        if (mContext == null) {
            throw new NullPointerException("you should call method \"init\" first in your application, the method on CommonUtil class");
        }
        return mContext;
    }

    /**
     * 读取配置文件
     *
     * @return
     */
    public Config getConfig() {
        return mConfig;
    }
}
