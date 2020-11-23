package com.ztsc.commonutils.editutils

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup

/**
 * Created by xuyang on 2020/10/20  13:48 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 实现控件获取焦点后被输入键盘遮挡bug，
 *              edittext会整体被顶上去
 * 类    名：
 * 备    注：
 */

class AndroidBug5497Workaround {


    /**
     * 解决键盘遮挡问题
     */

    fun assistActivity(content: View?) {
        AndroidBug5497Workaround(content)
    }

    private var mChildOfContent: View? = null
    private var usableHeightPrevious = 0
    private var frameLayoutParams: ViewGroup.LayoutParams? = null

    private fun AndroidBug5497Workaround(content: View?) {
        if (content != null) {
            mChildOfContent = content
            mChildOfContent!!.viewTreeObserver
                .addOnGlobalLayoutListener { possiblyResizeChildOfContent() }
            frameLayoutParams = mChildOfContent!!.layoutParams
        }
    }

    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {
            //如果两次高度不一致
            //将计算的可视高度设置成视图的高度
            frameLayoutParams!!.height = usableHeightNow
            mChildOfContent!!.requestLayout() //请求重新布局
            usableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(): Int {
        //计算视图可视高度
        val r = Rect()
        mChildOfContent!!.getWindowVisibleDisplayFrame(r)
        return r.bottom
    }

}