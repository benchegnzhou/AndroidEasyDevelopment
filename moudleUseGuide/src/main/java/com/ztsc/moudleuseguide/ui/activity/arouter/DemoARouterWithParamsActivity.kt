package com.ztsc.moudleuseguide.ui.activity.arouter

import android.app.Activity
import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.ztsc.commonuimoudle.constant.CommonARouterContant
import com.ztsc.house.BaseActivity
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.entity.bean.DataBean
import com.ztsc.moudleuseguide.entity.bean.DemoBean
import com.ztsc.moudleuseguide.ui.base.addClick
import kotlinx.android.synthetic.main.activity_demo_live_eventbus_with_params.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*


/**
 * Created by benchengzhou on 2020/10/28  16:53 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： 带有参数的 LiveEventBus
 * 备    注：
 */

@Route(path = CommonARouterContant.DemoARouterWithParamsActivity)
class DemoARouterWithParamsActivity : BaseActivity() {

    @JvmField
    @Autowired(name = "title")
    var title: String = ""

    @JvmField
    @Autowired(name = "number")
    var number: Int = 0

    @JvmField
    @Autowired(name = "data")
    var menu: DemoBean? = null

    @JvmField
    @Autowired(name = "bean")
    var bean: DataBean? = null


    override fun getContentView(): Int {
        return R.layout.activity_demo_live_eventbus_with_params
    }

    override fun initListener() {
        ImmersionBar.with(this).titleBar(common_toolbar_root)
            .addTag("DemoDialogActivity")
            .init()
        ARouter.getInstance().inject(this)
        addClick(rl_back,tv_jump)
    }

    override fun initData() {

        tv_title.text = title
        tv_msg.text="接收到的数据为\ntitle=${title} \nnumber=${number} \ndata={ id=${menu?.id},msg=${menu?.msg},desc=${menu?.desc}} \nbean={ age=${bean?.age},name=${bean?.name}} "
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_back -> finishAct()

            R.id.tv_jump -> {
                val intent = Intent()
                intent.putExtra("statusStr","OK")
                intent.putExtra("statusCode",200)
                setResult(Activity.RESULT_OK,intent)
                finishAct()
            }
        }
    }

}