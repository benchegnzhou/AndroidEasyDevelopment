package com.ztsc.moudleuseguide.application

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import com.alibaba.android.arouter.launcher.ARouter
import com.jeremyliao.liveeventbus.LiveEventBus
import com.ztsc.commonutils.CommonUtil
import com.ztsc.commonutils.utilconfig.Config
import com.ztsc.moudleuseguide.BuildConfig
import com.ztsc.moudleuseguide.util.ForegroundCallbacks
import es.dmoral.toasty.Toasty

class MApplacation : Application() {

    companion object {
        lateinit var context: Context
    }


    override fun onCreate() {
        super.onCreate()
        CommonUtil.getInstance().init(
            this, Config()
                .setLogOpen(true)
                .setLogTag("ZHENG_TU_SHU_CHUANG")
                .setToastOpen(true)
                .setLocalLogDir("")
        ) /*.enableCrash()*/
        initToast()
        ForegroundCallbacks.init(this)
        initARouter()
    }

    private fun initToast() {
        Toasty.Config.getInstance()
            .tintIcon(true)         // optional (apply textColor also to the icon)
            .setToastTypeface(Typeface.DEFAULT_BOLD) // optional
            .setTextSize(18)                // optional
            .allowQueue(false)   // optional (prevents several Toastys from queuing)
            .apply()                        // required
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        context = base!!
    }

    fun initARouter() {
        //https://blog.csdn.net/u011368551/article/details/104437399?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.channel_param
        if (BuildConfig.DEBUG_ENABLE) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog()      // Print log
            ARouter.openDebug()    // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this) // As early as possible, it is recommended to initialize in the Application
    }



}