package com.ztsc.moudleuseguide.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ztsc.commonuimoudle.BaseFragment
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.entity.bean.DemoBean
import com.ztsc.moudleuseguide.ui.activity.*
import kotlinx.android.synthetic.main.fragment_home_second_layout.*

/**
 * Created by benchengzhou on 2020/10/20  13:31 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名：
 * 备    注：
 */

class HomeFourFragment : BaseFragment {
    override fun getContentView(): Int {
        return R.layout.fragment_home_four_layout
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
        fun newInstance(msg: String): HomeFourFragment {
            val fragment =
                HomeFourFragment()
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
                    0 -> startAct(
                        getBundle().put("title", data.msg),
                        DemoEditTextActivity::class.java
                    )
                    1 -> startAct(DemoEditTextActivity::class.java)
                    2 -> startAct(DemoEditTextActivity::class.java)
                    3 -> startAct(DemoEditTextActivity::class.java)
                    4 -> startAct(DemoEditTextActivity::class.java)
                    5 -> startAct(DemoEditTextActivity::class.java)
                    6 -> startAct(DemoEditTextActivity::class.java)
                }


            }
        })

        val data = arrayListOf<DemoBean>()
        data.add(DemoBean(0, "EditText工具", "自定义工具"))
        data.add(DemoBean(1, "Dialog组件", "Dialog组件"))
        data.add(DemoBean(2, "状态栏切换", "状态栏切换"))
        data.add(DemoBean(3, "日志输出组件", "日志输出组件"))
        data.add(DemoBean(4, "MVP架构", "MVP架构"))
        data.add(DemoBean(5, "权限申请", "权限申请"))
        data.add(DemoBean(6, "图片选择演示", "图片选择"))
        mAdapter.setList(data)

    }

    override fun onClick(v: View?) {

    }
}