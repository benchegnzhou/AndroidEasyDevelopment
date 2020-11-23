package com.ztsc.moudleuseguide.ui.activity.liveeventbus

import androidx.annotation.Nullable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jeremyliao.liveeventbus.LiveEventBus
import com.ztsc.commonuimoudle.constant.CommonARouterContant
import com.ztsc.commonutils.toast.ToastUtils
import com.ztsc.house.BaseActivity
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.entity.event.DemoEvent
import com.ztsc.moudleuseguide.ui.base.addClick
import kotlinx.android.synthetic.main.layout_common_toolbar.*


/**
 * Created by benchengzhou on 2020/10/29  23:17 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： LiveEventBus事件接收
 * 备    注：
 */

@Route(path = CommonARouterContant.DemoLiveEventBusReceiverActivity)
class DemoLiveEventBusReceiverActivity : BaseActivity() {

    @JvmField
    @Autowired
    var title: String = ""


    override fun getContentView(): Int {
        return R.layout.activity_demo_live_event_bus_receiver
    }

    override fun initListener() {
        ARouter.getInstance().inject(this)
        addClick(rl_back)
        LiveEventBus.get(
            "msgKey",
            String::class.java
        ).observe(this, object : Observer<String?> {
            override fun onChanged(@Nullable s: String?) {
                ToastUtils.custom("接收到String字符串消息 ${s}")
            }
        })

        LiveEventBus.get(
            DemoEvent::class.java
        ).observe(this, object : Observer<DemoEvent?> {
            override fun onChanged(@Nullable event: DemoEvent?) {
                ToastUtils.custom("接收到对象消息 ${event?.event}")
            }
        })

        ARouter.getInstance().build(CommonARouterContant.DemoLiveEventBusActivity).navigation()
    }

    override fun initData() {
        tv_title.text = title
    }

}