package com.ztsc.moudleuseguide.ui.fragment

import android.os.Bundle
import android.view.View
import com.ztsc.commonuimoudle.BaseFragment
import com.ztsc.moudleuseguide.R

class HomeThirdFragment : BaseFragment {
    override fun getContentView(): Int {
        return R.layout.fragment_home_third_layout
    }

    constructor() : super()


    /**
     * 实例化首页
     *
     * @param msg
     * @return
     */

    companion object{
        @JvmStatic
        fun newInstance(msg: String): HomeThirdFragment {
            val fragment = HomeThirdFragment()
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