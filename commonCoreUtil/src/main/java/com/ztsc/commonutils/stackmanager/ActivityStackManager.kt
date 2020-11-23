package com.ztsc.commonutils.stackmanager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.collection.ArrayMap

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/11/18
 *    desc   : Activity 栈管理
 */
object ActivityStackManager : Application.ActivityLifecycleCallbacks {


    private val mActivitySet = ArrayMap<String, Activity>()

    /** 当前应用上下文对象  */
    private var mApplication: Application? = null

    /** 当前 Activity 对象标记  */
    private var mCurrentTag: String? = null

    private fun ActivityStackManager() {}


    fun init(application: Application) {
        mApplication = application
        application.registerActivityLifecycleCallbacks(this)
    }

    /**
     * 获取 Application 对象
     */
    fun getApplication(): Application? {
        return mApplication
    }

    /**
     * 获取栈顶的 Activity
     */
    fun getTopActivity(): Activity? {
        return mActivitySet[mCurrentTag]
    }

    /**
     * 销毁所有的 Activity
     */
    fun finishAllActivities() {
        finishAllActivities((null as Class<out Activity?>?)!!)
    }

    /**
     * 销毁所有的 Activity，除这些 Class 之外的 Activity
     */
    @SafeVarargs
    fun finishAllActivities(vararg classArray: Class<out Activity?>) {

        for (key in mActivitySet.keys) {
            val activity = mActivitySet[key]
            if (activity != null && !activity.isFinishing) {
                var whiteClazz = false
                if (classArray != null) {
                    for (clazz in classArray) {
                        if (activity::class.java == clazz) {
                            whiteClazz = true
                        }
                    }
                }
                // 如果不是白名单上面的 Activity 就销毁掉
                if (!whiteClazz) {
                    activity.finish()
                    mActivitySet.remove(key)
                }
            }
        }
    }

    /**
     * 获取一个对象的独立无二的标记
     */
    private fun getObjectTag(any: Any): String {
        // 对象所在的包名 + 对象的内存地址
        return any::class.java.name + Integer.toHexString(any.hashCode())
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        mCurrentTag = getObjectTag(activity)
        mActivitySet.put(getObjectTag(activity), activity)
    }

    override fun onActivityStarted(activity: Activity) {
        mCurrentTag = getObjectTag(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        mCurrentTag = getObjectTag(activity)
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        mActivitySet.remove(getObjectTag(activity))
        if (getObjectTag(activity) == mCurrentTag) {
            // 清除当前标记
            mCurrentTag = null
        }
    }


}