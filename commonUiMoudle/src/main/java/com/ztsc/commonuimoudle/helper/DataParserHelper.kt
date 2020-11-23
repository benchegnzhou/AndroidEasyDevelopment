package com.ztsc.commonuimoudle.helper

import com.chad.library.adapter.base.BaseQuickAdapter
import com.ztsc.commonuimoudle.liststatusview.CustomPageStatusView
import com.ztsc.commonuimoudle.liststatusview.PageStatus


enum class ErrorType {
    REQUEST_CODE_ERROR, RESULT_CODE_ERROR
}


class DataParserHelper {
    private var statusView: CustomPageStatusView? = null


    //网络请求成功
    var RESPONSE_OK = 200

    var RESULT_OK = 0


    private fun DataParserHelper() {}

    constructor(statusView: CustomPageStatusView?) {
        this.statusView = statusView
    }


    /**
     * 根据列表结果，解析对应界面
     *
     * @param responseCode
     * @param resultCode
     * @param adapter
     */
    fun checkSuccessByData(
        responseCode: Int,
        resultCode: Int,
        adapter: BaseQuickAdapter<*, *>?,
        callBack: CheckResultCallBack?
    ): DataParserHelper? {
        if (responseCode != 200) {
            callBack?.onError(ErrorType.REQUEST_CODE_ERROR, responseCode)
            return this
        } else if (resultCode != 0) {
            callBack?.onError(ErrorType.REQUEST_CODE_ERROR, resultCode)
            return this
        }

        //请求成功
        if (callBack != null) {
            val isEmpty =
                adapter == null || adapter.data == null || adapter.data.size == 0
            callBack.onSuccessCallBack(isEmpty)
        }
        return this
    }


    /**
     * 根据列表结果，解析对应界面
     *
     * @param responseCode
     * @param resultCode
     * @param adapter
     */
    fun sysnPageStatusByResponseData(
        responseCode: Int,
        resultCode: Int,
        adapter: BaseQuickAdapter<*, *>?,
        callBack: OnRequestSuccessCallback?
    ): DataParserHelper? {
        if (responseCode != 200) {
            if (callBack != null) {
                callBack.onError(ErrorType.REQUEST_CODE_ERROR, responseCode.toString())
                statusView?.showStatusView(PageStatus.PAGE_STATUS_NET_WORK_ERROR)
            }
            return this
        } else if (resultCode != 0) {
            if (callBack != null) {
                callBack.onError(ErrorType.REQUEST_CODE_ERROR, resultCode.toString())
                statusView?.showStatusView(PageStatus.PAGE_STATUS_LOAD_FAIL)
            }
            return this
        }

        //请求成功
        if (callBack != null) {
//            boolean isEmpty = (adapter == null || adapter.getData() == null || adapter.getData().size() == 0);
            val hasData = callBack.onSuccessCallBack()
            //请求成功
            if (hasData) {
                statusView?.showStatusView(PageStatus.PAGE_STATUS_NORMAL)
            } else {
                statusView?.showStatusView(PageStatus.PAGE_STATUS_NO_DATA)
            }
        }
        return this
    }


    /**
     * 根据列表结果，解析对应界面
     *
     * @param responseCode
     * @param resultCode
     * @param adapter
     */
    fun sysnPageStatusByResponseData(
        resultCode: String,
        adapter: BaseQuickAdapter<*, *>?,
        callBack: OnRequestSuccessCallback?
    ): DataParserHelper? {
        if ("0" != resultCode) {
            if (callBack != null) {
                callBack.onError(ErrorType.REQUEST_CODE_ERROR, resultCode)
                statusView?.showStatusView(PageStatus.PAGE_STATUS_LOAD_FAIL)
            }
            return this
        }

        //请求成功
        if (callBack != null) {
//            boolean isEmpty = (adapter == null || adapter.getData() == null || adapter.getData().size() == 0);
            val hasData = callBack.onSuccessCallBack()
            //请求成功
            if (hasData) {
                statusView?.showStatusView(PageStatus.PAGE_STATUS_NORMAL)
            } else {
                statusView?.showStatusView(PageStatus.PAGE_STATUS_NO_DATA)
            }
        }
        return this
    }

    /**
     * 根据列表结果，解析对应界面
     *
     * @param responseCode
     * @param resultCode
     * @param adapter
     */
    fun sysnPageStatusByResponseData(
        responseCode: Int,
        resultCode: String,
        adapter: BaseQuickAdapter<*, *>?,
        callBack: OnRequestSuccessCallback?
    ): DataParserHelper? {
        if (responseCode != 200) {
            if (callBack != null) {
                callBack.onError(ErrorType.REQUEST_CODE_ERROR, responseCode.toString())
                statusView?.showStatusView(PageStatus.PAGE_STATUS_NET_WORK_ERROR)
            }
            return this
        } else if ("0" != resultCode) {
            if (callBack != null) {
                callBack.onError(ErrorType.REQUEST_CODE_ERROR, resultCode)
                statusView?.showStatusView(PageStatus.PAGE_STATUS_LOAD_FAIL)
            }
            return this
        }

        //请求成功
        if (callBack != null) {
//            boolean isEmpty = (adapter == null || adapter.getData() == null || adapter.getData().size() == 0);
            val hasData = callBack.onSuccessCallBack()
            //请求成功
            if (hasData) {
                statusView?.showStatusView(PageStatus.PAGE_STATUS_NORMAL)
            } else {
                statusView?.showStatusView(PageStatus.PAGE_STATUS_NO_DATA)
            }
        }
        return this
    }

    /**
     * manager辅助判断请求成功
     */
    interface OnRequestSuccessCallback {
        //请求成功,并且返回有无数据,根据返回刷新界面
        fun onSuccessCallBack(): Boolean

        /**
         * 请求失败，返回resultCode
         * 判断哪个环节出现问题
         * 问题环节的值
         */
        fun onError(errorType: ErrorType, code: String)
    }


    /**
     * 状态检查
     */
    interface CheckResultCallBack {
        //请求成功,并且有无数据
        fun onSuccessCallBack(hasData: Boolean)

        /**
         * 请求失败，返回resultCode
         * 判断哪个环节出现问题
         * 问题环节的值
         */
        fun onError(type: ErrorType, code: Int)
    }
}