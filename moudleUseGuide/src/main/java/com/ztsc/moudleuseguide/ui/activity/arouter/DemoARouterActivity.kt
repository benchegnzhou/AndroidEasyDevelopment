package com.ztsc.moudleuseguide.ui.activity.arouter

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.gyf.immersionbar.ImmersionBar
import com.ztsc.commonuimoudle.constant.CommonARouterContant
import com.ztsc.commonutils.toast.ToastUtils
import com.ztsc.house.BaseActivity
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.entity.bean.DataBean
import com.ztsc.moudleuseguide.entity.bean.DemoBean
import com.ztsc.moudleuseguide.ui.base.addClick
import kotlinx.android.synthetic.main.activity_live_event_bus.rv_demo_list
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import zbc.com.cn.utils.yes


/**
 * 在支持路由的页面上添加注解（必选）
 * 这里的路径需要注意的是至少需要有两级， /xx/xx
 * 路径标签个人建议写在一个类里面  ，这样方便统一管理和维护
 *
 *
 * 传递object出现 Caused by: java.lang.NullPointerException: Attempt to invoke interface method
 * 'java.lang.String com.alibaba.android.arouter.facade.service.SerializationService.object2Json(java.lang.Object)'
 * on a null object reference
 * 解决方案 https://blog.csdn.net/qq_35559358/article/details/84300708
 */
@Route(path = CommonARouterContant.DemoARouterActivity)
class DemoARouterActivity : BaseActivity() {


    @Autowired(name = "title")
    @JvmField
    var title: String = ""


    override fun getContentView(): Int {
        return R.layout.activity_live_event_bus
    }

    override fun initListener() {
        ImmersionBar.with(this).titleBar(common_toolbar_root)
            .addTag("DemoDialogActivity")
            .init()
        //这句必须在最开始调用
        ARouter.getInstance().inject(this)
        addClick(rl_back)
        rv_demo_list.layoutManager = LinearLayoutManager(this@DemoARouterActivity)
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
                    0 -> ARouter.getInstance()
                        .build(CommonARouterContant.DemoARouterWithParamsActivity)
                        .withString("title", data.msg)
                        .withInt("number", 2000)
                        .withSerializable("data", DemoBean(2, "这是一个序列化的对象", "序列化的对象"))
                        .withObject("bean", DataBean(20, "小刘"))
                        .navigation()
                    1 -> ARouter.getInstance()
                        .build(CommonARouterContant.DemoARouterWithParamsActivity)
                        .withString("title", data.msg)
                        .withInt("intValue", 2000)
                        .withObject("bean", DataBean(20, "小刘"))
                        .withSerializable("data", DemoBean(2, "这是一个序列化的对象", "序列化的对象"))
                        .navigation(this@DemoARouterActivity, 200)


                }


            }
        })

        val data = arrayListOf<DemoBean>()
        data.add(DemoBean(0, "Activity携带参数的跳转", "参数跳转"))
        data.add(DemoBean(1, "Activity携带参数跳转接收返回值", "带参带返回值跳转"))

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        (resultCode == Activity.RESULT_OK).yes {
            when (requestCode) {
                200->
                ToastUtils.normal("statusCode=${data?.getIntExtra("statusCode",0)},statusStr=${data?.getStringExtra("statusStr")}")
            }
        }

    }

}
