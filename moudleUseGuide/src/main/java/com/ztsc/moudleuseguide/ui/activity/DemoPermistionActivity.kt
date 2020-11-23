package com.ztsc.moudleuseguide.ui.activity

import android.Manifest
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tbruyelle.rxpermissions3.Permission
import com.tbruyelle.rxpermissions3.RxPermissions
import com.zbc.mvp.impl.BaseMvpActivity
import com.ztsc.commonutils.toast.ToastUtils
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.entity.bean.DemoBean
import com.ztsc.moudleuseguide.ui.base.addClick
import com.ztsc.moudleuseguide.ui.presenter.DemoPermistionPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.android.synthetic.main.activity_demo_logger.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import zbc.com.cn.utils.otherwise
import zbc.com.cn.utils.yes
import java.util.concurrent.TimeUnit


class DemoPermistionActivity : BaseMvpActivity<DemoPermistionPresenter>() {


    override fun initListener() {
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
                    ToastUtils.normal(data.msg!!)
                    AlertDialog.Builder(this)
                        .setMessage(data.msg!!)
                        .setPositiveButton("明白", { dialog, button -> getCameraPermission() })
                        .show()
                }

                1 -> {
                    ToastUtils.normal(data.msg!!)
                    AlertDialog.Builder(this)
                        .setMessage(data.msg!!)
                        .setPositiveButton("明白", { dialog, button -> getPermissionsWithResult() })
                        .show()
                }

                2 -> {
                    ToastUtils.normal(data.msg!!)
                    AlertDialog.Builder(this)
                        .setMessage(data.msg!!)
                        .setPositiveButton("明白", { dialog, button -> requestEachCombined() })
                        .show()
                }

                //实际测试info的打印仅支持第一个参数
                3 -> {
                    ToastUtils.normal(data.msg!!)
                    AlertDialog.Builder(this)
                        .setMessage(data.msg!!)
                        .setPositiveButton("明白", { dialog, button -> testEnsure(rl_back) })
                        .show()
                }

                4 -> {
                    ToastUtils.normal(data.msg!!)
                    AlertDialog.Builder(this)
                        .setMessage(data.msg!!)
                        .setPositiveButton("明白", { dialog, button -> testEnsureEach(rl_back) })
                        .show()
                }

                5 -> {
                    ToastUtils.normal(data.msg!!)
                    AlertDialog.Builder(this)
                        .setMessage(data.msg!!)
                        .setPositiveButton(
                            "明白",
                            { dialog, button -> testEnsureEachCombined(rl_back) })
                        .show()
                }


            }
        }
        val data = arrayListOf<DemoBean>()
        data.add(DemoBean(0, "单个权限申请,只返回成功与否（摄像头权限申请）", "权限申请"))
        data.add(DemoBean(1, "申请一组权限，并将权限的结果逐个返回", "权限申请"))
        data.add(DemoBean(2, "返回的权限名称:将多个权限名合并成一个; 返回的权限结果:全部同意时值true,否则值为false", "权限申请"))
        data.add(DemoBean(3, "ensure例子:  必须配合rxjava,回调结果与request一样", "权限申请"))
        data.add(DemoBean(4, "ensureEach例子: 必须配合rxjava,回调结果跟requestEach一样", "权限申请"))
        data.add(DemoBean(5, "ensureEachCombined例子: 必须配合rxjava,回调结果跟requestEachCombined一样", "权限申请"))

        mAdapter.setList(data)
    }

    override fun initData() {
        tv_title.text = "权限申请操作示例"
    }

    override fun getContentView(): Int {
        return R.layout.activity_demo_permistion
    }

    //传入的是下文对象如果是fragment请传入this，否则会抛出异常
    var rxPermissions = RxPermissions(this)
    fun getCameraPermission() {
        rxPermissions
            .request(Manifest.permission.CAMERA)
            .subscribe { granted ->
                //权限是否成功
                if (granted) { // Always true pre-M
                    // I can control the camera now
                    ToastUtils.normal("单个权限申请成功")
                } else {
                    // Oups permission denied
                    ToastUtils.normal("单个权限申请拒绝")
                }
            }
    }


    /**
     * 权限组
     */
    val permissionsGroup = arrayOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.READ_SMS,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )


    fun getPermissionsWithResult() {
        rxPermissions.requestEach(*permissionsGroup)
            .subscribe(object : Consumer<Permission> {
                @Throws(Exception::class)
                override fun accept(permission: Permission) {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        ToastUtils.normal("用户同意了权限${permission}")
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                        ToastUtils.normal("用户拒绝了权限${permission}")
                    } else {
                        // 用户拒绝了该权限，而且选中『不再询问』那么下次启动时，就不会提示出来了，
                        ToastUtils.normal("用户拒绝了权限${permission}，并勾选不再询问")
                    }
                }

            })
    }


    /**
     * requestEachCombined例子:
     * 返回的权限名称:将多个权限名合并成一个
     * 返回的权限结果:全部同意时值true,否则值为false
     */
    fun requestEachCombined() {
        val rxPermissions = RxPermissions(this)
        rxPermissions.requestEachCombined(*permissionsGroup)
            .subscribe(object : Consumer<Permission> {
                override fun accept(permission: Permission) {
                    //权限是否成功
                    if (permission.granted) { // Always true pre-M
                        // I can control the camera now
                        ToastUtils.normal("多个权限申请一起成功")
                    } else {
                        // Oups permission denied
                        ToastUtils.normal("多个权限申请一起拒绝")
                    }
                }
            })
    }


    /**
     * 配合按钮使用
     * ensure例子:
     * 必须配合rxjava,回调结果与request一样
     */
    fun testEnsure(view: View?) {
        val rxPermissions = RxPermissions(this)
        Observable.timer(10, TimeUnit.MILLISECONDS)
            .compose(rxPermissions.ensure(*permissionsGroup))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer<Boolean> { aBoolean ->
                aBoolean.yes {
                    ToastUtils.normal("权限申请成功}")
                }.otherwise {
                    ToastUtils.normal("权限申请失败}")
                }
            })
    }


    /**配合按钮使用
     * ensureEach例子:
     * 必须配合rxjava,回调结果跟requestEach一样
     */
    fun testEnsureEach(view: View?) {
        val rxPermissions = RxPermissions(this)
        //
        Observable.timer(10, TimeUnit.MILLISECONDS)
            .compose(rxPermissions.ensureEach(*permissionsGroup))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer<Permission> { permission ->
                ToastUtils.normal(
                    "权限名称:" + permission.name + ",申请结果:" + permission.granted
                )
            })
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
    }


    /**
     * ensureEachCombined例子:
     * 必须配合rxjava,回调结果跟requestEachCombined一样
     */
    fun testEnsureEachCombined(view: View?) {
        val rxPermissions = RxPermissions(this)
        Observable.timer(10, TimeUnit.MILLISECONDS)
            .compose(rxPermissions.ensureEachCombined(*permissionsGroup))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { permission ->
                ToastUtils.normal(
                    "权限名称:" + permission.name + ",申请结果:" + permission.granted
                )
            }
    }


//    fun showRationaleForCamera(request: PermissionRequest) {
//        AlertDialog.Builder(this)
//            .setMessage("摄像头权限")
//            .setPositiveButton("同意", { dialog, button -> request.proceed() })
//            .setNegativeButton("拒绝", { dialog, button -> request.cancel() })
//            .show()
//    }
//


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_back -> finishAct()
        }
    }
}