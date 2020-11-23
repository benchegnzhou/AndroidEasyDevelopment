package com.ztsc.commonuimoudle.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.ztsc.commonuimoudle.R

class SubmitUploadingDialog : Dialog {
    var mContext: Context? = null
    var progressText: String = ""

    constructor(context: Context) : super(context, R.style.dialog_uploading) {
        mContext = context
    }

    constructor(context: Context, stytle: Int) : super(context, stytle) {
        mContext = context
    }

    constructor(context: Context, progressText: String) : super(context, R.style.dialog_uploading) {
        mContext = context
        this.progressText=progressText
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_loading_progressbar)
        val title =
            findViewById<View>(R.id.custom_imageview_progress_title) as TextView
        title.text = if (progressText == null) "加载数据中，请稍后..." else progressText
    }

    /**
     * @see Dialog.show
     */
    override fun show() {
        try {
            if (!isShowing) {
                super.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val im =
            findViewById<View>(R.id.custom_imageview_progress_bar) as ImageView
        im.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.animate_uploading_dialog
            )
        )
    }

}