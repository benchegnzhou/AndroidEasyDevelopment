package com.zbc.androideasydevelopment.net;


import com.zbc.androideasydevelopment.BuildConfig;

/**
 * Created by benchengzhou on 2019/7/17  14:07 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 网络请求配置
 * 类    名： API
 * 备    注：
 */

public class API {
    private static String baseUrl = BuildConfig.BAEE_URL;
    private static String COMMON_SERVICE = baseUrl + "pub-service/Service";

    /**
     * 版本更新
     *
     * @return
     */
    public static String getCheckNewVersionUrl() {
        return COMMON_SERVICE + "?service=dataManager&function=checkAppVerson";
    }
}
