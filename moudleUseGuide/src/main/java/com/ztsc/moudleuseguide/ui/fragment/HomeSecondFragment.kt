package com.ztsc.moudleuseguide.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ztsc.commonuimoudle.BaseFragment
import com.ztsc.commonuimoudle.constant.CommonARouterContant
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.entity.bean.DemoBean
import com.ztsc.moudleuseguide.ui.activity.*
import kotlinx.android.synthetic.main.fragment_home_second_layout.*
import org.jetbrains.anko.support.v4.runOnUiThread


class HomeSecondFragment : BaseFragment {
    override fun getContentView(): Int {
        return R.layout.fragment_home_second_layout
    }

    constructor() : super()


    /**
     * 实例化首页
     *
     * @param msg
     * @return
     */

    companion object {
        @JvmStatic
        fun newInstance(msg: String): HomeSecondFragment {
            val fragment =
                HomeSecondFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_MSG, msg)
            fragment.setArguments(bundle)
            return fragment
        }
    }


    override fun initListener() {

    }

    override fun onLazyLoad() {

    }

    override fun initData() {
        rv_demo_list.layoutManager = LinearLayoutManager(activity)
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

        mAdapter.setOnItemChildClickListener(object : OnItemChildClickListener {
            override fun onItemChildClick(
                adapter: BaseQuickAdapter<*, *>,
                view: View,
                position: Int
            ) {
                val data = mAdapter.data.get(position)
                when (data.id) {
                    0 -> startAct(DemoToastActivity::class.java)
                    1 -> startAct(DemoDialogActivity::class.java)
                    2 -> startAct(DemoStatusBarActivity::class.java)
                    3 -> startAct(DemoLoggerActivity::class.java)
                    4 -> startAct(DemoToastActivity::class.java)
                    5 -> startAct(DemoPermistionActivity::class.java)
                    6 -> startAct(PhotoSelectActivity::class.java)
                    7 -> ARouter.getInstance()
                        .build(CommonARouterContant.DemoARouterActivity)
                        .withString("title", data.msg)
                        .navigation()
                    8-> ARouter.getInstance()
                        .build(CommonARouterContant.DemoLiveEventBusReceiverActivity)
                        .withString("title", data.msg)
                        .navigation()

                    9-> ARouter.getInstance()
                        .build(CommonARouterContant.DemoOnSaveInstanceActivity)
                        .withString("title", data.msg)
                        .navigation()
                }


            }
        })

        val data = arrayListOf<DemoBean>()
        data.add(DemoBean(0, "Toast提示", "Toast提示"))
        data.add(DemoBean(1, "Dialog组件", "Dialog组件"))
        data.add(DemoBean(2, "状态栏切换", "状态栏切换"))
        data.add(DemoBean(3, "日志输出组件", "日志输出组件"))
        data.add(DemoBean(4, "MVP架构", "MVP架构"))
        data.add(DemoBean(5, "权限申请", "权限申请"))
        data.add(DemoBean(6, "图片选择演示", "图片选择"))
        data.add(DemoBean(7, "ARouter路由跳转练习", "ARouter路由跳转练习"))
        data.add(DemoBean(8, "LiveEventBus练习", "LiveEventBus练习"))
        data.add(DemoBean(9, "onSaveInstance数据保存实验", "onSaveInstance数据保存实验"))
        mAdapter.setList(data)

    }

    override fun onClick(v: View?) {

    }


}

