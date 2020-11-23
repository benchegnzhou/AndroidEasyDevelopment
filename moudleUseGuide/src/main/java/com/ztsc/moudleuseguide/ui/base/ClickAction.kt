package com.ztsc.moudleuseguide.ui.base

import android.view.View
import androidx.annotation.IdRes
import com.zbc.mvp.impl.BaseMvpActivity
import com.zbc.mvp.impl.BaseMvpFrament
import com.ztsc.commonuimoudle.BaseFragment
import com.ztsc.house.BaseActivity
import com.ztsc.house.mvp.impl.BasePresenter

/**
 * Created by benchengzhou on 2020/9/22  13:19 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名：
 * 备    注：
 */
fun BaseMvpActivity<BasePresenter<BaseMvpActivity<*>>>.addClick(@IdRes vararg viewIds: Int){
    for (viewId in viewIds) {
        findViewById<View>(viewId)?.setOnClickListener(this)
    }
}
fun BaseMvpActivity<BasePresenter<BaseMvpActivity<*>>>.addClick( vararg views: View){
    for (view in views) {
        view?.setOnClickListener(this)
    }
}



fun BaseMvpActivity<BasePresenter<BaseMvpActivity<*>>>.addLongClick(@IdRes vararg viewIds: Int){
    for (viewId in viewIds) {
        findViewById<View>(viewId)?.setOnLongClickListener(this)
    }
}
fun BaseMvpActivity<BasePresenter<BaseMvpActivity<*>>>.addLongClick( vararg views: View){
    for (view in views) {
        view?.setOnLongClickListener(this)
    }
}


fun BaseMvpFrament<BasePresenter<BaseMvpFrament<*>>>.addClick(@IdRes vararg viewIds: Int){
    for (viewId in viewIds) {
        this.activity?.findViewById<View>(viewId)?.setOnClickListener(this)
    }
}


fun BaseMvpFrament<BasePresenter<BaseMvpFrament<*>>>.addClick( vararg views: View){
    for (view in views) {
        view?.setOnClickListener(this)
    }
}




fun BaseMvpFrament<BasePresenter<BaseMvpFrament<*>>>.addLongClick(@IdRes vararg viewIds: Int){
    for (viewId in viewIds) {
        activity?.findViewById<View>(viewId)?.setOnLongClickListener(this)
    }
}
fun BaseMvpFrament<BasePresenter<BaseMvpFrament<*>>>.addLongClick( vararg views: View){
    for (view in views) {
        view?.setOnLongClickListener(this)
    }
}





fun BaseActivity.addClick(@IdRes vararg viewIds: Int){
    for (viewId in viewIds) {
       findViewById<View>(viewId)?.setOnClickListener(this)
    }
}


fun BaseActivity.addClick( vararg views: View){
    for (view in views) {
        view?.setOnClickListener(this)
    }
}




fun BaseActivity.addLongClick(@IdRes vararg viewIds: Int){
    for (viewId in viewIds) {
        findViewById<View>(viewId)?.setOnLongClickListener(this)
    }
}
fun BaseActivity.addLongClick( vararg views: View){
    for (view in views) {
        view?.setOnLongClickListener(this)
    }
}





fun BaseFragment.addClick(@IdRes vararg viewIds: Int){
    for (viewId in viewIds) {
        activity?.findViewById<View>(viewId)?.setOnClickListener(this)
    }
}


fun BaseFragment.addClick( vararg views: View){
    for (view in views) {
        view?.setOnClickListener(this)
    }
}




fun BaseFragment.addLongClick(@IdRes vararg viewIds: Int){
    for (viewId in viewIds) {
        activity?.findViewById<View>(viewId)?.setOnLongClickListener(this)
    }
}
fun BaseFragment.addLongClick( vararg views: View){
    for (view in views) {
        view?.setOnLongClickListener(this)
    }
}

