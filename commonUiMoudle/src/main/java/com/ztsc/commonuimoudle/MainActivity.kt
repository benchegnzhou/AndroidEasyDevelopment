package com.ztsc.commonuimoudle

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ztsc.commonuimoudle.dialog.ADialog

class MainActivity : AppCompatActivity() {

    lateinit var mStatusDialog: ADialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mStatusDialog = ADialog(this)
//        createLoadingDialog("加载中...")
        quitEditUI()
    }


    /**
     * 获取一个和界面状态相关的Dialog
     */
    fun getStatusDialog(): ADialog {
        try {
            if (mStatusDialog == null) {
                mStatusDialog = ADialog(this)
            }
        } catch (e: java.lang.Exception) {
            mStatusDialog = ADialog(this)
        }

        return mStatusDialog
    }


    /**
     * 加载等待和上传等待界面统一样式
     */
    protected fun createLoadingDialog() {
        getStatusDialog().showDialog().syncLoadingDialog("加载中...")
    }

    /**
     * 加载等待和上传等待界面统一样式
     *
     * @param loadingMessage
     */
    protected fun createLoadingDialog(loadingMessage: String) {
        getStatusDialog().showDialog().syncLoadingDialog(loadingMessage)
    }


    /**
     * 状态dialog隐藏
     */
    protected fun dissmissLoadingDialog() {
        try {
            getStatusDialog().dissMissDialog()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * 编辑界面退出询问
     */
    protected fun quitEditUI() {
        getStatusDialog().showDialog()
            .SetOnDoubuleOptionClickCallBack(object : ADialog.onDoubleOptionClickCallBack {
                override fun onDoubleLeftCallback(): Boolean {
                    finish()
                    return true
                }

                override fun onDoubleRightCallback(): Boolean {
                    return true
                }
            }).syncDoubleOptionDialog("你要放弃本次编辑么？", "去意已决", "取消")
    }


    /**
     * 提交失败的风格和样式
     * 同时可用于确认信息
     *
     * @param messge
     */
    protected fun showSingleBtnSuccessfulDialog(messge: String) {
        getStatusDialog().showDialog()
            .SetOnSingleSuccessOptionClickCallBack(object : ADialog.onSingleOptionClickCallBack {
                override fun onSingleCallback(): Boolean {
                    return true
                }
            })
            ?.syncSingleOptionSuccessDialog(messge, "确定")
    }


    /**
     * 提交失败的风格和样式
     * 同时可用于确认信息
     *
     * @param messge
     * @param finishEnable 是否退出界面
     */
    protected open fun showSingleBtnSuccessfulDialog(messge: String, finishEnable: Boolean) {
        getStatusDialog().showDialog()
            .SetOnSingleSuccessOptionClickCallBack(object : ADialog.onSingleOptionClickCallBack {
                override fun onSingleCallback(): Boolean {
                    if (finishEnable) {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                    return true
                }

            })?.syncSingleOptionSuccessDialog(messge, "确定")
    }

    /**
     * 数据验证失败样式弹框
     * 同时可用于确认信息
     *
     * @param messge
     */
    protected fun showSingleBtnFailDialog(messge: String?) {
        showSingleBtnFailDialog(messge, "确定", false)
    }

    /**
     * 数据验证失败样式弹框
     * 同时可用于确认信息
     *
     * @param messge
     * @param finishEnable 是否退出界面
     */
    protected fun showSingleBtnFailDialog(messge: String?, finishEnable: Boolean) {
        showSingleBtnFailDialog(messge, "确定", finishEnable)
    }

    /**
     * 数据验证失败样式弹框
     * 同时可用于确认信息
     *
     * @param messge
     * @param btnName      btn名称
     * @param finishEnable 是否退出界面
     */
    protected fun showSingleBtnFailDialog(
        messge: String?,
        btnName: String?,
        finishEnable: Boolean
    ) {
        getStatusDialog().showDialog()
            .SetOnSingleFailOptionClickCallBack(object : ADialog.onSingleOptionClickCallBack {
                override fun onSingleCallback(): Boolean {
                    if (finishEnable) {
                        finish()
                    }
                    return true
                }

            })?.syncSingleOptionFailDialog(messge ?: "", "确定")
    }

}
