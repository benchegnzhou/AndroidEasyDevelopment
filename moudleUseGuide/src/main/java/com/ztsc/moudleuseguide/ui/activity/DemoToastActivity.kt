package com.ztsc.moudleuseguide.ui.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.gyf.immersionbar.ImmersionBar
import com.zbc.mvp.impl.BaseMvpActivity
import com.ztsc.commonutils.toast.ToastUtils
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.entity.bean.DemoBean
import com.ztsc.moudleuseguide.ui.base.addClick
import com.ztsc.moudleuseguide.ui.presenter.DemoToastBarPresenter
import kotlinx.android.synthetic.main.fragment_home_second_layout.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*


/**
 * Created by benchengzhou on 2020/9/22  13:13 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： 这是一个关于toast的demo
 * 备    注： 仿照baserecycleView我们这支持 addClick(rl_back,tv_title)、
 *   addClick(R.id.rl_back,R.id.tv_title)添加点击事件
 */

class DemoToastActivity : BaseMvpActivity<DemoToastBarPresenter>() {
    override fun getContentView(): Int {
        return R.layout.activity_demo_toast
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_back -> finishAct()
        }
    }

    override fun initListener() {
        ImmersionBar.with(this).titleBar(common_toolbar_root)
            .addTag("DemoDialogActivity")
            .init()
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
                0 -> ToastUtils.normal(data.msg ?: "")

                1 -> ToastUtils.normal(
                    data.msg!!,
                    icon = getResources().getDrawable(R.drawable.ic_pets_white_48dp)
                )
                2 -> ToastUtils.normalLongWithIcon(
                    data.msg!!,
                    icon = getResources().getDrawable(R.drawable.ic_pets_white_48dp)
                )
                3 -> ToastUtils.normal(
                    data.msg!!,
                    R.drawable.ic_pets_white_48dp
                )
                4 -> ToastUtils.success(
                    data.msg!!
                )
                5 -> ToastUtils.success(
                    R.string.toast_demo_success_msg
                )
                6 -> ToastUtils.error(
                    data.msg!!
                )
                7 -> ToastUtils.error(
                    R.string.toast_demo_error_msg
                )
                8 -> ToastUtils.info(
                    data.msg!!
                )
                9 -> ToastUtils.info(
                    R.string.toast_demo_error_msg
                )
                10 -> ToastUtils.warning(
                    data.msg!!
                )
                11 -> ToastUtils.warning(
                    R.string.toast_demo_error_msg
                )
                12 -> ToastUtils.custom(
                    data.msg!!
                )
                13 -> ToastUtils.custom(
                    R.string.toast_demo_error_msg
                )
            }
        }
        val data = arrayListOf<DemoBean>()
        data.add(DemoBean(0, "正常toast无图标(1-2s)", "Toast提示"))
        data.add(DemoBean(1, "正常toast有图标(1-2s)", "Toast提示"))
        data.add(DemoBean(3, "正常 toast有图标(3-4s)资源id", "Toast提示"))
        data.add(DemoBean(4, "成功toast 带有图标", "Toast提示"))
        data.add(DemoBean(5, "成功toast 带有图标 资源id", "Toast提示"))
        data.add(DemoBean(6, "失败toast 带有图标", "Toast提示"))
        data.add(DemoBean(7, "失败toast 带有图标 资源id", "Toast提示"))
        data.add(DemoBean(8, "蓝底反叹号信息提示toast 带有图标", "Toast提示"))
        data.add(DemoBean(9, "蓝底反叹号信息提示toast 带有图标 资源id", "Toast提示"))
        data.add(DemoBean(10, "黄底反叹号信息提示toast 带有图标", "Toast提示"))
        data.add(DemoBean(11, "黄底反叹号信息提示toast 带有图标 资源id", "Toast提示"))
        data.add(DemoBean(12, "自定义样式toast 带有图标", "Toast提示"))
        data.add(DemoBean(13, "自定义样式toast 带有图标 资源id", "Toast提示"))
        mAdapter.setList(data)
    }

    override fun initData() {
        tv_title.text = "Toast提示"
    }


}