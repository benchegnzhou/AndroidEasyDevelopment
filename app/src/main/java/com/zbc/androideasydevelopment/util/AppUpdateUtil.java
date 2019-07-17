package com.zbc.androideasydevelopment.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zbc.androideasydevelopment.application.MApplicationInfo;
import com.zbc.androideasydevelopment.bean.AppCheckNewVersion;
import com.zbc.androideasydevelopment.callback.JsonCallback;
import com.zbc.androideasydevelopment.net.API;
import com.zbc.updates.UpdateAppBean;
import com.zbc.updates.UpdateAppManager;
import com.zbc.updates.UpdateCallback;
import com.ztsc.commonutils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AppUpdateUtil {

    private static final int IS_PHONE = 0;
    private static final int IS_PAD = 1;
    public static final int TAG_MAY = 1000;
    public static final int TAG_MUST = 2000;

    private AppUpdateUtil() {
    }

    public static AppUpdateUtil getInstance() {
        return getInstance2.appUpdateUtil;
    }

    private static class getInstance2 {
        private static AppUpdateUtil appUpdateUtil = new AppUpdateUtil();
    }


    public void checkNewVersion(@NonNull Activity activity) {


        HashMap<String, String> hashMap = new HashMap<>();
        /**
         * 检测APP是否有新版本
         */
        public void checkNewAppVersion ( final Context context){

            OkGo.<AppCheckNewVersion>post(API.getCheckNewVersionUrl())
                    .tag("AppCheckNewVersion")
                    .params("appVersion", Util.getVersion(MApplicationInfo.sAppContext))
                    .params("appVersionCode", Util.getVersionCode(MApplicationInfo.sAppContext))
                    .params("appPort", "publicApp")
                    .params("sdkVersion", Build.VERSION.SDK_INT) //当前OS 版本号
                    .params("isPad", IS_PHONE)
                    .params("phoneBrand", Build.MODEL)
                    .execute(new JsonCallback<AppCheckNewVersion>(AppCheckNewVersion.class) {
                        @Override
                        public void onSuccess(Response<AppCheckNewVersion> response) {
                            try {
                                if (response.code() == 200) {
                                    AppCheckNewVersion.ResultBean result = response.body().getResult();
                    AppVersionManager.getInstance().init().saveNewVersionMsg(new NewAppVersionBean(result.getUpdateNecess()
                                        , result.getDownUrl()
                                        , APP_BASE_PATH + APP_NAME_PREFIX + result.getNetVersion() + APP_NAME_SUFFIX
                                        , result.getUpdateMsg()
                                        , result.getAppSize()
                                        , result.getNetVersion()
                                        , versionIgored));
                                    UpdateAppBean updateAppBean = new UpdateAppBean();
                                    updateAppBean.
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });




                            /*      //删除就安装包
                                clearOldApk(APP_BASE_PATH + APP_NAME_PREFIX + result.getNetVersion() + APP_NAME_SUFFIX);

                                getSp().putStringValue(UPDATA_KEY_MSG, result.getUpdateMsg());
                                getSp().putStringValue(UPDATA_KEY_VERSION, result.getNetVersion());
                                getSp().putStringValue(UPDATA_KEY_URL, normalPicList.get(0));
                                getSp().putLongValue(UPDATA_KEY_SIZE, result.getAppSize());
                                //版本号增加重置下载标识
                                String lastSuccessVersion = getSp().getStringValue(UPDATA_KEY_DPWN_SUCCESS_VERSION);
                                if (TextUtils.isEmpty(lastSuccessVersion) || !lastSuccessVersion.equals(result.getNetVersion())) {
                                    getSp().putBooleanValue(UPDATA_KEY_DOWN_SUCCESS, false);
                                }

                                switch (result.getUpdateNecess()) {
                                    case "igore":

                                        break;
                                    case "may":
                                        AppVersionManager manager = AppVersionManager.getInstance().init();
                                        if (!manager.checkVersionIgored(result.getNetVersion())) {
                                            showMayDialog(context, result);
                                        }

                                        break;
                                    case "must":
                                        showMustDialog(context);
                                        break;
                                    default:
                                }
                            }
                        }*/




        new UpdateAppManager
                .Builder()
                //当前Activity
                .setActivity(activity)
                //更新地址
                .setUpdateUrl("http:192.168.0.1:8081")
                .setPost(true)
                .setParams()
                .build()
                .checkNewApp(new UpdateCallback() {
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            updateAppBean
                                    //（必须）是否更新Yes,No
                                    .setUpdate(jsonObject.optString("update"))
                                    //（必须）新版本号，
                                    .setNewVersion(jsonObject.optString("new_version"))
                                    //（必须）下载地址
                                    .setApkFileUrl(jsonObject.optString("apk_file_url"))
                                    //（必须）更新内容
                                    .setUpdateLog(jsonObject.optString("update_log"))
                                    //大小，不设置不显示大小，可以不设置
                                    .setTargetSize(jsonObject.optString("target_size"))
                                    //是否强制更新，可以不设置
                                    .setConstraint(false)
                            //设置md5，可以不设置
                            /*.setNewMd5(jsonObject.optString("new_md51"))*/;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;

                    }
                });
    }


}
