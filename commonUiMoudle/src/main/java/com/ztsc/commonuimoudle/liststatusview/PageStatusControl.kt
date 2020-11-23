package com.ztsc.commonuimoudle.liststatusview

import com.ztsc.commonuimoudle.helper.DataParserHelper


enum class PageStatus(var code: Int) {

    /**
     * 正常加载数据
     */
    PAGE_STATUS_NORMAL(100),


    /**
     * 界面加载中
     */
    PAGE_STATUS_LOADING(102),


    /**
     * 没有数据
     */
    PAGE_STATUS_NO_DATA(103),

    /**
     * 网络异常，无法连接网络
     */
    PAGE_STATUS_NET_WORK_ERROR(109),

    /**
     * 数据加载失败
     */
    PAGE_STATUS_LOAD_FAIL(114)

}


interface PageStatusControl {

    /**
     * 数据页初始化
     *
     * @return
     */
    fun initPageStatus(): PageStatusControl

    /**
     * 数据页初始化
     *
     * @param status 初始化展示的界面状态，默认为正常（不显示状态页）
     * @return
     */
    fun initPageStatus(status: PageStatus): PageStatusControl


    /**
     * @param #status One of [.PAGE_STATUS_LOAD_FAIL], [.PAGE_STATUS_LOADING], [.PAGE_STATUS_NET_WORK_ERROR], [.PAGE_STATUS_NO_DATA],
     * [.PAGE_STATUS_NET_WORK_ERROR] .
     * @return
     */
    fun syncPageStatus(status: PageStatus): PageStatusControl


    /**
     * 获取当前界面展示的状态
     *
     * @return
     */
    fun getPageStatus(): PageStatus

    /**
     * 显示设定的页面状态
     */
    fun showStatusView()

    /**
     * 同步页面状态，并显示到页面上
     */
    fun showStatusView(status: PageStatus)


    /**
     * 禁用原生加载更多布局
     *
     * @param isLoadingViewEnable
     */
    fun setLoadingViewEnable(isLoadingViewEnable: Boolean): PageStatusControl


    /**
     * 获取加载更多页面状态
     *
     * @return
     */
    fun getLoadingViewEnable(): Boolean


    /**
     * 获取数据解析器
     * @return
     */
    fun getDataParer(): DataParserHelper


    /**
     * 加载错误点击揭界面重试
     */
    fun setOnErrorLoadRetryListener(loadRetryListener: LoadRetryListener?): PageStatusControl?

    /**
     * 状态页状态回调
     * 用于同步加载动画
     */
    fun setOnPageStatusListener(pageStatusListener: PageStatusLisener?): PageStatusControl?


    /**
     * 注册错误界面点击事件
     */
    interface LoadRetryListener {
        fun onErrorRetryCilck( statusType: PageStatus)
    }


    /**
     * 注册状态页面回调，用于界面状态展示
     */
    interface PageStatusLisener {
        fun onLoading()
        fun onFinish()
    }

}