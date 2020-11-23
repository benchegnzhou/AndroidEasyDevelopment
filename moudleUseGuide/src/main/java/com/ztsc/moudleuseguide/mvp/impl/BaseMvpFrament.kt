package com.zbc.mvp.impl


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import butterknife.internal.ListenerClass
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.ztsc.house.mvp.IMvpView
import com.ztsc.house.mvp.IPresenter
import com.ztsc.house.mvp.impl.BasePresenter

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

abstract class BaseMvpFrament<out P : BasePresenter<BaseMvpFrament<P>>> : IMvpView<P>, Fragment(),
    View.OnClickListener, View.OnLongClickListener {

    override val presenter: P


    init {
        presenter = createPresenterKt()
        //在fragment初始化的地方对view初始化
        presenter.view = this
    }


    /**
     * 使用反射的形式获取P的类型
     */
    fun createPresenterKt(): P {
        sequence {
            var thisClass: KClass<*> = this@BaseMvpFrament::class
            while (true) {
                //https://www.jianshu.com/p/9f720b9ccdea?utm_source=desktop&utm_medium=timeline
                //https://blog.csdn.net/weixin_34306593/article/details/89691830
                //yield是一个suspend方法, 放弃执行权, 并将数据返回.
                yield(thisClass.supertypes)
                //找到thisClass的所有父类，supertypes是包含接口的
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure ?: break
            }
        }.flatMap {
            it.flatMap {
                //获取到泛型参数
                it.arguments
            }.asSequence()
        }.first {
            it.type?.jvmErasure?.isSubclassOf(IPresenter::class) ?: false
        }.let {
            return it.type!!.jvmErasure.primaryConstructor!!.call() as P
        }

    }

    override fun onClick(v: View?) {

    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        presenter.onViewStateRestored(savedInstanceState)
    }
    /**
     * 刷新和加载的统一样式
     *
     * @param loadMoreListener
     * @param mRefreshLayout
     */
    protected fun initRefreshStyle(
        loadMoreListener: OnRefreshLoadMoreListener,
        mRefreshLayout: SmartRefreshLayout
    ) {
        mRefreshLayout?.setRefreshHeader(ClassicsHeader(activity))
        mRefreshLayout?.setRefreshFooter(ClassicsFooter(activity))
        mRefreshLayout?.setOnRefreshLoadMoreListener(loadMoreListener)
    }


    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }
}


