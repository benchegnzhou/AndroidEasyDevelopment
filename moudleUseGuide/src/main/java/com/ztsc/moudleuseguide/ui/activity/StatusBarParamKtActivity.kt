package com.ztsc.moudleuseguide.ui.activity

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.gyf.immersionbar.ktx.*
import com.zbc.mvp.impl.BaseMvpActivity

import com.ztsc.commonutils.toast.ToastUtils
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.ui.presenter.DemoStatusBarParamKtPresenter
import kotlinx.android.synthetic.main.activity_statusbar_param.*

import zbc.com.cn.utils.otherwise
import zbc.com.cn.utils.yes


class StatusBarParamKtActivity : BaseMvpActivity<DemoStatusBarParamKtPresenter>() {
    private var mIsHideStatusBar = false
    override fun getContentView(): Int {
        return R.layout.activity_statusbar_param
    }

    override fun setStatusBar() {
        immersionBar {
            titleBar(mToolbar)
            navigationBarColor(R.color.btn13)
            setOnNavigationBarListener {
                it.yes {
                    ToastUtils.normal("导航栏显示了")
                }
                    .otherwise {
                        ToastUtils.normal("导航栏隐藏了")
                    }
            }
        }
    }

    override fun initListener() {
        mToolbar?.title = intent.extras?.get("title") as? String
        mBtnStatus?.setOnClickListener {
            mIsHideStatusBar = if (!mIsHideStatusBar) {
                hideStatusBar()
                true
            } else {
                showStatusBar()
                false
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(mTvInsets) { _, windowInsetsCompat ->
            mTvInsets.text =
                "${mTvInsets.title}${windowInsetsCompat.systemWindowInsetTop}".content()
            windowInsetsCompat.consumeSystemWindowInsets()
        }
    }

    override fun initData() {
        mTvStatus.text = "${mTvStatus.title}$statusBarHeight".content()
        mTvHasNav.text = "${mTvHasNav.title}$hasNavigationBar".content()
        mTvNav.text = "${mTvNav.title}$navigationBarHeight".content()
        mTvNavWidth.text = "${mTvNavWidth.title}$navigationBarWidth".content()
        mTvAction.text = "${mTvAction.title}$actionBarHeight".content()
        mTvHasNotch.post { mTvHasNotch.text = "${mTvHasNotch.title}$hasNotchScreen".content() }
        mTvNotchHeight.post {
            mTvNotchHeight.text = "${mTvNotchHeight.title}$notchHeight".content()
        }
        mTvFits.text =
            "${mTvFits.title}${findViewById<View>(android.R.id.content).checkFitsSystemWindows}".content()
        mTvStatusDark.text = "${mTvStatusDark.title}$isSupportStatusBarDarkFont".content()
        mTvNavigationDark.text = "${mTvNavigationDark.title}$isSupportNavigationIconDark".content()
    }

    private val TextView.title get() = text.toString().split("   ")[0] + "   "

    private fun String.content(): SpannableString {
        val split = split("   ")
        return SpannableString(this).apply {
            val colorSpan = ForegroundColorSpan(
                ContextCompat.getColor(
                    this@StatusBarParamKtActivity,
                    R.color.btn3
                )
            )
            setSpan(
                colorSpan,
                this.length - split[1].length,
                this.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
    }

}