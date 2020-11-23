package com.ztsc.commonuimoudle.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.annotation.IntDef
import com.ztsc.commonuimoudle.R
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by benchengzhou on 2020/9/21  14:56 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 
 * 类    名： 对话框
 * 备    注： 
 */

open class ADialog : Dialog, View.OnClickListener {

    lateinit var mContext: Activity

    @DialogType
    private var mDialogStatus = LOADING //默认加载loading


    private val progressText: String? = null
    private var mLoadingLayout: FrameLayout? = null
    private var mQiutLayout: FrameLayout? = null
    private var mMessageLayout: FrameLayout? = null
    private var mIvLoadingIcon: ImageView? = null
    private var mTvLoadingTitle: TextView? = null
    private var mTvDoubleOptionMessage: TextView? = null
    private var mTvDoubleOptionQuitBtnLeft: TextView? = null
    private var mTvDoubleOptionQuitBtnRight: TextView? = null
    private var mTvSingleOptionMessage: TextView? = null
    private var mTvSingleOptionBtn: TextView? = null


    companion object {
        //先定义 常量
        const val DEFAULT = 0 //清空所有

        const val LOADING = 1 //加载loading样式

        const val DOUBLE_OPTION = 2 //两个选项

        const val SING_OPTION_SUCCESS = 3 //单个选项成功

        const val SING_OPTION_FAIL = 4 //单个选项失败
    }

    private var mLoadingMessage = "加载中,请稍后..."
    private var mDoubleOptionMessage = "你确认放弃本次编辑么？"
    private var mDoubleOptionLeftName = "确定"
    private var mDoubleOptionRightName = "取消"
    private var mSingleOptionSuccessName = "操作成功"
    private var mSingleOptionFailName = "操作成功"
    private var mSingleOptionSuccessMessage = "知道了"
    private var mSingleOptionFailMessage = "知道了"
    private var singleSuccessOptionCallback: onSingleOptionClickCallBack? =
        null
    private var singleFailOptionCallback: onSingleOptionClickCallBack? =
        null
    private var doubleOptionCallback: onDoubleOptionClickCallBack? =
        null
    private var mFailMessageLayout: FrameLayout? = null
    private var mTvSingleOptionFailMessage: TextView? = null
    private var mTvSingleOptionFailBtn: TextView? = null


    //网络参考链接 http://blog.csdn.net/young21234/article/details/49962659
    //用 <span></span>@IntDef "包住" 常量；
    // @Retention 定义策略
    // 声明构造器
    @IntDef(DEFAULT, LOADING, DOUBLE_OPTION, SING_OPTION_SUCCESS, SING_OPTION_FAIL)
    @Retention(RetentionPolicy.SOURCE)
    annotation class DialogType {}

    constructor(context: Activity) : this(context, R.style.dialog_adialog_stytle)


    constructor(context: Activity, themeResId: Int) : super(context, themeResId) {
        mContext = context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragment_loading)
        findView()
        initListener()
        setCancelable(false)
//        mTvLoadingTitle.setText(progressText == null ? "加载数据中，请稍后..." : progressText);
    }


    private fun findView() {
        mLoadingLayout =
            findViewById<View>(R.id.framelayout_loading) as FrameLayout
        mQiutLayout =
            findViewById<View>(R.id.framelayout_qiut) as FrameLayout
        mMessageLayout =
            findViewById<View>(R.id.framelayout_message) as FrameLayout
        mFailMessageLayout =
            findViewById<View>(R.id.framelayout_message_fail) as FrameLayout

        //加载样式布局
        mIvLoadingIcon =
            findViewById<View>(R.id.iv_loading_progress_bar) as ImageView
        mTvLoadingTitle =
            findViewById<View>(R.id.tv_loading_messge) as TextView


        //两个选项操作的样式布局
        mTvDoubleOptionMessage =
            findViewById<View>(R.id.tv_message_quit) as TextView
        mTvDoubleOptionQuitBtnLeft =
            findViewById<View>(R.id.tv_quit_btn_left) as TextView
        mTvDoubleOptionQuitBtnRight =
            findViewById<View>(R.id.tv_quit_btn_right) as TextView

        //单个选项提示成功的布局
        mTvSingleOptionMessage =
            findViewById<View>(R.id.tv_single_option_message) as TextView
        mTvSingleOptionBtn =
            findViewById<View>(R.id.tv_single_option_btn) as TextView

        //单个选项提示失败的布局
        mTvSingleOptionFailMessage =
            findViewById<View>(R.id.tv_single_option_fail_message) as TextView
        mTvSingleOptionFailBtn =
            findViewById<View>(R.id.tv_single_option_fail_btn) as TextView
    }


    /**
     * 状态更新为加载中。。。
     *
     * @return
     */
    fun syncLoadingDialog(): ADialog? {
        mDialogStatus = LOADING
        updataDialogStatus()
        return this
    }

    /**
     * 状态更新为加载中。。。
     *
     * @param loadingMessage
     * @return
     */
    fun syncLoadingDialog(loadingMessage: String): ADialog? {
        mDialogStatus = LOADING
        mLoadingMessage = loadingMessage
        updataDialogStatus()
        return this
    }


    /**
     * 状态更新为。。。
     *
     * @return
     */
    fun syncDoubleOptionDialog(): ADialog? {
        mDialogStatus = DOUBLE_OPTION
        updataDialogStatus()
        return this
    }


    /**
     * 状态更新为。。。
     *
     * @param Message      信息
     * @param btnLeftName  左侧选项名称
     * @param btnRightName 右侧选项名称
     * @return
     */
    fun syncDoubleOptionDialog(
        Message: String,
        btnLeftName: String,
        btnRightName: String
    ): ADialog? {
        mDialogStatus = DOUBLE_OPTION
        mDoubleOptionMessage = Message
        mDoubleOptionLeftName = btnLeftName
        mDoubleOptionRightName = btnRightName
        updataDialogStatus()
        return this
    }


    /**
     * 状态更新为单个选项。。。
     *
     * @return
     */
    fun syncSingleOptionSuccessDialog(): ADialog? {
        mDialogStatus = SING_OPTION_SUCCESS
        updataDialogStatus()
        return this
    }

    /**
     * 状态更新为单个选项。。。
     *
     * @param Message
     * @param btnName
     * @return
     */
    fun syncSingleOptionSuccessDialog(
        Message: String,
        btnName: String
    ): ADialog? {
        mDialogStatus = SING_OPTION_SUCCESS
        mSingleOptionSuccessMessage = Message
        mSingleOptionSuccessName = btnName
        updataDialogStatus()
        return this
    }

    /**
     * 状态更新为单个选项。。。
     *
     * @param Message
     * @param btnName
     * @return
     */
    fun syncSingleOptionFailDialog(Message: String, btnName: String): ADialog? {
        mDialogStatus = SING_OPTION_FAIL
        mSingleOptionFailMessage = Message
        mSingleOptionFailName = btnName
        updataDialogStatus()
        return this
    }


    /**
     * 同步dialog为指定的状态
     *
     * @param dialogStatus One of [.DEFAULT], [.LOADING], [.DOUBLE_OPTION],
     * [.SING_OPTION_SUCCESS]
     * @return
     */
    fun syncStatusDialog(@DialogType dialogStatus: Int): ADialog? {
        mDialogStatus = dialogStatus
        updataDialogStatus()
        return this
    }


    /**
     * 同步dialog为指定的状态
     *
     * @param dialogStatus One of [.DEFAULT], [.LOADING], [.DOUBLE_OPTION],
     * [.SING_OPTION_SUCCESS]
     * @param message      提示信息
     * @return
     */
    fun syncStatusDialog(
        @DialogType dialogStatus: Int,
        message: String
    ): ADialog? {
        mDialogStatus = dialogStatus
        when (mDialogStatus) {
            LOADING -> mLoadingMessage = message
            DOUBLE_OPTION -> mDoubleOptionMessage = message
            SING_OPTION_SUCCESS -> mSingleOptionSuccessMessage = message
            SING_OPTION_FAIL -> mSingleOptionFailMessage = message
        }
        updataDialogStatus()
        return this
    }


    /**
     * 更新
     */
    private fun updataDialogStatus() {
        mLoadingLayout?.setVisibility(View.GONE)
        mQiutLayout?.setVisibility(View.GONE)
        mMessageLayout?.setVisibility(View.GONE)
        mFailMessageLayout!!.visibility = View.GONE
        when (mDialogStatus) {
            LOADING -> {
                mLoadingLayout?.setVisibility(View.VISIBLE)
                mTvLoadingTitle?.setText(mLoadingMessage)
                mIvLoadingIcon?.startAnimation(
                    AnimationUtils.loadAnimation(
                        context, R.anim.animate_adialog_cutsom_1
                    )
                )
            }
            DOUBLE_OPTION -> {
                mQiutLayout?.setVisibility(View.VISIBLE)
                mTvDoubleOptionMessage?.setText(mDoubleOptionMessage)
                mTvDoubleOptionQuitBtnLeft?.setText(mDoubleOptionLeftName)
                mTvDoubleOptionQuitBtnRight?.setText(mDoubleOptionRightName)
            }
            SING_OPTION_SUCCESS -> {
                mMessageLayout?.setVisibility(View.VISIBLE)
                mTvSingleOptionMessage?.setText(mSingleOptionSuccessMessage)
                mTvSingleOptionBtn?.setText(mSingleOptionSuccessName)
            }
            SING_OPTION_FAIL -> {
                mFailMessageLayout?.visibility = View.VISIBLE
                mTvSingleOptionFailMessage!!.text = mSingleOptionFailMessage
                mTvSingleOptionFailBtn!!.text = mSingleOptionFailName
            }
        }
    }


    private fun initListener() {
        mTvSingleOptionBtn?.setOnClickListener(this)
        mTvDoubleOptionQuitBtnLeft?.setOnClickListener(this)
        mTvDoubleOptionQuitBtnRight?.setOnClickListener(this)
        mTvSingleOptionFailBtn!!.setOnClickListener(this)
    }


    /**
     * 显示当前的dialog
     *
     * @return
     */
    fun showDialog(): ADialog{
        show()
        return this
    }


    /**
     * 隐藏当前的dialog
     */
    @Throws(Exception::class)
    fun dissMissDialog() {
        if (isShowing) {
            dismiss()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_single_option_btn -> {
                singleSuccessOptionCallback?.apply {
                    if (onSingleCallback()) {
                        dismiss()
                    }
                }
            }
            R.id.tv_quit_btn_left -> {
                doubleOptionCallback?.apply {
                    if (onDoubleLeftCallback()) {
                        dismiss()
                    }
                }
            }
            R.id.tv_quit_btn_right -> {
                doubleOptionCallback?.apply {
                    if (onDoubleRightCallback()) {
                        dismiss()
                    }
                }
            }
            R.id.tv_single_option_fail_btn -> {
                singleFailOptionCallback?.apply {
                    if (onSingleCallback()) {
                        dismiss()
                    }
                }
            }
        }
    }


    /**
     * 获取当前dialog的状态
     *
     * @return
     */
    fun getDialogType(): Int {
        return mDialogStatus
    }


    interface onSingleOptionClickCallBack {
        //返回是否隐藏当前弹框
        fun onSingleCallback(): Boolean
    }


    /**
     * 单项选择对话框设置
     *
     * @param singleOptionCallback
     * @return
     */
    fun SetOnSingleSuccessOptionClickCallBack(singleOptionCallback: onSingleOptionClickCallBack?): ADialog{
        singleSuccessOptionCallback = singleOptionCallback
        return this
    }

    /**
     * 是样式是的单项选择对话框设置
     *
     * @param singleOptionCallback
     * @return
     */
    fun SetOnSingleFailOptionClickCallBack(singleOptionCallback: onSingleOptionClickCallBack?): ADialog{
        singleFailOptionCallback = singleOptionCallback
        return this
    }


    interface onDoubleOptionClickCallBack {
        //返回是否隐藏当前弹框
        fun onDoubleLeftCallback(): Boolean

        //返回是否隐藏当前弹框
        fun onDoubleRightCallback(): Boolean
    }


    /**
     * 单项选择对话框设置
     *
     * @param doubleOptionCallback
     * @return
     */
    fun SetOnDoubuleOptionClickCallBack(doubleOptionCallback: onDoubleOptionClickCallBack?): ADialog {
        this.doubleOptionCallback = doubleOptionCallback
        return this
    }

}