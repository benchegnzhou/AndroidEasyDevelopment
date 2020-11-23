package com.ztsc.moudleuseguide.ui.fragment

import android.os.Bundle
import android.view.View
import com.ztsc.commonuimoudle.BaseFragment
import com.ztsc.moudleuseguide.R


class HomeFirstFragment : BaseFragment {
    override fun getContentView(): Int {
        return R.layout.fragment_home_first_layout
    }

    constructor() : super()


    /**
     * 实例化首页
     *
     * @param msg
     * @return
     */
    companion object {
        @JvmStatic
        fun newInstance(msg: String): HomeFirstFragment {
            val fragment = HomeFirstFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_MSG, msg)
            fragment.setArguments(bundle)
            return fragment
        }
    }


    override fun initListener() {
    }

    override fun onLazyLoad() {

    }

    override fun initData() {

    }

    override fun onClick(v: View?) {

    }
}