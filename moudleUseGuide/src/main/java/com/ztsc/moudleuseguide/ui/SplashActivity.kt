package com.ztsc.moudleuseguide.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.ztsc.moudleuseguide.BuildConfig

import com.ztsc.moudleuseguide.MyActivity
import com.ztsc.moudleuseguide.R


/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 闪屏界面
 */
class SplashActivity : MyActivity() {
    private var mLottieView: LottieAnimationView? = null
    private var mDebugView: View? = null


    protected override fun initView() {
        mLottieView = findViewById(R.id.iv_splash_lottie)
        mDebugView = findViewById(R.id.iv_splash_debug)
        // 设置动画监听
        mLottieView?.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        })
    }

    override fun initData() {
    }

    override fun createStatusBarConfig(): ImmersionBar {
        return super.createStatusBarConfig() // 隐藏状态栏和导航栏
            .hideBar(BarHide.FLAG_HIDE_BAR)
    }

    override fun getlayoutId(): Int {
     return   R.layout.activity_splash
    }

    override fun onBackPressed() {
        //禁用返回键
        //super.onBackPressed();
    }

    val isSwipeEnable: Boolean
        get() = false

    protected override fun onDestroy() {
        mLottieView?.removeAllAnimatorListeners()
        super.onDestroy()
    }
}