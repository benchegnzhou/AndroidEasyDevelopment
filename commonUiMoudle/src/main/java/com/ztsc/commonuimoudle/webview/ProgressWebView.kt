package com.ztsc.commonuimoudle.webview


import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.ztsc.commonuimoudle.R


import java.io.File


/**
 * Created by benchengzhou on 2020/10/20  10:47 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名：
 * 备    注： 参考链接   http://www.jianshu.com/p/980ae278568e
 */

class ProgressWebView(
    context: Context?,
    attrs: AttributeSet?,
    defStyle: Int
) : WebView(context, attrs, defStyle) {


    private var progressbar: ProgressBar? = null
    private var onWebCallBack //回调
            : OnWebCallBack? = null
    var mUploadCallbackAboveL: ValueCallback<*>? = null
    var mUploadMessage: ValueCallback<*>? = null
    var imageUri: Uri? = null
    val FILECHOOSER_RESULTCODE = 3000

    constructor(context: Context?) : this(context, null)

    constructor(
        context: Context?,
        attrs: AttributeSet?
    ) : this(context, attrs, android.R.attr.webTextViewStyle)

    init {
//        this.context = context
        init()
        webViewClient = MyWebViewClient()
        webChromeClient = WebChromeClient()
    }


    /**
     * 设置ProgressBar
     */
    fun init() {
        progressbar =
            ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
        progressbar!!.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            20,
            0,
            0
        )
        addView(progressbar)
    }

    inner class MWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(
            view: WebView,
            newProgress: Int
        ) {
            if (newProgress == 100) {
                progressbar?.setVisibility(View.GONE)
            } else {
                progressbar?.setVisibility(View.VISIBLE)
                progressbar?.setProgress(newProgress)
            }
            super.onProgressChanged(view, newProgress)
        }

        override fun onReceivedTitle(
            view: WebView,
            title: String
        ) {
            super.onReceivedTitle(view, title)
            if (onWebCallBack != null) {  //获取标题
                onWebCallBack?.getTitle(title)
            }
        }

        override fun onShowFileChooser(
            webView: WebView?,
            filePathCallback: ValueCallback<Array<Uri>>?,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            mUploadCallbackAboveL = filePathCallback
            take()
            return true
        }


        //<3.0
        fun openFileChooser(uploadMsg: ValueCallback<*>) {
            mUploadMessage = uploadMsg
            take()
        }

        //>3.0+
        fun openFileChooser(uploadMsg: ValueCallback<*>, acceptType: String?) {
            mUploadMessage = uploadMsg
            take()
        }

        //>4.1.1
        fun openFileChooser(
            uploadMsg: ValueCallback<*>,
            acceptType: String?,
            capture: String?
        ) {
            mUploadMessage = uploadMsg
            take()
        }
    }


    private fun take() {
        val imageStorageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "ZTSC_PROP_APP"
        )
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs()
        }
        val file = File(
            imageStorageDir.toString() + File.separator + "IMG_" + System.currentTimeMillis()
                .toString() + ".jpg"
        )
        imageUri = Uri.fromFile(file)
        val cameraIntents: java.util.ArrayList<Intent> = java.util.ArrayList()
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val packageManager = context!!.packageManager
        val listCam =
            packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            val packageName = res.activityInfo.packageName
            val i = Intent(captureIntent)
            i.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            i.setPackage(packageName)
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            cameraIntents.add(i)
        }
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        i.type = "image/*"
        val chooserIntent = Intent.createChooser(i, "Image Chooser")
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            cameraIntents.toArray(arrayOf<Parcelable>())
        )
        (context as Activity?)!!.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE)
    }


    /**
     * 不重写的话，会跳到手机浏览器中
     *
     * @author admin
     */
    inner class MyWebViewClient : WebViewClient() {
        override fun onReceivedError(
            view: WebView, errorCode: Int,
            description: String, failingUrl: String
        ) { // Handle the
            goBack()
        }

        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(
            view: WebView,
            url: String
        ) {
            super.onPageFinished(view, url)
        }

        override fun onPageStarted(
            view: WebView,
            url: String,
            favicon: Bitmap
        ) {
            if (onWebCallBack != null) { //获得WebView的地址
                onWebCallBack?.getUrl(url)
            }
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val lp =
            progressbar!!.layoutParams as LayoutParams
        lp.x = l
        lp.y = t
        progressbar!!.layoutParams = lp
        super.onScrollChanged(l, t, oldl, oldt)
    }

    /**
     * 设置WebView的回掉器
     *
     * @param onWebCallBack
     */
    fun setOnWebCallBack(onWebCallBack: OnWebCallBack?) {
        this.onWebCallBack = onWebCallBack
    }

    interface OnWebCallBack {
        /**
         * 获取标题
         *
         * @param title
         */
        fun getTitle(title: String?)

        /**
         * 获得WebView的地址
         *
         * @param url
         */
        fun getUrl(url: String?)
    }


}