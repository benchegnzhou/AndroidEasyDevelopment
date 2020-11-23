package com.ztsc.moudleuseguide.util

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.ztsc.commonutils.logcat.LoggerUtil
import com.ztsc.commonutils.toast.ToastUtils

/**
 * Created by benchengzhou on 2020/11/2  14:00 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 前台进程回调器
 * 类    名：
 * 备    注：
 */

object ForegroundCallbacks : Application.ActivityLifecycleCallbacks {

    fun init(application: Application): ForegroundCallbacks {
        application.registerActivityLifecycleCallbacks(this)
        return this
    }


    override fun onActivityPaused(activity: Activity) {
        ToastUtils.normal("当前activity：${activity::class.java.simpleName} ， 调用了自己的 onActivityPaused")
        LoggerUtil.d("当前activity：${activity::class.java.simpleName} ， 调用了自己的 onActivityPaused")
    }

    override fun onActivityStarted(activity: Activity) {
        ToastUtils.normal("当前activity：${activity::class.java.simpleName} ， 调用了自己的 onActivityStarted")
        LoggerUtil.d("当前activity：${activity::class.java.simpleName} ， 调用了自己的 onActivityStarted")
    }

    override fun onActivityDestroyed(activity: Activity) {
        ToastUtils.normal("当前activity：${activity::class.java.simpleName} ， 调用了自己的 onActivityDestroyed")
        LoggerUtil.d("当前activity：${activity::class.java.simpleName} ， 调用了自己的 onActivityDestroyed")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        ToastUtils.normal("当前activity：${activity::class.java.simpleName} ， 调用了自己的 onActivitySaveInstanceState")
        LoggerUtil.d("当前activity：${activity::class.java.simpleName} ， 调用了自己的 onActivitySaveInstanceState")
    }

    override fun onActivityStopped(activity: Activity) {
        ToastUtils.normal("当前activity：${activity::class.java.simpleName} ， 调用了自己的 onActivityStopped")
        LoggerUtil.d("当前activity：${activity::class.java.simpleName} ， 调用了自己的 onActivityStopped")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        ToastUtils.normal("当前activity：${activity::class.java.simpleName} ， 调用了自己的 onActivityCreated")
        LoggerUtil.d("当前activity：${activity::class.java.simpleName} ， 调用了自己的 onActivityCreated")
    }

    override fun onActivityResumed(activity: Activity) {
        ToastUtils.normal("当前activity：${activity::class.java.simpleName} ， 调用了自己的 onActivityResumed")
        LoggerUtil.d("当前activity：${activity::class.java.simpleName} ， 调用了自己的 onActivityResumed")
    }

}