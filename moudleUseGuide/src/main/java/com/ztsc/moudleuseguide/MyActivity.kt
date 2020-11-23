package com.ztsc.moudleuseguide

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.os.HandlerCompat.postDelayed
import com.gyf.immersionbar.ImmersionBar
import com.ztsc.moudleuseguide.ui.BaseActivity

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目中的 Activity 基类
 */
abstract class MyActivity : BaseActivity()  {
    /** 标题栏对象  */
//    private var mTitleBar: TitleBar? = null

    /** 状态栏沉浸  */
    private var mImmersionBar: ImmersionBar? = null

    /** 加载对话框  */
//    private var mDialog: BaseDialog? = null

    /** 对话框数量  */
    private var mDialogTotal = 0

    /**
     * 当前加载对话框是否在显示中
     */
//    val isShowDialog: Boolean
//        get() = mDialog != null && mDialog.isShowing()

    /**
     * 显示加载对话框
     */
//    fun showDialog() {
//        mDialogTotal++
//        postDelayed({
//            if (mDialogTotal > 0 && !isFinishing()) {
//                if (mDialog == null) {
//                    mDialog = AlertDialog.Builder(this)
//                        .setCancelable(false)
//                        .create()
//                }
//                if (!mDialog.isShowing()) {
//                    mDialog.show()
//                }
//            }
//        }, 300)
//    }

    /**
     * 隐藏加载对话框
     */
//    fun hideDialog() {
//        if (mDialogTotal > 0) {
//            mDialogTotal--
//        }
//        if (mDialogTotal == 0 && mDialog != null && mDialog.isShowing() && !isFinishing()) {
//            mDialog.dismiss()
//        }
//    }

    override fun initLayout() {
        super.initLayout()
//        if (titleBar != null) {
//            titleBar.setOnTitleBarListener(this)
//        }

        // 初始化沉浸式状态栏
        if (isStatusBarEnabled) {
            statusBarConfig.init()

            // 设置标题栏沉浸
//            if (titleBar != null) {
//                ImmersionBar.setTitleBar(this, titleBar)
//            }
        }
    }

    /**
     * 是否使用沉浸式状态栏
     */
    protected val isStatusBarEnabled: Boolean
        protected get() = true

    /**
     * 状态栏字体深色模式
     */
    protected val isStatusBarDarkFont: Boolean
        protected get() = true

    /**
     * 初始化沉浸式状态栏
     */
    protected open fun createStatusBarConfig(): ImmersionBar {
        return ImmersionBar.with(this) // 默认状态栏字体颜色为黑色
            .statusBarDarkFont(isStatusBarDarkFont)
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    val statusBarConfig: ImmersionBar
        get() {
            if (mImmersionBar == null) {
                mImmersionBar = createStatusBarConfig()
            }
            return mImmersionBar!!
        }

    /**
     * 设置标题栏的标题
     */
    override fun setTitle(@StringRes id: Int) {
        setTitle(getString(id))
    }

    /**
     * 设置标题栏的标题
     */
    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
//        if (titleBar != null) {
//            titleBar.setTitle(title)
//        }
    }

    fun onLeftClick(v: View?) {
        onBackPressed()
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int, options: Bundle?) {
        super.startActivityForResult(intent, requestCode, options)
        overridePendingTransition(R.anim.right_in_activity, R.anim.right_out_activity)
    }



    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.left_in_activity, R.anim.left_out_activity)
    }



}