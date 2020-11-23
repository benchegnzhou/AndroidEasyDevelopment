package com.ztsc.moudleuseguide.ui.activity.webview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.gyf.immersionbar.ImmersionBar
import com.ztsc.house.BaseActivity
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.ui.base.addClick
import kotlinx.android.synthetic.main.activity_common_web_view.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*


/**
 * Created by benchengzhou on 2020/10/20  11:10 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 通用的协议展示页面
 * 类    名：
 * 备    注：
 */

class CommonWebViewActivity : BaseActivity() {
    private var url = ""

    override fun getContentView(): Int {
        return R.layout.activity_common_web_view
    }

    override fun initListener() {
        addClick(rl_back)
        ImmersionBar.with(this).titleBar(common_toolbar_root)
            .addTag("DemoDialogActivity")
            .init()
    }

    override fun initData() {
        val mIntent = intent
        url = mIntent.getStringExtra("url")

        tv_title.setText(mIntent.getStringExtra("title"))
        //1. 启用mixed content   android 5.0以上默认不支持Mixed Content
        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }*/

        webViewSetting()

        /**
         * 2. 设置WebView接受所有网站的证书
         * 注：在重写WebViewClient的onReceivedSslError方法时，注意一定要去除onReceivedSslError方法的super.onReceivedSslError(view, handler, error);，否则设置无效。
         */
        webview.setWebViewClient(object : WebViewClient() {
            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler, error: SslError
            ) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed() // 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {
                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        webview.loadUrl(url)
                    } else {
                        var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    }
                    return true
                } catch (e: Exception) {
                    return false
                }

            }
            /* @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().);
                return true;
            }*/
        })
        if (TextUtils.isEmpty(url)) {
            return
        }

        try {
            if (url.startsWith("http:") || url.startsWith("https:")) {
                webview.loadUrl(url)
            } else {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }

        } catch (e: Exception) {

        }
        //设置自适应屏幕，两者合用
        //        webview.setUseWideViewPort(true); //将图片调整到适合webview的大小
        //        webview.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //
        //缩放操作
        //        webview.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        //        webview.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        //        webview.setDisplayZoomControls(false); //隐藏原生的缩放控件
    }


    companion object {
        /**
         * 启动
         *
         * @param context
         * @param title
         * @param url
         */
        @JvmStatic
        fun loadCommonWebview(
            context: Context?,
            title: String?,
            url: String?
        ): Boolean {
            if (context == null) {
                return false
            }
            val intent = Intent(context, CommonWebViewActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("url", url)
            context.startActivity(intent)
            return true
        }

    }


    /**
     * 自定义WebView的配置
     */
    private fun webViewSetting() {
        val settings: WebSettings = webview.getSettings()
        settings.javaScriptEnabled = true
        //webview缩放 100%
        webview.getSettings().setTextZoom(100)
        //支持插件
        //        settings.setPluginsEnabled(true);

        //设置自适应屏幕，两者合用
        settings.useWideViewPort = true //将图片调整到适合webview的大小
        settings.loadWithOverviewMode = true // 缩放至屏幕的大小

        //缩放操作
        settings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        settings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.displayZoomControls = false //隐藏原生的缩放控件

        //其他细节操作
        settings.cacheMode = WebSettings.LOAD_NO_CACHE //关闭webview中缓存
        settings.allowFileAccess = true //设置可以访问文件
        settings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        settings.loadsImagesAutomatically = true //支持自动加载图片
        settings.defaultTextEncodingName = "utf-8" //设置编码格式
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_back -> finish()
            else -> {
            }
        }
    }


    override fun onDestroy() {
        if (webview != null) {
            webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            webview.clearHistory()
            (webview.getParent() as ViewGroup).removeView(webview)
            webview.destroy()

        }
        super.onDestroy()
    }


}