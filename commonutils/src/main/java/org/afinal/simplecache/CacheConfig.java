package org.afinal.simplecache;

import android.text.TextUtils;

/**
 * Created by benchengzhou on 2019/7/16  14:49 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： https://github.com/yangfuhai/ASimpleCache
 * 类    名： CacheConfig
 * 备    注： android环境下的一款轻量级的缓存框架
 */

public class CacheConfig {
    public static final String DefAcacheFileName = "CacheConfig";
    public static String aCacheFileName = "";

    /**
     * 获取缓存文件名称
     */
    public static String getDefAcacheFileName() {
        return TextUtils.isEmpty(aCacheFileName) ? DefAcacheFileName : aCacheFileName;
    }
}
