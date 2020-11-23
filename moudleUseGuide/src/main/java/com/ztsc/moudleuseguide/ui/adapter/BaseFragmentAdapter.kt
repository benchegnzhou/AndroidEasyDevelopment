package com.ztsc.moudleuseguide.ui.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import java.util.*


/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : FragmentPagerAdapter 基类
 */
class BaseFragmentAdapter<F : Fragment?>(manager: FragmentManager?) :
    FragmentPagerAdapter(manager!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    /** Fragment 集合  */
    private val mFragmentSet: MutableList<F> = ArrayList()

    /** Fragment 标题  */
    private val mFragmentTitle: MutableList<CharSequence?> =
        ArrayList()

    /**
     * 获取当前的Fragment
     */
    /** 当前显示的Fragment  */
    var mShowFragment: F? = null


    /** 当前 ViewPager  */
    private var mViewPager: ViewPager? = null

    /** 设置成懒加载模式  */
    private var mLazyMode = true

    constructor(activity: FragmentActivity) : this(activity.supportFragmentManager) {}
    constructor(fragment: Fragment) : this(fragment.childFragmentManager) {}

    override fun getItem(position: Int): Fragment {
        return mFragmentSet[position]!!
    }

    override fun getCount(): Int {
        return mFragmentSet.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitle[position]
    }

    override fun setPrimaryItem(
        container: ViewGroup,
        position: Int,
        any: Any
    ) {
        if (mShowFragment !== any) {
            // 记录当前的Fragment对象
            mShowFragment = any as F
        }
        super.setPrimaryItem(container, position, any)
    }

    /**
     * 添加 Fragment
     */
    @JvmOverloads
    fun addFragment(fragment: F, title: CharSequence? = null) {
        mFragmentSet.add(fragment)
        mFragmentTitle.add(title)
        if (mViewPager != null) {
            notifyDataSetChanged()
            if (mLazyMode) {
                mViewPager!!.offscreenPageLimit = count
            }
        }
    }

    /**
     * 获取当前的Fragment
     */
    fun getShowFragment(): F {
        return mShowFragment!!
    }

    override fun startUpdate(container: ViewGroup) {
        super.startUpdate(container)
        if (container is ViewPager) {
            // 记录绑定 ViewPager
            mViewPager = container
            refreshLazyMode()
        }
    }

    /**
     * 设置懒加载模式
     */
    fun setLazyMode(lazy: Boolean) {
        mLazyMode = lazy
        refreshLazyMode()
    }

    /**
     * 刷新加载模式
     */
    private fun refreshLazyMode() {
        if (mViewPager == null) {
            return
        }
        if (mLazyMode) {
            // 设置成懒加载模式（也就是不限制 Fragment 展示的数量）
            mViewPager!!.offscreenPageLimit = count
        } else {
            mViewPager!!.offscreenPageLimit = 1
        }
    }
}