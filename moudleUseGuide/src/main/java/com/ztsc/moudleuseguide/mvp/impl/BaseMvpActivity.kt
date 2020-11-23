package com.zbc.mvp.impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity

import com.ztsc.commonutils.Util
import com.ztsc.house.IBaseViewUI

import com.ztsc.house.mvp.IMvpView
import com.ztsc.house.mvp.IPresenter
import com.ztsc.house.mvp.impl.BasePresenter
import com.ztsc.house.statistics.IUserBehaviorStatistics
import com.ztsc.moudleuseguide.helper.JGJanalyticsHelper
import com.ztsc.moudleuseguide.helper.JGJanalyticsHelper.onCountEvent
import com.ztsc.moudleuseguide.ui.base.addClick
import java.io.Serializable
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure


/**
 * Created by benchengzhou on 2020/7/17  17:13 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 基础类
 * 类    名：
 * 备    注：
 */

abstract class BaseMvpActivity<out P : BasePresenter<BaseMvpActivity<P>>> : IMvpView<P>,
    AppCompatActivity(), IUserBehaviorStatistics, IBaseViewUI, View.OnClickListener,
    View.OnLongClickListener {

    override val presenter: P


    init {
        presenter = createPresenterKt()
        presenter.view = this
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        otherInit()
        setContentView(getContentView())
        presenter.onCreate(savedInstanceState)
        setStatusBar()
        initListener()
        initData()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }


    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onViewStateRestored(saveInstanceState: Bundle?) {

    }

    fun otherInit() {

    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    override abstract fun initListener()

    override abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }


    /**
     * 创建  Presenter
     */
    fun createPresenterKt(): P {

        sequence {
            var thisClass: KClass<*> = this@BaseMvpActivity::class
            while (true) {
                //https://www.jianshu.com/p/9f720b9ccdea?utm_source=desktop&utm_medium=timeline
                //https://blog.csdn.net/weixin_34306593/article/details/89691830
                //yield是一个suspend方法, 放弃执行权, 并将数据返回.
                yield(thisClass.supertypes)
                //找到thisClass的所有父类，supertypes是包含接口的
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure ?: break
            }
        }.flatMap {
            it.flatMap {
                //获取到泛型参数
                it.arguments
            }.asSequence()
        }.first {
            it.type?.jvmErasure?.isSubclassOf(IPresenter::class) ?: false
        }.let {
            return it.type!!.jvmErasure.primaryConstructor!!.call() as P
        }
        return null as P
    }


    open fun setStatusBar() {}

    /**
     * 完成数据的初始化任务
     */
    //    protected abstract void initData();
    open override fun startAct(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }

    open override fun startAct(clazz: Class<*>, bundle: Bundle?) {
        val initent = Intent(this, clazz)
        bundle?.apply { initent.putExtras(bundle) }
        startActivity(initent)
    }


    open override fun startAct(clazz: Class<*>, killSelf: Boolean, bundle: Bundle?) {
        val initent = Intent(this, clazz)
        bundle?.apply { initent.putExtras(bundle) }
        startActivity(initent)
        if (killSelf) {
            finishAct()
        }
    }

    open override fun Bundle.put(key: String, value: Any?): Bundle {
        if (key.isEmpty() || value == null) {
            return this
        }
        when {
            value is String -> this.putSerializable(key, value)
            value is Short -> this.putShort(key, value)
            value is CharSequence -> this.putCharSequence(key, value)
            value is Int -> this.putInt(key, value)
            value is Boolean -> this.putBoolean(key, value)
            value is Float -> this.putFloat(key, value)
            value is Parcelable -> this.putParcelable(key, value)
        }
        return this
    }

    open override fun getBundle(): Bundle {
        return Bundle()
    }

    open override fun startAct(clazz: Class<*>, killSelf: Boolean) {
        startActivity(Intent(this, clazz))
        if (killSelf) {
            finishAct()
        }
    }

    open override fun startAct(bundle: Bundle?, clazz: Class<*>) {
        val intent = Intent(this, clazz)
        intent.putExtras(bundle ?: Bundle())
        startActivity(intent)
        finishAct()
    }

    open override fun startAct(bundle: Bundle?, clazz: Class<*>, killSelf: Boolean) {
        val intent = Intent(this, clazz)
        intent.putExtras(bundle ?: Bundle())
        startActivity(intent)
        if (killSelf) {
            finishAct()
        }
    }

    open override fun startActForResult(clazz: Class<*>, requestCode: Int) {
        startActivityForResult(Intent(this, clazz), requestCode)
    }

    open override fun startActForResult(bundle: Bundle?, clazz: Class<*>, requestCode: Int) {
        val intent = Intent(this, clazz)
        intent.putExtras(bundle ?: Bundle())
        startActivityForResult(intent, requestCode)
    }


    open override fun finishAct() {
        finish()
    }

    open override fun finishActWithCode(resultCode: Int) {
        setResult(resultCode)
        finishAct()
    }

    override abstract fun getContentView(): Int

    override fun getInflateView(): Int {
        return 0
    }

    override fun startActAfterLogin() {

    }

    override fun checkLogin(): Boolean {
        return true
    }


    override fun onClick(v: View?) {

    }

    override fun onUserOpen(context: Context, eventId: String, pageName: String) {
        /* onCountEvent(context, eventId
                 , JGJanalyticsHelper.JGEvent()
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.PAGE_NAME.keyWordId, pageName + "")
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.PHONE_NUMBER.keyWordId, UserInformationManager.getInstance().phoneNum)
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.USER_ID.keyWordId, UserInformationManager.getInstance().userId)
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.USER_DEPT_NAME.keyWordId, UserInformationManager.getInstance().userDeptmentName)
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.MACHINE_ID.keyWordId, DeviceMessageUtils.getIMEI(MApplicationInfo.sAppContext))
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.MACHINE_NAME.keyWordId, DeviceMessageUtils.getMobileInfo(MApplicationInfo.sAppContext))
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.MACHINE_VERSION_CODE.keyWordId, "android_os_" + DeviceMessageUtils.getSDKVersion())
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.APP_VERSION_CODE.keyWordId, Util.getVersionCode(MApplicationInfo.sAppContext).toString() + "")
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.APP_VERSION_NAME.keyWordId, Util.getVersion(MApplicationInfo.sAppContext) + "")
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.APP_CHANNEL.keyWordId, Util.getChannel(MApplicationInfo.sAppContext, ""))
                 .build())*/
    }

    override fun onUserOpen(
        context: Context,
        eventId: JGJanalyticsHelper.CustomEventId,
        pageName: String
    ) {
        /* onCountEvent(context, eventId.eventId
                 , JGJanalyticsHelper.JGEvent()
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.PAGE_NAME.keyWordId, pageName + "")
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.PHONE_NUMBER.keyWordId, UserInformationManager.getInstance().phoneNum)
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.USER_ID.keyWordId, UserInformationManager.getInstance().userId)
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.USER_DEPT_NAME.keyWordId, UserInformationManager.getInstance().userDeptmentName)
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.MACHINE_ID.keyWordId, DeviceMessageUtils.getIMEI(MApplicationInfo.sAppContext))
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.MACHINE_NAME.keyWordId, DeviceMessageUtils.getMobileInfo(MApplicationInfo.sAppContext))
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.MACHINE_VERSION_CODE.keyWordId, "android_os_" + DeviceMessageUtils.getSDKVersion())
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.APP_VERSION_CODE.keyWordId, Util.getVersionCode(MApplicationInfo.sAppContext).toString() + "")
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.APP_VERSION_NAME.keyWordId, Util.getVersion(MApplicationInfo.sAppContext) + "")
                 .addKeyValue(JGJanalyticsHelper.CustomKeyWord.APP_CHANNEL.keyWordId, Util.getChannel(MApplicationInfo.sAppContext, ""))
                 .build())*/
    }


    override fun onUserOpen(
        context: Context,
        eventId: JGJanalyticsHelper.CustomEventId,
        pageName: String,
        jgEvent: JGJanalyticsHelper.JGEvent
    ) {
        onCountEvent(context, eventId.eventId, jgEvent.build())
    }
}