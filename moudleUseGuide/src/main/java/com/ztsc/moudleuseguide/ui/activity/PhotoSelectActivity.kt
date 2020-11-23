package com.ztsc.moudleuseguide.ui.activity


import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.gyf.immersionbar.ImmersionBar
import com.luck.picture.lib.entity.LocalMedia
import com.ztsc.house.BaseActivity
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.entity.bean.DemoBean
import com.ztsc.moudleuseguide.ui.base.addClick
import com.ztsc.moudleuseguide.ui.fragment.OnPhotoChangeCallBack
import com.ztsc.moudleuseguide.ui.fragment.PhotoSelectFragment
import kotlinx.android.synthetic.main.activity_demo_statusbar.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import java.util.*


/**
 * Created by benchengzhou on 2020/10/9  17:12 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： PhotoSelectActivity
 * 备    注：
 */

class PhotoSelectActivity : BaseActivity() {

    override fun getContentView(): Int {
        return R.layout.activity_photo_select
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_back -> finishAct()
        }
    }

    override fun setStatusBar() {
        ImmersionBar.with(this).titleBar(R.id.common_toolbar_root)
            .addTag("PhotoSelectActivity")
            .init()
    }


    override fun initListener() {
        //仿照baserecycleView我们这支持这样添加点击事件
        addClick(rl_back)

        initPhotoSelectFragment()
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

                }

                1 -> {

                }


            }
        }

        val data = arrayListOf<DemoBean>()
        data.add(DemoBean(0, "普通信息提示框", "Dialog示例"))
        data.add(DemoBean(1, "刪除样式的提示框", "Dialog示例"))


        mAdapter.setList(data)
    }


        private fun initPhotoSelectFragment() {
            val fManager = supportFragmentManager
            val fTransaction = fManager.beginTransaction()
            var selectFragement = PhotoSelectFragment.newInstance("")
            selectFragement.setPhotoSelectMax(3)
            fTransaction.add(R.id.framelayout_photo, selectFragement)
            fTransaction.commit()
            selectFragement.setOnPhoneChangCallBack(object : OnPhotoChangeCallBack {
                override fun onPhotoChange(localMedias: ArrayList<LocalMedia>?) {

                }
            })
        }


    override fun initData() {
        tv_title.text = "图片选择演示"
    }

}