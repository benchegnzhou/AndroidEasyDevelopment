package com.ztsc.house.mvp.impl

import android.os.Bundle
import com.ztsc.house.mvp.IMvpView
import com.ztsc.house.mvp.IPresenter


abstract class BasePresenter<out V : IMvpView<IPresenter<V>>> : IPresenter<V> {


    //这个view要在fragment或者activity中初始化
    //这里view定义成T类型会报错，out V是协变的 ，这里的view有get同时也有set方法，是不变的
    //需要人为告诉编译器忽略安全检查
    override lateinit var view: @UnsafeVariance V

    override fun onCreate(saveInstanceState: Bundle?) = Unit

    override fun onSaveInstanceState(outState: Bundle) = Unit

    override fun onViewStateRestored(saveInstanceState: Bundle?) = Unit

    override fun onStart() = Unit

    override fun onResume() = Unit

    override fun onPause() = Unit

    override fun onStop() = Unit

    override fun onDestroy() = Unit


}