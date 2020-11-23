package com.ztsc.moudleuseguide.ui.activity.liveeventbus

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.gyf.immersionbar.ImmersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.ztsc.commonuimoudle.constant.CommonARouterContant
import com.ztsc.house.BaseActivity
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.entity.bean.DemoBean
import com.ztsc.moudleuseguide.entity.event.DemoEvent
import com.ztsc.moudleuseguide.ui.base.addClick
import kotlinx.android.synthetic.main.activity_live_event_bus.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*

/**
 * Created by benchengzhou on 2020/10/29  19:10 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： 使用之前需要在application中进行初始化
 * 备    注：
 */

@Route(path = CommonARouterContant.DemoLiveEventBusActivity)
class DemoLiveEventBusActivity : BaseActivity() {

    @Autowired(name = "title")
    @JvmField
    var title: String = ""


    override fun getContentView(): Int {
        return R.layout.activity_demo_live_event_bus
    }


    override fun initListener() {
        ImmersionBar.with(this).titleBar(common_toolbar_root)
            .addTag("DemoLiveEventBusActivity")
            .init()
        //这句必须在最开始调用
        ARouter.getInstance().inject(this)
        addClick(rl_back)
        rv_demo_list.layoutManager = LinearLayoutManager(this@DemoLiveEventBusActivity)
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
                    0 -> {
                        LiveEventBus
                            .get("msgKey")
                            .post(data.msg)
                        ARouter.getInstance()
                            .build(CommonARouterContant.DemoLiveEventBusReceiverActivity)
                            .withString("title",data.msg)
                            .navigation()
                    }
                    1 -> {
                        var event = DemoEvent("Hello world")
                        event.event = "消息事件"
                        LiveEventBus
                            .get(DemoEvent::class.java)
                            .post(event)
                        ARouter.getInstance()
                            .build(CommonARouterContant.DemoLiveEventBusReceiverActivity)
                            .withString("title",data.msg)
                            .navigation()
                    }

                }


            }
        })

        val data = arrayListOf<DemoBean>()
        data.add(DemoBean(0, "不定义消息直接发送", "发送消息"))
        data.add(DemoBean(1, "先定义消息，再发送消息", "发送消息"))

        mAdapter.setList(data)
    }

    override fun initData() {
        tv_title.text = title
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_back -> finishAct()

        }
    }

}