package com.ztsc.moudleuseguide.ui.activity

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.gyf.immersionbar.ImmersionBar
import com.ztsc.commonutils.editutils.*
import com.ztsc.house.BaseActivity
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.ui.base.addClick
import kotlinx.android.synthetic.main.activity_demo_edit_text.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import org.jetbrains.anko.imageResource

/**
 * Created by benchengzhou on 2020/10/20  13:34 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名：
 * 备    注：
 */

class DemoEditTextActivity : BaseActivity() {
    val TYPE_PASSWORD_INVISIABLE = 1000
    val TYPE_PASSWORD_VISIABLE = 2000
    private var PASSWORD_VISIABLE_TAG = TYPE_PASSWORD_INVISIABLE
    override fun getContentView(): Int {
        return R.layout.activity_demo_edit_text
    }

    override fun initListener() {
        addClick(rl_back, rl_is_can_see)
    }

    override fun setStatusBar() {
        ImmersionBar.with(this).titleBar(common_toolbar_root)
            .addTag("DemoEditTextActivity")
            .init()
    }

    override fun initData() {
        tv_title.text = intent.extras?.getString("title")
        et_no_emoji.setFilterTextNoEmojiNoEnter()
        et_number_letter.setFilterTextOnlyLetterNum()
        et_chinese.setFilterOnlyChinese()


        //解决输入键盘遮盖
//        AndroidBug5497Workaround().assistActivity(et_number_letter)
//        AndroidBug5497Workaround().assistActivity(et_chinese)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_back -> finishAct()
            R.id.rl_is_can_see -> {
                if (PASSWORD_VISIABLE_TAG == TYPE_PASSWORD_INVISIABLE) {
                    PASSWORD_VISIABLE_TAG = TYPE_PASSWORD_VISIABLE
                    iv_is_can_see.imageResource = R.drawable.list_ic_seeing
                    //显示密码
                    et_password.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    EditTextUtils.cursorMoveLast(et_password)
                } else {
                    PASSWORD_VISIABLE_TAG = TYPE_PASSWORD_INVISIABLE
                    iv_is_can_see.imageResource = R.drawable.llist_lnvisible
                    //隐藏密码
                    et_password.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    EditTextUtils.cursorMoveLast(et_password)
                }
            }
        }
    }


}