package com.zbc.androideasydevelopment.bean;


/**
 * Created by benchengzhou on 2018/8/25  11:41 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 检查APP新版本
 * 类    名： AppCheckNewVersion
 * 备    注：
 */

public class AppCheckNewVersion {


    /**
     * code : 200
     * message :
     * result : {"updateNecess":"must","downUrl":"http://192.168.1.96:8800/ZtscApp/file/png/20170815162907.png,","updateMsg":"1224154151","appSize":5000000,"netVersion":"1.1.2"}
     */

    private int code;
    private String message;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * updateNecess : must
         * downUrl : http://192.168.1.96:8800/ZtscApp/file/png/20170815162907.png,
         * updateMsg : 1224154151
         * appSize : 5000000
         * netVersion : 1.1.2
         */

        private String updateNecess;
        private String downUrl;
        private String updateMsg;
        private long appSize;
        private String netVersion;

        public String getUpdateNecess() {
            return updateNecess;
        }

        public void setUpdateNecess(String updateNecess) {
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
    }
}
