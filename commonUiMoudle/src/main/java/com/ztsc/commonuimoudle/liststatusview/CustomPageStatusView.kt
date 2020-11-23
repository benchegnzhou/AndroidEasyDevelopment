package com.ztsc.commonuimoudle.liststatusview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.InflateException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import com.ztsc.commonuimoudle.R
import com.ztsc.commonuimoudle.helper.DataParserHelper
import com.ztsc.commonutils.logcat.LoggerUtil
import kotlinx.android.synthetic.main.layout_empty_page_view.view.*
import kotlinx.android.synthetic.main.layout_item_data_error_pageview.view.*
import kotlinx.android.synthetic.main.layout_item_emptydata_pageview.view.*
import kotlinx.android.synthetic.main.layout_item_loading_pageview.view.*
import zbc.com.cn.utils.yes

class CustomPageStatusView : ViewGroup, PageStatusControl {



    private var emptyViewTitle: String = "暂时木有数据"
    private var loadingText: String = "加载中，请稍后..."
    private var networkErrorTitle: String = "数据加载错误"
    private var networkErrorContent: String = "下拉刷新试试"

    private var loadRetryListener: PageStatusControl.LoadRetryListener? = null
    private var pageStatusListener: PageStatusControl.PageStatusLisener? = null


    private var mPageStatus: PageStatus = PageStatus.PAGE_STATUS_NORMAL


    private var dataParser: DataParserHelper = DataParserHelper(this)

    /**
     * 默认使能本地加载界面
     */
    var isLoadingViewEnable = true
    private var ivLoading: ImageView? = null
    private var tvLoadingHint: TextView? = null
    private var animationDrawable: AnimationDrawable? = null

    constructor(context: Context) : super(context) {
        initView(context)
        initListener()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        try {
            initView(context)
            initListener()
        } catch (e: InflateException) {
            e.printStackTrace()
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        try {
            initView(context)
            initListener()
        } catch (e: InflateException) {
            e.printStackTrace()
        }
    }


    override protected fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        try {
            val childCount: Int = getChildCount()
            for (i in 0 until childCount) {
                val children: View = getChildAt(i)
                measureChild(children, widthMeasureSpec, heightMeasureSpec)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun initView(context: Context) {
        try {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.layout_empty_page_view, this)

            rl_layout_emptydata_view.visibility = View.GONE
            rl_layout_neterror_view.visibility = View.GONE
            rl_layout_dataerror_view.visibility = View.GONE
            rl_layout_loading_view.visibility = View.GONE
            status_view_root.visibility = View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initListener() {
        rl_layout_neterror_view.setOnClickListener {
            loadRetryListener?.onErrorRetryCilck(mPageStatus)
        }
    }


    /**
     * 设置空布局文案
     */
    fun setEmptyViewTitle(@NonNull emptyViewTitle: String): CustomPageStatusView {

        (emptyViewTitle != null).yes {
            this.emptyViewTitle = emptyViewTitle
        }
        return this
    }


    /**
     * 设置加载中文案
     */
    fun setLoadingText(@NonNull loadingText: String): CustomPageStatusView {

        (loadingText != null).yes {
            this.loadingText = loadingText
        }
        return this
    }

    /**
     * 设置网络错误文案标题
     */
    fun setNetworkErrorTitle(@NonNull networkErrorTitle: String): CustomPageStatusView {

        (networkErrorTitle != null).yes {
            this.networkErrorTitle = networkErrorTitle
        }
        return this
    }

    /**
     * 设置网络错误文案内容
     */
    fun setNetworkErrorContent(@NonNull networkErrorContent: String): CustomPageStatusView {
        (networkErrorContent != null).yes {
            this.networkErrorContent = networkErrorContent
        }
        return this
    }


    override protected fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        try {
            LoggerUtil.e("changed_____________$changed")
            val childCount: Int = getChildCount()
            var preHeight = 0
            for (i in 0 until childCount) {
                val children: View = getChildAt(i)
                val cHeight = children.measuredHeight
                if (children.visibility != View.GONE) {
                    children.layout(l, preHeight, r, cHeight.let { preHeight += it; preHeight })
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun initPageStatus(): CustomPageStatusView {
        return this
    }

    override fun initPageStatus(status: PageStatus): CustomPageStatusView {
        return this
    }

    override fun syncPageStatus(status: PageStatus): CustomPageStatusView {
        sysnCallBack(status)
        mPageStatus = status
        return this
    }


    /**
     * 界面状态同步
     */
    private fun sysnCallBack(status: PageStatus) {

        //上次不为加载中，且本次变更为加载中的，执行加载中的回调
        if (pageStatusListener != null && mPageStatus != status && status == PageStatus.PAGE_STATUS_LOADING) {
            pageStatusListener?.onLoading()
        } else if (pageStatusListener != null && mPageStatus == PageStatus.PAGE_STATUS_LOADING && status != PageStatus.PAGE_STATUS_LOADING) {
            pageStatusListener?.onFinish()
        }
    }


    override fun getPageStatus(): PageStatus {
        return mPageStatus
    }


    override fun setLoadingViewEnable(loadingViewEnable: Boolean): CustomPageStatusView {
        isLoadingViewEnable = loadingViewEnable
        return this
    }

    override fun getLoadingViewEnable(): Boolean {
        return isLoadingViewEnable
    }

    override fun getDataParer(): DataParserHelper {
        if (dataParser == null) {
            dataParser = DataParserHelper(this)
        }
        return dataParser
    }

    override fun setOnErrorLoadRetryListener(loadRetryListener: PageStatusControl.LoadRetryListener?): PageStatusControl? {
        this.loadRetryListener = loadRetryListener
        return this
    }

    override fun setOnPageStatusListener(pageStatusListener: PageStatusControl.PageStatusLisener?): PageStatusControl? {
        this.pageStatusListener = pageStatusListener
        return this
    }

    /**
     * 根据状态显示对应的界面
     */
    override fun showStatusView() {
        showStatusView(mPageStatus)
    }

    override fun showStatusView(status: PageStatus) {

        try {
            //同步界面状态
            syncPageStatus(status)
            when (status) {
                PageStatus.PAGE_STATUS_NORMAL -> if (dissmissLoading()) {
                    mHandler.sendEmptyMessageDelayed(PageStatus.PAGE_STATUS_NORMAL.code, 500)
                } else {
                    mHandler.sendEmptyMessage(PageStatus.PAGE_STATUS_NORMAL.code)
                }
                PageStatus.PAGE_STATUS_LOADING -> showLoadingView()
                PageStatus.PAGE_STATUS_NET_WORK_ERROR -> if (dissmissLoading()) {
                    mHandler.sendEmptyMessageDelayed(
                        PageStatus.PAGE_STATUS_NET_WORK_ERROR.code,
                        500
                    )
                } else {
                    mHandler.sendEmptyMessage(PageStatus.PAGE_STATUS_NET_WORK_ERROR.code)
                }
                PageStatus.PAGE_STATUS_NO_DATA -> if (dissmissLoading()) {
                    mHandler.sendEmptyMessageDelayed(PageStatus.PAGE_STATUS_NO_DATA.code, 500)
                } else {
                    mHandler.sendEmptyMessage(PageStatus.PAGE_STATUS_NO_DATA.code)
                }
                PageStatus.PAGE_STATUS_LOAD_FAIL -> if (dissmissLoading()) {
                    mHandler.sendEmptyMessageDelayed(PageStatus.PAGE_STATUS_LOAD_FAIL.code, 500)
                } else {
                    mHandler.sendEmptyMessage(PageStatus.PAGE_STATUS_LOAD_FAIL.code)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * 隐藏lodding状态view
     */
    private fun dissmissLoading(): Boolean {
        return if (rl_layout_loading_view.visibility == View.VISIBLE) {
            try {
                //获取控件当前透明度并开始动画
                val alpha = rl_layout_loading_view.alpha
                //                LogUtil.e("alpha:"+alpha);
                val animation = setAlphaAnimation(alpha, 0f, 500)
                rl_layout_loading_view!!.animation = animation
                rl_layout_loading_view!!.startAnimation(animation)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            true
        } else {
            false
        }
    }


    /**
     * 渐变的一种效果
     *
     * @param fromAlpha
     * @param toAlpha
     */
    fun setAlphaAnimation(
        fromAlpha: Float,
        toAlpha: Float,
        timeLong: Long
    ): Animation {
        val alphaAnimation: Animation =
            AlphaAnimation(fromAlpha, toAlpha)
        alphaAnimation.duration = timeLong
        return alphaAnimation
    }

    /**
     * 显示加载中动画
     */
    private fun showLoadingView() {
        try {
            val animation = setAlphaAnimation(0f, 1f, 500)
            iv_loading_icon.animation = animation
            iv_loading_icon.startAnimation(animation)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        rl_layout_emptydata_view.visibility = View.GONE
        rl_layout_neterror_view.visibility = View.GONE
        rl_layout_dataerror_view.visibility = View.GONE
        rl_layout_loading_view.visibility = View.VISIBLE
        status_view_root.visibility = View.VISIBLE

        rl_layout_loading_view.visibility = if (isLoadingViewEnable) View.VISIBLE else View.GONE
        tv_loading_msg.text = loadingText
        isLoadingViewEnable.yes {
            if (animationDrawable != null && !animationDrawable!!.isRunning) {
                animationDrawable!!.start()
            }
        }

    }


    /**
     * 显示加载失败界面
     */
    private fun showFailView() {
        try {
            val animation2 = setAlphaAnimation(0f, 1f, 500)
            rl_layout_dataerror_view.animation = animation2
            rl_layout_dataerror_view.startAnimation(animation2)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        rl_layout_emptydata_view.visibility = View.GONE
        rl_layout_neterror_view.visibility = View.GONE
        rl_layout_dataerror_view.visibility = View.VISIBLE
        rl_layout_loading_view.visibility = View.GONE
        status_view_root.visibility = View.VISIBLE
        tv_net_error_title.text = networkErrorTitle
        tv_net_error_content.text = networkErrorContent

    }


    /**
     * 显示空数据界面
     */
    private fun showNoDataView() {
        try {
            val animation2 = setAlphaAnimation(0f, 1f, 500)
            rl_layout_emptydata_view.animation = animation2
            rl_layout_emptydata_view.startAnimation(animation2)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        rl_layout_emptydata_view.visibility = View.VISIBLE
        rl_layout_neterror_view.visibility = View.GONE
        rl_layout_dataerror_view.visibility = View.GONE
        rl_layout_loading_view.visibility = View.GONE
        status_view_root.visibility = View.VISIBLE
        tv_no_data_msg.text = emptyViewTitle

    }


    /**
     * 显示网络链接失败按钮
     */
    private fun showNetWorkErrorView() {
        try {
            val animation2 = setAlphaAnimation(0f, 1f, 500)
            rl_layout_neterror_view.animation = animation2
            rl_layout_neterror_view.startAnimation(animation2)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        rl_layout_emptydata_view.visibility = View.GONE
        rl_layout_neterror_view.visibility = View.VISIBLE
        rl_layout_dataerror_view.visibility = View.GONE
        rl_layout_loading_view.visibility = View.GONE
        status_view_root.visibility = View.VISIBLE
        tv_net_error_title.text = networkErrorTitle
        tv_net_error_content.text = networkErrorContent
    }


    @SuppressLint("HandlerLeak")
    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                PageStatus.PAGE_STATUS_NET_WORK_ERROR.code -> showNetWorkErrorView()
                PageStatus.PAGE_STATUS_NORMAL.code -> showNormalView()
                PageStatus.PAGE_STATUS_NO_DATA.code -> showNoDataView()
                PageStatus.PAGE_STATUS_LOAD_FAIL.code -> showFailView()
            }
        }
    }


    /**
     * 正常加载数据，界面隐藏
     */
    private fun showNormalView() {
        status_view_root.visibility = View.GONE
    }


}