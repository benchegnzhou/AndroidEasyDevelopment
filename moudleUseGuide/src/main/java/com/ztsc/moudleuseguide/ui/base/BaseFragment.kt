package com.ztsc.commonuimoudle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import butterknife.Unbinder
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.ztsc.commonuimoudle.dialog.ADialog
import com.ztsc.house.IBaseViewUI
import com.ztsc.house.statistics.IUserBehaviorStatistics
import com.ztsc.moudleuseguide.helper.JGJanalyticsHelper
import com.ztsc.moudleuseguide.helper.JGJanalyticsHelper.onCountEvent
import org.jetbrains.annotations.NotNull


/**
 * Created by benchengzhou on 2017/4/10.
 * 作者邮箱：mappstore@163.com
 * 功能描述：
 * 备    注：
 * 将一些共用的东西集中放在base基类中
 * 方便后期修改维护
 * 避免子类代码冗余
 * 消除不必要的重复代码
 * 方便子类的使用
 */
abstract class BaseFragment : Fragment(), View.OnClickListener, View.OnLongClickListener,
    IBaseViewUI,
    IUserBehaviorStatistics {


    companion object {
        @JvmField
        val EXTRA_MSG = "extra_msg"
    }

    @JvmField
    var msg: String? = null

    /**
     * 贴附的activity
     */
    protected var mActivity: FragmentActivity? = null

    /**
     * 根view
     */
    protected lateinit var mRootView: View

    /**
     * 是否对用户可见
     */
    protected var mIsVisible = false

    /**
     * 是否加载完成
     * 当执行完oncreatview,View的初始化方法后方法后即为true
     */
    protected var mIsPrepare = false
    private var mStatusDialog: ADialog? = null


    private var mBind: Unbinder? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(getContentView(), container, false)
        //        在onCreate方法里，添加
//        mBind = ButterKnife.bind(this, mRootView)
        mIsPrepare = true //界面加载完成
        //onLazyLoad();
        return mRootView
    }


    /**
     * 布局控件加载完成
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initData()
    }

    /**
     * 刷新和加载的统一样式
     *
     * @param loadMoreListener
     * @param refreshLayout
     */
    protected open fun initRefreshStyle(
        @NonNull loadMoreListener: OnRefreshLoadMoreListener,
        @NonNull refreshLayout: SmartRefreshLayout
    ) {

        refreshLayout?.setRefreshHeader(ClassicsHeader(activity).setSpinnerStyle(SpinnerStyle.FixedBehind))
        refreshLayout?.setRefreshFooter(ClassicsFooter(activity).setSpinnerStyle(SpinnerStyle.FixedBehind))
        refreshLayout?.setOnRefreshLoadMoreListener(loadMoreListener)
    }

    /**
     * 在fragment中，我们可以通过getActivity()方法获取到当前依附的activity实例。
     * 但是如果在使用的时候直接获取有时候可能会报空指针，
     * 那么我们可以在fragment生命周期的onAttach(Context context)方法中获取到并提升为全局变量。
     *
     * @param context
     */
    override open fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = activity as FragmentActivity
    }


    override fun onDestroy() {
        super.onDestroy()
        mBind?.apply { unbind() }
    }

    /**
     * 设置布局资源文件
     *
     * @return
     */
    abstract override fun getContentView(): Int

    /**
     * 由子类实现完成
     * 各种监听事件和适配器操作的统一设置
     */
    abstract override fun initListener()

    /**
     * 懒加载，仅当用户可见切view初始化结束后才会执行
     * 用于网络和本地数据库数据的加载
     * 懒加载数据，在oncreatview方法中调用可以直接理解为加载数据，方法中可以进行view的操作
     * 何为懒加载，就是view没有与用户交互的话并不会加载，但是他的加载顺序又非常快。
     * 该方法主要在viewpager嵌套fragment中使用得多。
     * 我们都知道，viewpager可以提前加载左右指定数目
     * （当然这个数目可以通过setOffscreenPageLimit(int limit)设置）的fragment，
     * 如果我们使用懒加载，就只会做些view的创建等操作，避免提前执行其他页面的网络请求。
     */
    protected abstract fun onLazyLoad()

    /**
     * 数据初始化，主要包括拆包从activity传递过来的参数，适配器初始化，集合初始化等，不可进行view的操作
     */
    abstract override fun initData()


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mIsVisible = isVisibleToUser
        if (isVisibleToUser) {
            onVisibleToUser()
        }
    }

    /**
     * 用户可见时执行的操作
     *
     * @author 漆可
     * @date 2016-5-26 下午4:09:39
     */
    protected open fun onVisibleToUser() {
        if (mIsPrepare && mIsVisible) {
            onLazyLoad()
        }
    }

    protected open fun intent2Activity(tarActivity: Class<*>?) {
        val intent = Intent(activity, tarActivity)
        startActivity(intent)
    }


    /**
     * 提交样式，不带有文字的ProgressBar
     */
    open fun createProgressBar() {
        this.createProgressBar(null)
    }

    /**
     * 提交样式，带有文字的ProgressBar
     *
     * @param text 提示的文字内容
     */
    open fun createProgressBar(text: String?) {
//        if (uploadingDialog == null) {
//            uploadingDialog = text?.let { SubmitUploadingDialog(activity, it) }
//                ?: SubmitUploadingDialog(activity)
//            uploadingDialog!!.setCancelable(true)
//        }
//        if (!uploadingDialog!!.isShowing) {
//            uploadingDialog!!.show()
//        }
    }

    /**
     * 提交对话框隐藏
     */
    open fun disMissProgress() {
//        if (uploadingDialog != null && uploadingDialog!!.isShowing) {
//            uploadingDialog!!.dismiss()
//        }
    }


    /**
     * 获取一个和界面状态相关的Dialog
     */
    open fun getStatusDialog(): ADialog? {
        if (mStatusDialog == null) {
            mStatusDialog = ADialog(activity!!)
        }
        return mStatusDialog
    }


    override open fun onUserOpen(
        @NotNull context: Context,
        @NotNull eventId: JGJanalyticsHelper.CustomEventId,
        @Nullable pageName: String
    ) {
        onCountEvent(
            context, eventId.eventId
            , JGJanalyticsHelper.JGEvent()
                .addKeyValue(JGJanalyticsHelper.CustomKeyWord.PAGE_NAME.keyWordId, pageName + "")
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.PHONE_NUMBER.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.USER_ID.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.USER_DEPT_NAME.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.MACHINE_ID.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.MACHINE_NAME.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.MACHINE_VERSION_CODE.keyWordId,
                    "android_os_" + ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.APP_VERSION_CODE.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.APP_VERSION_NAME.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.APP_CHANNEL.keyWordId,
                    ""
                )
                .build()
        )
    }

    override open fun onUserOpen(
        @NotNull context: Context,
        @NotNull eventId: String,
        @Nullable pageName: String
    ) {
        onCountEvent(
            context, eventId
            , JGJanalyticsHelper.JGEvent()
                .addKeyValue(JGJanalyticsHelper.CustomKeyWord.PAGE_NAME.keyWordId, pageName + "")
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.PHONE_NUMBER.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.USER_ID.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.USER_DEPT_NAME.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.MACHINE_ID.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.MACHINE_NAME.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.MACHINE_VERSION_CODE.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.APP_VERSION_CODE.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.APP_VERSION_NAME.keyWordId,
                    ""
                )
                .addKeyValue(
                    JGJanalyticsHelper.CustomKeyWord.APP_CHANNEL.keyWordId,
                    ""
                )
                .build()
        )
    }


    override fun onUserOpen(
        context: Context,
        eventId: JGJanalyticsHelper.CustomEventId,
        pageName: String,
        jgEvent: JGJanalyticsHelper.JGEvent
    ) {
        onCountEvent(context, eventId.eventId, jgEvent.build())
    }





    open override fun startAct(clazz: Class<*>, bundle: Bundle?) {
        val initent = Intent(activity, clazz)
        bundle?.apply { initent.putExtras(bundle) }
        startActivity(initent)
    }

    open  override fun startAct(clazz: Class<*>, killSelf: Boolean, bundle: Bundle?) {
        val initent = Intent(activity, clazz)
        bundle?.apply { initent.putExtras(bundle) }
        startActivity(initent)
        if (killSelf) {
            finishAct()
        }
    }

    open override fun Bundle.put(key: String, value: Any?): Bundle {
        if (key.isEmpty() || value == null) {
            return this
        }
        when {
            value is String -> this.putSerializable(key, value)
            value is Short -> this.putShort(key, value)
            value is CharSequence -> this.putCharSequence(key, value)
            value is Int -> this.putInt(key, value)
            value is Boolean -> this.putBoolean(key, value)
            value is Float -> this.putFloat(key, value)
            value is Parcelable -> this.putParcelable(key, value)
        }
        return this
    }

    open override fun getBundle(): Bundle {
        return Bundle()
    }



    /**
     * 完成数据的初始化任务
     */
    //    protected abstract void initData();
    override fun startAct(clazz: Class<*>) {
        startActivity(Intent(activity, clazz))
    }


    override fun startAct(clazz: Class<*>, killSelf: Boolean) {
        startActivity(Intent(activity, clazz))
        if (killSelf) {
            finishAct()
        }
    }

    override fun startAct(bundle: Bundle?, clazz: Class<*>) {
        val intent = Intent(activity, clazz)
        intent.putExtras(bundle?:Bundle())
        startActivity(intent)
    }

    override fun startAct(bundle: Bundle?, clazz: Class<*>, killSelf: Boolean) {
        val intent = Intent(activity, clazz)
        intent.putExtras(bundle?:Bundle())
        startActivity(intent)
        if (killSelf) {
            finishAct()
        }
    }

    override fun startActForResult(clazz: Class<*>, requestCode: Int) {
        startActivityForResult(Intent(activity, clazz), requestCode)
    }

    override fun startActForResult(bundle: Bundle?, clazz: Class<*>, requestCode: Int) {
        val intent = Intent(activity, clazz)
        intent.putExtras(bundle?:Bundle())
        startActivityForResult(intent, requestCode)
    }


    override fun finishAct() {
        activity?.finish()
    }

    override fun finishActWithCode(resultCode: Int) {
        activity?.setResult(resultCode)
        finishAct()
    }

    override fun checkLogin(): Boolean {
        return false
    }


    /**
     * 打开一个activity
     */
    override fun startActAfterLogin() {}

    /**
     * fragment获取布局文件
     */
    override fun getInflateView(): Int {
        return 0
    }


    /**
     * Fragment 返回键被按下时回调
     */
    open fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 默认不拦截按键事件，回传给 Activity
        return false
    }


    override fun onLongClick(v: View?): Boolean {
        return false
    }
}