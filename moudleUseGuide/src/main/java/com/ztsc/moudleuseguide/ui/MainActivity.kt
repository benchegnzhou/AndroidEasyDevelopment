package com.ztsc.moudleuseguide.ui

import android.os.Handler
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ztsc.commonuimoudle.BaseFragment
import com.ztsc.commonutils.quit.DoubleClickHelper
import com.ztsc.commonutils.stackmanager.ActivityStackManager
import com.ztsc.house.BaseActivity
import com.ztsc.moudleuseguide.ui.adapter.BaseFragmentAdapter
import com.ztsc.moudleuseguide.MyActivity
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.ui.fragment.HomeFirstFragment
import com.ztsc.moudleuseguide.ui.fragment.HomeFourFragment
import com.ztsc.moudleuseguide.ui.fragment.HomeSecondFragment
import com.ztsc.moudleuseguide.ui.fragment.HomeThirdFragment
import com.ztsc.moudleuseguide.util.KeyboardWatcher
import org.jetbrains.anko.toast

class MainActivity : BaseActivity(), KeyboardWatcher.SoftKeyboardStateListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    var mViewPager: ViewPager? = null
    var mBottomNavigationView: BottomNavigationView? = null

    var mPagerAdapter: BaseFragmentAdapter<Fragment>? = null





    override fun getContentView(): Int {

        return  R.layout.activity_main
    }

    override fun initListener() {
        mViewPager = findViewById(R.id.vp_home_pager)
        mBottomNavigationView = findViewById(R.id.bv_home_navigation)

        // 不使用图标默认变色
        mBottomNavigationView!!.itemIconTintList = null
        mBottomNavigationView!!.setOnNavigationItemSelectedListener(this)
        KeyboardWatcher.with(this)
            .setListener(this)
    }

    override fun initData() {
        mPagerAdapter = BaseFragmentAdapter(this)
        mPagerAdapter?.addFragment(HomeFirstFragment.newInstance("首页"))
        mPagerAdapter?.addFragment(HomeSecondFragment.newInstance("发现"))
        mPagerAdapter?.addFragment(HomeThirdFragment.newInstance("消息"))
        mPagerAdapter?.addFragment(HomeFourFragment.newInstance("我的"))
        // 设置成懒加载模式
        mPagerAdapter?.setLazyMode(true)
        mViewPager!!.adapter = mPagerAdapter
    }

    /**
     * [BottomNavigationView.OnNavigationItemSelectedListener]
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> {
                mViewPager!!.currentItem = 0
                return true
            }
            R.id.home_found -> {
                mViewPager!!.currentItem = 1
                return true
            }
            R.id.home_message -> {
                mViewPager!!.currentItem = 2
                return true
            }
            R.id.home_me -> {
                mViewPager!!.currentItem = 3
                return true
            }
            else -> {
            }
        }
        return false
    }

    /**
     * [KeyboardWatcher.SoftKeyboardStateListener]
     */
    override fun onSoftKeyboardOpened(keyboardHeight: Int) {
        mBottomNavigationView!!.visibility = View.GONE
    }

    override fun onSoftKeyboardClosed() {
        mBottomNavigationView!!.visibility = View.VISIBLE
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 回调当前 Fragment 的 onKeyDown 方法
        return if ((mPagerAdapter?.getShowFragment() as BaseFragment).onKeyDown(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if (DoubleClickHelper.isOnDoubleClick()) {
            // 移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false)
            Handler().postDelayed(object : Runnable {
                override fun run() {
                    // 进行内存优化，销毁掉所有的界面
                    ActivityStackManager.finishAllActivities()
                }

            }, 300)

        } else {
            toast(R.string.home_exit_hint)
        }
    }

    override fun onDestroy() {
        mViewPager!!.adapter = null
        mBottomNavigationView!!.setOnNavigationItemSelectedListener(null)
        super.onDestroy()
    }

    fun isSwipeEnable(): Boolean {
        return false
    }
}
