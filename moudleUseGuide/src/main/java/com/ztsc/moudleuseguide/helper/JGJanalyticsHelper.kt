package com.ztsc.moudleuseguide.helper

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import cn.jiguang.analytics.android.api.*
import cn.jiguang.analytics.android.api.Currency
import com.ztsc.commonutils.Util
import com.ztsc.commonutils.logcat.LoggerUtil
import com.ztsc.moudleuseguide.application.MApplacation
import java.util.*

/**
 * Created by benchengzhou on 2020/8/26  13:56 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 极光统计
 * 类    名： JGJanalyticsHelper
 * 备    注：
 */

object JGJanalyticsHelper {


    val KEY_APP_KEY = "570d336540528f4c86fbdfc0"

    var mContext: Context = MApplacation.context


    /**
     * 初始化极光,在Application的oncreate()方法中调用
     *
     * @param sApplication
     */
    fun initJGJanalyticsHelper(sApplication: Application?) {
        //设置是否开启debug模式。true则会打印更多的日志信息。建议在init接口之前调用。
        JAnalyticsInterface.setDebugMode(true)
        JAnalyticsInterface.init(sApplication)
        //动态配置channel，优先级比AndroidManifest里配置的高
        JAnalyticsInterface.setChannel(sApplication, Util.getChannel(sApplication, "ztsc"))
        //：周期，单位秒，最小10秒，最大1天，超出范围会打印调用失败日志。传0表示统计数据即时上报
        JAnalyticsInterface.setAnalyticsReportPeriod(sApplication, 4 * 60)
    }

    /**
     * 开启crashlog日志上报
     */
    fun openCrashLog() {
        JAnalyticsInterface.initCrashHandler(mContext)
    }

    /**
     * 关闭crashlog日志上报
     */
    fun closeCrashLog() {
        JAnalyticsInterface.stopCrashHandler(mContext)
    }

    /**
     * 页面启动接口。在页面(activity和fragment)的相关生命周期内调用，和onPageEnd需要成对调用
     *
     * @param context  activity的上下文
     * @param pageName 页面名称
     */
    fun onPageStart(context: Context, pageName: String) {
        JAnalyticsInterface.onPageStart(context, pageName)
    }

    /**
     * 页面结束接口。在页面(activity和fragment)的相关生命周期内调用，和onPageStart需要成对调用
     *
     * @param context  activity的上下文
     * @param pageName 页面名称
     */
    fun onPageEnd(context: Context, pageName: String) {
        JAnalyticsInterface.onPageEnd(context, pageName)
    }

    /**
     * 注册事件
     *
     * @param context
     * @param extra   map的key不能为"register_method","register_success"
     */
    fun onRegisterEvent(context: Context, extra: Map<String, String?>) {
        val registerEvent = RegisterEvent("RegisterMethod", true)
        //添加自己的Extra 信息
        for ((key, value) in extra) {
            registerEvent.addKeyValue(key, value)
        }
        JAnalyticsInterface.onEvent(context, registerEvent)
    }

    /**
     * 注册事件
     *
     * @param context
     * @param registerMethod 注册方式
     * @param isSuccess      是否成功
     * @param extra          附加信息 map的key不能为"register_method","register_success"
     */
    fun onRegisterEvent(
        context: Context,
        registerMethod: String,
        isSuccess: Boolean,
        extra: Map<String, String?>?
    ) {
        val registerEvent = RegisterEvent()
            .setRegisterMethod(registerMethod) //注册方式
            .setRegisterSuccess(isSuccess) //是否成功
            .addExtMap(extra)
        JAnalyticsInterface.onEvent(context, registerEvent)
    }

    /**
     * 登陆事件
     *
     * @param context
     * @param extra   map的key不能为"login_method","login_success"
     */
    fun onLoginEvent(context: Context, extra: Map<String, String?>) {
        val loginEvent = LoginEvent(CustomEventId.PWLOGING.eventId, true)
        //添加自己的Extra 信息
        for ((key, value) in extra) {
            loginEvent.addKeyValue(key, value)
        }
        JAnalyticsInterface.onEvent(context, loginEvent)
    }

    /**
     * 登录事件
     *
     * @param context
     * @param loginMethod 登录方式
     * @param isSuccess   是否成功
     * @param extra       附加信息  map的key不能为"login_method","login_success"
     */
    fun onLoginEvent(
        context: Context,
        loginMethod: String,
        isSuccess: Boolean,
        extra: Map<String, String?>?
    ) {
        val loginEvent = LoginEvent()
            .setLoginMethod(loginMethod) //login方式
            .setLoginSuccess(isSuccess) //是否成功
            .addExtMap(extra)
        JAnalyticsInterface.onEvent(context, loginEvent)
    }

    /**
     * 购买事件
     *
     * @param context
     */
    fun onPurchaseEvent(context: Context, extra: Map<String, String?>) {
        val purchaseEvent = PurchaseEvent(
            "test_purchaseGoodsID",  //商品ID
            "篮球",  //商品名称
            300.0,  //商品价格
            true,  //商品购买是否成功
            Currency.CNY,  //货币类型
            "sport",  //商品类型
            1
        ) //商品购买数量
        //添加自己的Extra 信息
        for ((key, value) in extra) {
            purchaseEvent.addKeyValue(key, value)
        }
        JAnalyticsInterface.onEvent(context, purchaseEvent)
    }

    /**
     * 购买事件
     *
     * @param context
     * @param purchaseGoodsid    物品id，开发者自己定义
     * @param purchaseGoodsname  购买物品名称(如lv ，香奈儿，劳力士表，安踏运动鞋 etc）
     * @param purchasePrice      购买价格
     * @param purchaseSuccess    购买是否成功
     * @param purchaseCurrency   购买货币类型(人民币(CNY)，美元(USD) ,具体参考Currency)
     * @param purchaseGoodsCount 购买数量
     * @param purchaseGoodstype  购买物品类型(如衣服，手表，鞋子，家用电器etc)
     */
    fun onPurchaseEvent(
        context: Context,
        purchaseGoodsid: String,
        purchaseGoodsname: String?,
        purchasePrice: Double,
        purchaseSuccess: Boolean,
        purchaseCurrency: Currency?,
        purchaseGoodstype: String?,
        purchaseGoodsCount: Int,
        extra: Map<String?, String?>?
    ) {
        val purchaseEvent = PurchaseEvent()
            .setPurchaseGoodsid(purchaseGoodsid) //商品ID
            .setPurchaseGoodsname(purchaseGoodsname) //商品名称
            .setPurchasePrice(purchasePrice) //商品价格
            .setPurchaseSuccess(purchaseSuccess) //购买是否成功
            .setPurchaseCurrency(purchaseCurrency) //货币类型
            .setPurchaseGoodstype(purchaseGoodstype) //商品类型
            .setPurchaseGoodsCount(purchaseGoodsCount)
            .addExtMap(extra)
        JAnalyticsInterface.onEvent(context, purchaseEvent)
    }

    /**
     * 浏览事件
     *
     * @param context
     * @param browseId       浏览内容id，开发者自己定义
     * @param browseName     浏览的内容的名称(如内容标题等)
     * @param browseType     浏览的内容类型(如是热点，还是nba，还是cba，还是汽车，财经 etc)
     * @param browseDuration 浏览的内容时长，单位秒
     * @param extra          附加信息
     */
    fun onBrowseEvent(
        context: Context,
        browseId: String,
        browseName: String,
        browseType: String,
        browseDuration: Float,
        extra: Map<String, String?>?
    ) {
        val browseEvent = BrowseEvent()
            .setBrowseId(browseId) //设置浏览内容id
            .setBrowseName(browseName) //设置浏览的内容的名称
            .setBrowseType(browseType) //设置浏览的内容类型
            .setBrowseDuration(browseDuration) //设置浏览的内容时长
            .addExtMap(extra)
        JAnalyticsInterface.onEvent(context, browseEvent)
    }

    /**
     * 计算事件
     *
     * @param context
     * @param eventId    事件ID
     * @param eventValue 事件对应的值
     * @param extra      附加信息
     */
    fun onCalculateEvent(
        context: Context,
        eventId: String,
        eventValue: Double,
        extra: Map<String, String?>?
    ) {
        val calculateEvent = CalculateEvent()
            .setEventId(eventId)
            .setEventValue(eventValue)
            .addExtMap(extra)
        JAnalyticsInterface.onEvent(context, calculateEvent)
    }


    /**
     * 计数事件
     *
     * @param context
     * @param eventId 事件ID
     * @param extra   附加信息
     */
    fun onCountEvent(context: Context, eventId: String, extra: Map<String, String?>) {
        val countEvent = CountEvent()
            .setEventId(eventId)
            .addExtMap(extra)
        JAnalyticsInterface.onEvent(context, countEvent)
    }

    // 取得AppKey
    fun getAppKey(context: Context): String? {
        var metaData: Bundle? = null
        var appKey: String? = null
        try {
            val ai = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            if (ai != null) {
                metaData = ai.metaData
            }
            if (null != metaData) {
                appKey = metaData.getString(KEY_APP_KEY)
                if (null == appKey || appKey.length != 24) {
                    appKey = null
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            LoggerUtil.e("===error======" + e.message)
        }
        return appKey
    }


    class JGEvent {
        var hashMap: MutableMap<String, String> = HashMap()
        fun addKeyValue(key: String, value: String?): JGEvent {
            init()
            value?.apply {
                hashMap[key] = value
            }
            return this
        }

        fun addKeyValue(key: String, value: Int): JGEvent {
            init()
            hashMap[key] = value.toString()
            return this
        }

        fun addKeyValue(key: String, value: Any?): JGEvent {
            init()
            value?.apply {
                hashMap[key] = value.toString()
            }
            return this
        }

        fun build(): Map<String, String> {
            return hashMap
        }

        private fun init() {
            if (hashMap == null) {
                hashMap = HashMap()
            }
        }
    }


    /**
     * 自定义事件id
     * 每新增一个id需要同步注册极光事件ID
     */
    enum class CustomEventId(var eventId: String, var eventDesc: String) {
        //APP被启动事件
        APP_START("app_start", "APP被启动"),  //用户注册失败
        USER_FAIL_REGISTER("user_fail_register", "用户注册失败"),  //用户登录失败
        USER_FAIL_LOGIN("user_fail_login", "用户登录失败"),  //用户名密码登录
        PWLOGING("PWLogin", "用户名密码登录"),  //本机号码一键登录
        FASTLOGING("FastLogin", "本机号码一键登录"),  //验证码登录
        CODE_LOGING("CodeLogin", "验证码登录"),
        HOME_TABLE_WORKBENCH("home_table_workbench", "主页标签-工作台"),
        HOME_TABLE_MSG("home_table_msg", "主页标签-消息"),
        HOME_TABLE_ADDRESSBOOK("home_table_addressbook", "主页标签-通讯录"),
        HOME_TABLE_ABOUTME("home_table_aboutme", "主页标签-我的");

    }


    enum class CustomKeyWord(var keyWordId: String, var keyWordDesc: String) {
        PAGE_NAME("pageName", "页面名称"),
        PHONE_NUMBER("phoneNumber", "用户手机号"),
        USER_ID("userId", "用户id"),
        USER_DEPT_NAME("userDept", "用户岗位名称"),
        MACHINE_ID("machineId", "机器唯一识别号"),
        MACHINE_NAME("machineName", "手机品牌名称"),
        MACHINE_VERSION_CODE("machineVer", "手机Os版本"),
        APP_VERSION_CODE("appVcode", "APP版本号"),
        APP_VERSION_NAME("appVname", "APP版本名称"),
        APP_CHANNEL("appChannel", "App下载渠道"),

    }

}