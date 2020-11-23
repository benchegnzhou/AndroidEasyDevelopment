package com.ztsc.moudleuseguide.ui.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.gyf.immersionbar.ImmersionBar
import com.ztsc.commonuimoudle.base.BaseDialog
import com.ztsc.commonuimoudle.dialog.*

import com.ztsc.commonutils.toast.ToastUtils
import com.ztsc.house.BaseActivity
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.entity.bean.DemoBean
import com.ztsc.moudleuseguide.ui.base.addClick
import kotlinx.android.synthetic.main.activity_demo_statusbar.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*


class DemoDialogActivity : BaseActivity() {

    override fun getContentView(): Int {
        return R.layout.activity_demo_dialog
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_back -> finishAct()
        }
    }

    override fun setStatusBar() {
        ImmersionBar.with(this).titleBar(common_toolbar_root)
            .addTag("DemoDialogActivity")
            .init()
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
                0 -> {

                    // 消息对话框
                    MessageDialog.Builder(this) // 标题可以不用填写
//                        .setTitle("我是标题") // 内容必须要填写
                        .setMessage("我是内容我是内容我是内容我是内容我是内容") // 确定按钮文本
                        .setConfirm(getString(R.string.common_confirm)) // 设置 null 表示不显示取消按钮
                        .setCancel(getString(R.string.common_cancel)) // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .setListener(object : MessageDialog.OnListener {
                            override fun onConfirm(dialog: BaseDialog?) {
                                ToastUtils.normal("确定了")
                            }

                            override fun onCancel(dialog: BaseDialog?) {
                                ToastUtils.normal("取消了")
                            }
                        })
                        .show()
                }

                1 -> {

                    // 消息对话框
                    DeleteDialog.Builder(this) // 标题可以不用填写
//                        .setTitle("我是标题") // 内容必须要填写
                        .setMessage("我是内容我是内容我是内容我是内容我是内容") // 确定按钮文本
                        .setConfirm(getString(R.string.common_confirm)) // 设置 null 表示不显示取消按钮
                        .setCancel(getString(R.string.common_cancel)) // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .setListener(object : DeleteDialog.OnListener {
                            override fun onConfirm(dialog: BaseDialog?) {
                                ToastUtils.normal("确定了")
                            }

                            override fun onCancel(dialog: BaseDialog?) {
                                ToastUtils.normal("取消了")
                            }
                        })
                        .show()
                }
                2 -> {
                    // 失败对话框
                    HintDialog.Builder(this)
                        .setIcon(HintDialog.ICON_ERROR)
                        .setMessage("错误")
                        .show()
                }

                3 -> {
                    // 成功对话框
                    HintDialog.Builder(this)
                        .setIcon(HintDialog.ICON_FINISH)
                        .setMessage("完成")
                        .show()
                }

                4 -> {
                    // 信息提交成功
                    SubmitSuccessDialog.Builder(this)
                        .setMessage("信息提交成功，请等待审核")
                        .setBtnText("知道了")
                        .setListener(object : SubmitSuccessDialog.OnListener {
                            override fun onConfirm(dialog: BaseDialog?) {

                            }
                        })
                        .show()
                }
                5 -> {
                    // 信息提交成功，带有标题
                    SubmitSuccessDialog.Builder(this)
                        .setMessage("信息提交成功，请等待审核")
                        .setBtnText("知道了")
                        .setTitle("提交成功")
                        .setListener(object : SubmitSuccessDialog.OnListener {
                            override fun onConfirm(dialog: BaseDialog?) {

                            }
                        })
                        .show()
                }
                6 -> {
                    // 信息提交失败
                    SubmitFailDialog.Builder(this)
                        .setMessage("信息提交失败，请等待审核")
                        .setBtnText("知道了")
                        .setListener(object : SubmitFailDialog.OnListener {
                            override fun onConfirm(dialog: BaseDialog?) {

                            }
                        })
                        .show()
                }
                7 -> {
                }


            }
        }

        val data = arrayListOf<DemoBean>()
        data.add(DemoBean(0, "普通信息提示框", "Dialog示例"))
        data.add(DemoBean(1, "刪除样式的提示框", "Dialog示例"))
        data.add(DemoBean(2, "提交失败风格的dialog", "Dialog示例"))
        data.add(DemoBean(3, "提交成功风格的dialog", "Dialog示例"))
        data.add(DemoBean(4, "带有确定按钮的提交成功dialog", "Dialog示例"))
        data.add(DemoBean(5, "带有确定按钮和标题的提交成功dialog", "Dialog示例"))
        data.add(DemoBean(6, "带有确定按钮的提交失败dialog", "Dialog示例"))
        data.add(DemoBean(7, "版本更新的dialog", "Dialog示例"))

        mAdapter.setList(data)
    }

    override fun initData() {
        tv_title.text = "Dialog样式测试"
    }
}