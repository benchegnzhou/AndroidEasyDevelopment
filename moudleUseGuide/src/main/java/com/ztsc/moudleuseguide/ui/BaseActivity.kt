package com.ztsc.moudleuseguide.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import java.util.*

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : Activity 基类
 */
abstract class BaseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity()
    }

    protected fun initActivity() {
        initLayout()
        initView()
        initData()
    }

    /**
     * 获取布局 ID
     */
    protected abstract fun getlayoutId(): Int

    /**
     * 初始化控件
     */
    protected abstract fun initView()

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 初始化布局
     */
    protected open fun initLayout() {
        if (getlayoutId() > 0) {
            setContentView(getlayoutId())
            initSoftKeyboard()
        }
    }

    /**
     * 初始化软键盘
     */
    protected fun initSoftKeyboard() {
        // 点击外部隐藏软键盘，提升用户体验
        contentView.setOnClickListener { v: View? -> hideSoftKeyboard() }
    }

    override fun onDestroy() {

        super.onDestroy()
    }

    override fun finish() {
        hideSoftKeyboard()
        super.finish()
    }

    /**
     * 如果当前的 Activity（singleTop 启动模式） 被复用时会回调
     */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // 设置为当前的 Intent，避免 Activity 被杀死后重启 Intent 还是最原先的那个
        setIntent(intent)
    }

    val bundle: Bundle
        get() = intent.extras!!

    /**
     * 和 setContentView 对应的方法
     */
    val contentView: ViewGroup
        get() = findViewById(Window.ID_ANDROID_CONTENT)

    val context: Context
        get() = this

    /**
     * startActivityForResult 方法优化
     */
    private var mActivityCallback: OnActivityCallback? = null
    private var mActivityRequestCode = 0
    fun startActivityForResult(
        clazz: Class<out Activity?>?,
        callback: OnActivityCallback?
    ) {
        startActivityForResult(Intent(this, clazz), null, callback)
    }

    fun startActivityForResult(
        intent: Intent,
        callback: OnActivityCallback?
    ) {
        startActivityForResult(intent, null, callback)
    }

    fun startActivityForResult(
        intent: Intent,
        options: Bundle?,
        callback: OnActivityCallback?
    ) {
        // 回调还没有结束，所以不能再次调用此方法，这个方法只适合一对一回调，其他需求请使用原生的方法实现
        if (mActivityCallback == null) {
            mActivityCallback = callback
            // 随机生成请求码，这个请求码必须在 2 的 16 次幂以内，也就是 0 - 65535
            mActivityRequestCode = Random().nextInt(Math.pow(2.0, 16.0).toInt())
            startActivityForResult(intent, mActivityRequestCode, options)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (mActivityCallback != null && mActivityRequestCode == requestCode) {
            mActivityCallback!!.onActivityResult(resultCode, data)
            mActivityCallback = null
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun startActivityForResult(
        intent: Intent,
        requestCode: Int,
        options: Bundle?
    ) {
        hideSoftKeyboard()
        // 查看源码得知 startActivity 最终也会调用 startActivityForResult
        super.startActivityForResult(intent, requestCode, options)
    }

    /**
     * 隐藏软键盘
     */
    private fun hideSoftKeyboard() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        val view = currentFocus
        if (view != null) {
            val manager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (manager != null && manager.isActive(view)) {
                manager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    interface OnActivityCallback {
        /**
         * 结果回调
         *
         * @param resultCode        结果码
         * @param data              数据
         */
        fun onActivityResult(resultCode: Int, data: Intent?)
    }
}