package com.ztsc.moudleuseguide.ui.activity

import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gyf.immersionbar.ImmersionBar
import com.ztsc.house.BaseActivity
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.ui.base.addClick
import kotlinx.android.synthetic.main.activity_demo_picture_color_bar.*
import kotlinx.android.synthetic.main.activity_statusbar_param.*
import java.util.*

/**
 * Created by benchengzhou on 2020/9/28  9:52 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： 图片 + 彩色状态栏
 * 备    注：
 */

class DemoPictureColorBarActivity : BaseActivity(), OnSeekBarChangeListener {
    override fun getContentView(): Int {
        return R.layout.activity_demo_picture_color_bar
    }

    override fun initListener() {
        mToolbar?.title = intent.extras?.get("title") as? String
        ImmersionBar.with(this)
            .statusBarView(R.id.top_view)
            .navigationBarColor(R.color.colorPrimary)
            .fullScreen(true)
            .addTag("PicAndColor")
            .init()

        addClick(btn_status_color, btn_navigation_color, btn_color)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_status_color -> {
                ImmersionBar.with(this).statusBarColor(R.color.colorAccent).init()
            }
            R.id.btn_navigation_color -> {
                if (ImmersionBar.hasNavigationBar(this)) {
                    ImmersionBar.with(this).navigationBarColor(R.color.colorAccent).init()
                } else {
                    Toast.makeText(this, "当前设备没有导航栏", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btn_color -> {
                ImmersionBar.with(this).getTag("PicAndColor").init()
            }
        }
    }

    override fun initData() {

        Glide
            .with(this)
            .asBitmap()
            .load(getPic())
            .apply(RequestOptions().placeholder(R.drawable.banner_default))
            .into(mIv)
        seek_bar.setOnSeekBarChangeListener(this)

    }

    fun getPic(): String? {
        val random = Random()
        return "http://106.14.135.179/ImmersionBar/" + random.nextInt(40) + ".jpg"
    }


    override fun onProgressChanged(
        seekBar: SeekBar?,
        progress: Int,
        fromUser: Boolean
    ) {
        val alpha = progress.toFloat() / 100
        ImmersionBar.with(this)
            .statusBarColorTransform(R.color.btn13)
            .navigationBarColorTransform(R.color.color_transp_common_ui)
            .addViewSupportTransformColor(toolbar)
            .barAlpha(alpha)
            .init()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}