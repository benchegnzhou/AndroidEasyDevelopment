package com.ztsc.house

import android.os.Bundle
import androidx.annotation.NonNull


/**
 * Created by benchengzhou on 2019/7/19  11:10 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： ui通用接口定义
 * 类    名： IBaseViewUi
 * 备    注：
 */

interface IBaseViewUI {


    /**
     * 获取布局文件
     */
    fun getContentView(): Int

    fun getInflateView(): Int

    /**
     * 初始化监听任务和注册适配器
     */
    fun initListener()

    /**
     * 完成数据的初始化任务
     */
    fun initData()

    /**
     * 获取一个bundle
     */
    fun getBundle(): Bundle


    fun Bundle.put(key: String, value:Any?): Bundle

    /**
     * 打开一个activity
     */
    fun startAct(@NonNull clazz: Class<*>)

    /**
     * 打开一个activity
     */
    fun startAct(@NonNull clazz: Class<*>, bundle: Bundle?)

    /**
     * 打开一个activity
     */
    fun startAct(@NonNull clazz: Class<*>, killSelf: Boolean)

    /**
     * 打开一个activity
     */
    fun startAct(@NonNull clazz: Class<*>, killSelf: Boolean, bundle: Bundle?)

    /**
     * 打开一个activity
     */
    fun startActAfterLogin()

    /**
     * 使用bundle传递数据，默认关闭
     */
    fun startAct(bundle: Bundle?, @NonNull clazz: Class<*>)

    /**
     * 使用bundle传递数据,选择是否关闭
     */
    fun startAct(bundle: Bundle?, @NonNull clazz: Class<*>, killSelf: Boolean)

    /**
     * 开启一个activity，以获取数据，本身不会结束自己
     */
    fun startActForResult(@NonNull clazz: Class<*>, requestCode: Int)

    /**
     * 开启一个activity，以获取数据，本身不会结束自己，可以通过bundle传递数据
     */
    fun startActForResult(bundle: Bundle?, @NonNull clazz: Class<*>, requestCode: Int)


    fun finishAct()

    fun finishActWithCode(resultCode: Int)


    /**
     * 检查登录，没有登录直接登录
     */
    fun checkLogin(): Boolean


}