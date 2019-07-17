package com.zbc.updates.bean;

/**
 * Created by benchengzhou on 2019/7/17  14:49 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： APP更新信息
 * 类    名： AppMsgBean
 * 备    注：
 */

public class AppMsgBean {
    /**
     * 更新必要
     */
    private boolean updateNecess;
    /**
     * 下载地址
     */
    private String downUrl;
    /**
     * 更新信息
     */
    private String updateMsg;
    /**
     * APP大小
     */
    private long appSize;
    /**
     * 最新的线上版本号
     */
    private String netVersion;
    /**
     * 更新是否可忽略
     */
    private boolean ingoreable;

    /**
     * 用户是否选择该版本已忽略
     */
    private boolean userIngored;

    /**
     * APP是否已经下载
     */
    private boolean appDowned;
    /**
     * APP存储的本地路径
     */
    private String localPath;

    public AppMsgBean() {

    }

    public AppMsgBean(boolean updateNecess, String downUrl, String updateMsg, long appSize, String netVersion, boolean ingoreable, boolean userIngored, boolean appDowned, String localPath) {
        this.updateNecess = updateNecess;
        this.downUrl = downUrl;
        this.updateMsg = updateMsg;
        this.appSize = appSize;
        this.netVersion = netVersion;
        this.ingoreable = ingoreable;
        this.userIngored = userIngored;
        this.appDowned = appDowned;
        this.localPath = localPath;
    }

    public boolean isUserIngored() {
        return userIngored;
    }

    public void setUserIngored(boolean userIngored) {
        this.userIngored = userIngored;
    }

    public boolean isUpdateNecess() {
        return updateNecess;
    }

    public void setUpdateNecess(boolean updateNecess) {
        this.updateNecess = updateNecess;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getUpdateMsg() {
        return updateMsg;
    }

    public void setUpdateMsg(String updateMsg) {
        this.updateMsg = updateMsg;
    }

    public long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public String getNetVersion() {
        return netVersion;
    }

    public void setNetVersion(String netVersion) {
        this.netVersion = netVersion;
    }

    public boolean isIngoreable() {
        return ingoreable;
    }

    public void setIngoreable(boolean ingoreable) {
        this.ingoreable = ingoreable;
    }

    public boolean isAppDowned() {
        return appDowned;
    }

    public void setAppDowned(boolean appDowned) {
        this.appDowned = appDowned;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
