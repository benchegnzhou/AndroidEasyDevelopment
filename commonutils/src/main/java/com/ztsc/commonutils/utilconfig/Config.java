package com.ztsc.commonutils.utilconfig;

/**
 * 工具类的通用config
 */
public class Config {
    public boolean logOpen = true;
    public String logTag = "ZHENG_TU_SHU_CHUANG";
    //吐司开关
    public boolean toastOpen = true;
    //本地缓存文件名称
    public String aCacheFname = "Demo_cache";

    public Config() {

    }

    public Config(boolean logOpen, String logTag, boolean toastOpen, String aCacheFname) {
        this.logOpen = logOpen;
        this.logTag = logTag;
        this.toastOpen = toastOpen;
        this.aCacheFname = aCacheFname;
    }

    /**
     * 打开日志工具类
     *
     * @param LogOpen
     * @return
     */
    public Config setLogOpen(boolean LogOpen) {
        this.logOpen = LogOpen;
        return this;
    }

    /**
     * 设置log的tag
     *
     * @param LogTag
     * @return
     */
    public Config setLogTag(String LogTag) {
        this.logTag = LogTag;
        return this;
    }


    /**
     * 设置toast是否开启
     * 不设置默认开启toast显示
     *
     * @param ToastOpen
     * @return
     */
    public Config setToastOpen(boolean ToastOpen) {
        this.toastOpen = ToastOpen;
        return this;
    }

    public String getACacheFname() {
        return aCacheFname;
    }

    public Config setACacheFname(String ACacheFname) {
        this.aCacheFname = ACacheFname;
        return this;
    }
}
