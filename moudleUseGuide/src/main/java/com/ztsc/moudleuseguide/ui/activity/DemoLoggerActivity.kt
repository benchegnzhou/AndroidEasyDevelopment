package com.ztsc.moudleuseguide.ui.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zbc.mvp.impl.BaseMvpActivity
import com.ztsc.commonutils.logcat.LoggerUtil
import com.ztsc.commonutils.toast.ToastUtils
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.R2.id.rl_back
import com.ztsc.moudleuseguide.entity.bean.DemoBean
import com.ztsc.moudleuseguide.ui.base.addClick
import com.ztsc.moudleuseguide.ui.presenter.DemoLoggerPresenter
import kotlinx.android.synthetic.main.activity_demo_logger.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import org.json.JSONObject


/**
 * Created by benchengzhou on 2020/9/22  13:13 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： 这是一个关于toast的demo
 * 备    注： 仿照baserecycleView我们这支持 addClick(rl_back,tv_title)、
 *   addClick(R.id.rl_back,R.id.tv_title)添加点击事件
 */

class DemoLoggerActivity : BaseMvpActivity<DemoLoggerPresenter>() {
    override fun getContentView(): Int {
        return R.layout.activity_demo_logger
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

        var json = JSONObject()
        try {
            json.put("name", "xiaoming")
            json.put("age", 12)
            json.put("home", "shandong")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val data = mAdapter.data.get(position)
            when (data.id) {
                0 -> LoggerUtil.d(data.msg)

                1 -> LoggerUtil.d(json)

                2 -> LoggerUtil.d(data)

                //实际测试info的打印仅支持第一个参数
                3 -> LoggerUtil.i(data.msg!!)

                4 -> LoggerUtil.i("%s : %s", data.msg!!, json)

                5 -> LoggerUtil.i("%d , %s", position, data)

                6 -> {
                    LoggerUtil.json(json.toString())
                    ToastUtils.info(
                        data.msg!!
                    )
                }

                7 -> LoggerUtil.e(data.msg!!)

                8 -> LoggerUtil.e("%s : %s", data.msg!!, json)

                9 -> LoggerUtil.e(java.lang.Exception("我是抛出的异常"), data.msg!!)


            }
        }
        val data = arrayListOf<DemoBean>()
        data.add(DemoBean(0, "调试日志，字符串打印", "Logger日志"))
        data.add(DemoBean(1, "调试日志，json打印", "Logger日志"))
        data.add(DemoBean(2, "调试日志，对象打印", "Logger日志"))
        data.add(DemoBean(3, "info日志，字符串打印", "Logger日志"))
        data.add(DemoBean(4, "info日志，打印格式化日志信息1", "Logger日志"))
        data.add(DemoBean(5, "info日志，打印格式化日志信息2", "Logger日志"))
        data.add(DemoBean(6, "json字符串格式化后输出，适合请求结果格式化输出", "Logger日志"))
        data.add(DemoBean(7, "error日志打印（仅字符串）", "Logger日志"))
        data.add(DemoBean(8, "error日志打印带有格式化信息的日志", "Logger日志"))
        data.add(DemoBean(9, "带有Throwable异常信息的error日志打印", "Logger日志"))

        mAdapter.setList(data)
    }

    override fun initData() {
        tv_title.text = "Toast提示"
    }


}