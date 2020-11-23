package com.ztsc.moudleuseguide.ui.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zbc.mvp.impl.BaseMvpActivity
import com.ztsc.commonutils.logcat.LoggerUtil
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.entity.bean.DemoBean
import com.ztsc.moudleuseguide.ui.base.addClick
import com.ztsc.moudleuseguide.ui.presenter.DemoStatusBarPresenter
import kotlinx.android.synthetic.main.activity_demo_statusbar.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*

class DemoStatusBarActivity : BaseMvpActivity<DemoStatusBarPresenter>() {
    override fun getContentView(): Int {
        return R.layout.activity_demo_statusbar
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_back -> finishAct()
        }
    }

    override fun initListener() {
        //仿照baserecycleView我们这支持这样添加点击事件
        addClick(rl_back)
        rv_demo_list.layoutManager = LinearLayoutManager(this)
        val mAdapter =
            object : BaseQuickAdapter<DemoBean, BaseViewHolder>(R.layout.item_home_second, null) {
                override fun getItemViewType(position: Int): Int {
                    addChildClickViewIds(R.id.tv_msg)
                    return super.getItemViewType(position)
                }

                override fun convert(holder: BaseViewHolder, item: DemoBean) {
                    holder.setText(R.id.tv_msg, item.msg ?: "")
                }
            }
        rv_demo_list.adapter = mAdapter

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val data = mAdapter.data.get(position)
            when (data.id) {
                0 -> startAct(
                    StatusBarParamActivity::class.java,
                    getBundle().put("title", data.msg)
                )

                1 -> startAct(
                    StatusBarParamKtActivity::class.java,
                    getBundle().put("title", data.msg)
                )

                2 -> startAct(
                    DemoPictureColorBarActivity::class.java,
                    getBundle().put("title", data.msg)
                )

                //实际测试info的打印仅支持第一个参数
                3 -> LoggerUtil.i(data.msg!!)

                4 -> startAct(StatusBarParamKtActivity::class.java)

                5 -> LoggerUtil.i("%d , %s", position, data)

                6 -> startAct(StatusBarParamActivity::class.java)

                7 -> LoggerUtil.e(data.msg!!)

                8 -> startAct(StatusBarParamActivity::class.java)

                9 -> LoggerUtil.e(java.lang.Exception("我是抛出的异常"), data.msg!!)


            }
        }

        val data = arrayListOf<DemoBean>()
        data.add(DemoBean(0, "Bar相关参数信息(Java)", "StatusBar状态栏"))
        data.add(DemoBean(1, "Bar相关参数信息(Kotlin)", "StatusBar状态栏"))
        data.add(DemoBean(2, "图片状态栏 + 彩色导航栏", "StatusBar状态栏"))
        data.add(DemoBean(3, "全屏图片", "StatusBar状态栏"))
        data.add(DemoBean(4, "彩色状态栏 + 彩色导航栏", "StatusBar状态栏"))
        data.add(DemoBean(5, "仿QQ状态栏和标题栏渐变色", "StatusBar状态栏"))
        data.add(DemoBean(6, "结合侧滑返回使用", "StatusBar状态栏"))
        data.add(DemoBean(7, "结合fragment使用", "StatusBar状态栏"))
        data.add(DemoBean(8, "结合Dialog使用", "StatusBar状态栏"))
        data.add(DemoBean(9, "结合PopupWindow使用", "StatusBar状态栏"))
        data.add(DemoBean(10, "结合侧边栏使用", "StatusBar状态栏"))
        data.add(DemoBean(11, "结合CoordinatorLayout使用", "StatusBar状态栏"))
        data.add(DemoBean(12, "结合TabLayout使用", "StatusBar状态栏"))
        data.add(DemoBean(13, "结合TabLayout使用(二)", "StatusBar状态栏"))
        data.add(DemoBean(14, "结合WebView使用", "StatusBar状态栏"))

        mAdapter.setList(data)
    }

    override fun initData() {
        tv_title.text = "ImmersionBar状态栏"
    }
}