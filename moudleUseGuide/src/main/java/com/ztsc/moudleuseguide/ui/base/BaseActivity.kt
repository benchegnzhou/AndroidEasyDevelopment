package com.ztsc.house

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.lzy.okgo.OkGo
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.ztsc.commonuimoudle.dialog.ADialog
import com.ztsc.moudleuseguide.R
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.Utils
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper
import org.greenrobot.eventbus.EventBus
import java.io.IOException

abstract class BaseActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener,
    SwipeBackActivityBase, IBaseViewUI {
    private var mHelper: SwipeBackActivityHelper? = null

    private lateinit var mStatusDialog: ADialog

    companion object {
        @JvmField
        var isLoadMore: Boolean = false

        @JvmField
        var PAGE_COUNT: Int = 30

        @JvmField
        var pageNum: Int = 1
    }


    /*open public var isLoadMore: Boolean = false
        get() = field
        set(value) {
            field = value
        }
    open public var PAGE_COUNT: Int = 30
        get() = field
        set(value) {
            field = value
        }

    open public var pageNum: Int = 1
        get() = field
        set(value) {
            field = value
        }*/

    /**
     * 翻页
     */
    fun pageNumAdd() {
        pageNum = pageNum + 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
//        initSwipeBackFinish();
        super.onCreate(savedInstanceState)
        initAct()
    }

    fun initAct() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //锁定竖屏
        othersInit()
        setContentView(getContentView())
        mStatusDialog = ADialog(this)
        setStatusBar()
        ButterKnife.bind(this)
        mHelper = SwipeBackActivityHelper(this)
        mHelper?.onActivityCreate()
        initListener()
        initData()
    }


    /**
     * 状态栏设置
     * 子类可以重写这个方法设置状态栏
     */
    protected open fun setStatusBar() {

    }

    /**
     * 由子类实现并且返回对应的view
     *
     * @return
     */
    abstract override fun getContentView(): Int


    open fun othersInit() {}


    /**
     * 刷新和加载的统一样式
     *
     * @param loadMoreListener
     * @param mRefreshLayout
     */
    protected fun initRefreshStyle(
        loadMoreListener: OnRefreshLoadMoreListener,
        mRefreshLayout: SmartRefreshLayout
    ) {
        mRefreshLayout?.setRefreshHeader(ClassicsHeader(this))
        mRefreshLayout?.setRefreshFooter(ClassicsFooter(this))
        mRefreshLayout?.setOnRefreshLoadMoreListener(loadMoreListener)
    }



    open override fun startAct(clazz: Class<*>, bundle: Bundle?) {
        val initent = Intent(this, clazz)
        bundle?.apply { initent.putExtras(bundle) }
        startActivity(initent)
    }

    open  override fun startAct(clazz: Class<*>, killSelf: Boolean, bundle: Bundle?) {
        val initent = Intent(this, clazz)
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
        startActivity(Intent(this, clazz))
    }


    override fun startAct(clazz: Class<*>, killSelf: Boolean) {
        startActivity(Intent(this, clazz))
        if (killSelf) {
            finishAct()
        }
    }

    override fun startAct(bundle: Bundle?, clazz: Class<*>) {
        val intent = Intent(this, clazz)
        intent.putExtras(bundle?:Bundle())
        startActivity(intent)
        finishAct()
    }

    override fun startAct(bundle: Bundle?, clazz: Class<*>, killSelf: Boolean) {
        val intent = Intent(this, clazz)
        intent.putExtras(bundle?:Bundle())
        startActivity(intent)
        if (killSelf) {
            finishAct()
        }
    }

    override fun startActForResult(clazz: Class<*>, requestCode: Int) {
        startActivityForResult(Intent(this, clazz), requestCode)
    }

    override fun startActForResult(bundle: Bundle?, clazz: Class<*>, requestCode: Int) {
        val intent = Intent(this, clazz)
        intent.putExtras(bundle?:Bundle())
        startActivityForResult(intent, requestCode)
    }


    override fun finishAct() {
        finish()
    }

    override fun finishActWithCode(resultCode: Int) {
        setResult(resultCode)
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
     * 由子类实现并且返回对应的完成监听操作和控件adapter的设置
     */
    abstract override fun initListener()

    /**
     * 用于子类的数据的初始化
     */
    abstract override fun initData()


    override fun onDestroy() {
        super.onDestroy()
        recycleEventBus()
        hideSoftKeyboard()
        try {
            //根据 Tag 取消请求
            OkGo.getInstance().cancelTag(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun recycleEventBus() {
        EventBus.getDefault().unregister(this)
    }

    protected fun intent2Activity(tarActivity: Class<*>?) {
        val intent = Intent(this, tarActivity)
        startActivity(intent)
    }

    protected fun finishActivity() {
        finish()
    }


    /**
     * 1、获取main在窗体的可视区域
     * 2、获取main在窗体的不可视区域高度
     * 3、判断不可视区域高度
     * 1、大于100：键盘显示  获取Scroll的窗体坐标
     * 算出main需要滚动的高度，使scroll显示。
     * 2、小于100：键盘隐藏
     *
     * @param mView  根布局
     * @param scroll 需要显示的最下方View
     */
    fun addLayoutListener(mView: View, scroll: View) {
        mView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            mView.getWindowVisibleDisplayFrame(rect)
            val mainInvisibleHeight = mView.rootView.height - rect.bottom
            if (mainInvisibleHeight > 100) {
                val location = IntArray(2)
                scroll.getLocationInWindow(location)
                val srollHeight = location[1] + scroll.height - rect.bottom
                mView.scrollTo(0, srollHeight)
            } else {
                mView.scrollTo(0, 0)
            }
        }
    }

    /*

     */
    /**
     * 提交样式，不带有文字的ProgressBar
     *//*

    public void createProgressBar() {
        this.createProgressBar(null);
    }

    */
    /**
     * 提交样式，带有文字的ProgressBar
     *
     * @param text 提示的文字内容
     *//*

    public void createProgressBar(String text) {

        if (uploadingDialog == null) {
            if (text == null) {
                uploadingDialog = new SubmitUploadingDialog(this);
            } else {
                uploadingDialog = new SubmitUploadingDialog(this, text);
            }
            uploadingDialog.setCancelable(true);
        }
        if (!uploadingDialog.isShowing()) {
            uploadingDialog.show();
        }
    }
*/


    /*

     */
    /**
     * 提交样式，不带有文字的ProgressBar
     */
    /*

    public void createProgressBar() {
        this.createProgressBar(null);
    }

    */
    /**
     * 提交样式，带有文字的ProgressBar
     *
     * @param text 提示的文字内容
     */
    /*

    public void createProgressBar(String text) {

        if (uploadingDialog == null) {
            if (text == null) {
                uploadingDialog = new SubmitUploadingDialog(this);
            } else {
                uploadingDialog = new SubmitUploadingDialog(this, text);
            }
            uploadingDialog.setCancelable(true);
        }
        if (!uploadingDialog.isShowing()) {
            uploadingDialog.show();
        }
    }
*/

    /**
     * 隐藏软键盘
     */
    open fun hideSoftKeyboard() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        val view = currentFocus
        if (view != null) {
            val manager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (manager != null && manager.isActive(view)) {
                manager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    /**
     * 内存垃圾释放
     */
    fun gcAndFinalize() {
        val runtime = Runtime.getRuntime()
        System.gc()
        runtime.runFinalization()
        System.gc()
    }


    /**
     * 获取一个和界面状态相关的Dialog
     */
    fun getStatusDialog(): ADialog {
        try {
            if (mStatusDialog == null) {
                mStatusDialog = ADialog(this)
            }
        } catch (e: java.lang.Exception) {
            mStatusDialog = ADialog(this)
        }

        return mStatusDialog
    }


    /**
     * 加载等待和上传等待界面统一样式
     */
    protected fun createLoadingDialog() {
        getStatusDialog().showDialog().syncLoadingDialog("加载中...")
    }

    /**
     * 加载等待和上传等待界面统一样式
     *
     * @param loadingMessage
     */
    protected fun createLoadingDialog(loadingMessage: String?) {
        getStatusDialog().showDialog().syncLoadingDialog(loadingMessage ?: "")
    }


    /**
     * 状态dialog隐藏
     */
    protected fun dissmissLoadingDialog() {
        try {
            getStatusDialog().dissMissDialog()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * 编辑界面退出询问
     */
    protected fun quitEditUI() {
        getStatusDialog().showDialog()
            .SetOnDoubuleOptionClickCallBack(object : ADialog.onDoubleOptionClickCallBack {
                override fun onDoubleLeftCallback(): Boolean {
                    finish()
                    return true
                }

                override fun onDoubleRightCallback(): Boolean {
                    return true
                }
            }).syncDoubleOptionDialog("你要放弃本次编辑么？", "去意已决", "取消")
    }


    /**
     * 提交失败的风格和样式
     * 同时可用于确认信息
     *
     * @param messge
     */
    protected fun showSingleBtnSuccessfulDialog(messge: String?) {
        getStatusDialog().showDialog()
            .SetOnSingleSuccessOptionClickCallBack(object : ADialog.onSingleOptionClickCallBack {
                override fun onSingleCallback(): Boolean {
                    return true
                }
            })
            .syncSingleOptionSuccessDialog(messge ?: "", "确定")
    }


    /**
     * 提交失败的风格和样式
     * 同时可用于确认信息
     *
     * @param messge
     * @param finishEnable 是否退出界面
     */
    protected open fun showSingleBtnSuccessfulDialog(messge: String?, finishEnable: Boolean) {
        getStatusDialog().showDialog()
            .SetOnSingleSuccessOptionClickCallBack(object : ADialog.onSingleOptionClickCallBack {
                override fun onSingleCallback(): Boolean {
                    if (finishEnable) {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                    return true
                }

            }).syncSingleOptionSuccessDialog(messge ?: "", "确定")
    }

    /**
     * 数据验证失败样式弹框
     * 同时可用于确认信息
     *
     * @param messge
     */
    protected fun showSingleBtnFailDialog(messge: String?) {
        showSingleBtnFailDialog(messge, "确定", false)
    }

    /**
     * 数据验证失败样式弹框
     * 同时可用于确认信息
     *
     * @param messge
     * @param finishEnable 是否退出界面
     */
    protected fun showSingleBtnFailDialog(messge: String?, finishEnable: Boolean) {
        showSingleBtnFailDialog(messge, "确定", finishEnable)
    }

    /**
     * 数据验证失败样式弹框
     * 同时可用于确认信息
     *
     * @param messge
     * @param btnName      btn名称
     * @param finishEnable 是否退出界面
     */
    protected fun showSingleBtnFailDialog(
        messge: String?,
        btnName: String?,
        finishEnable: Boolean
    ) {
        getStatusDialog().showDialog()
            .SetOnSingleFailOptionClickCallBack(object : ADialog.onSingleOptionClickCallBack {
                override fun onSingleCallback(): Boolean {
                    if (finishEnable) {
                        finish()
                    }
                    return true
                }

            }).syncSingleOptionFailDialog(messge ?: "", "确定")
    }


    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    fun isSupportSwipeBack(): Boolean {
        return true
    }


    /* @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }*/

    //------------------------------------------------------------------------------------------------------------------


    /* @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }*/
    //------------------------------------------------------------------------------------------------------------------
    /**
     * 启用系统的震动。
     */
//    fun vibrate() {
//        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        vibrator.vibrate(200) //设置震动的时间
//    }


    /**
     * 播放音效
     */
//    fun playAudio() {
//        直接创建，不需要设置setDataSource
//        val mMediaPlayer: MediaPlayer
//        mMediaPlayer = MediaPlayer.create(this, R.raw.wechatscanner)
//        mMediaPlayer.start()
//    }


    /****************提示声音 震动  发送成功之后显示 */
    private var mediaPlayer: MediaPlayer? = null
    private var playBeep = false
    private val BEEP_VOLUME = 0.10f
    private var vibrate = false

    private fun initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            volumeControlStream = AudioManager.STREAM_MUSIC
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer!!.setOnCompletionListener(beepListener)
            val file = resources.openRawResourceFd(R.raw.wechatscanner)
            try {
                mediaPlayer!!.setDataSource(file.fileDescriptor, file.startOffset, file.length)
                file.close()
                mediaPlayer!!.setVolume(BEEP_VOLUME, BEEP_VOLUME)
                mediaPlayer!!.prepare()
            } catch (e: IOException) {
                mediaPlayer = null
            }
        }
    }

    private val beepListener = OnCompletionListener { mediaPlayer -> mediaPlayer.seekTo(0) }

    private val VIBRATE_DURATION = 200L

/*
    private fun playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer!!.start()
        }
        if (vibrate) {
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(VIBRATE_DURATION)
        }
    }*/


    private fun initVoice() {
        // TODO Auto-generated method stub
        playBeep = true
        val audioService = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (audioService.ringerMode != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false
        }
        initBeepSound()
        vibrate = true
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


    /*---------------------下面代码和侧滑返回有关-----------------------------*/
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper?.onPostCreate()
    }

    override fun getSwipeBackLayout(): SwipeBackLayout? {
        return mHelper?.swipeBackLayout
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        swipeBackLayout?.setEnableGesture(enable)
    }

    override fun scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this)
        swipeBackLayout?.scrollToFinishActivity()
    }


    override fun onLongClick(v: View?): Boolean {
        return false
    }

    open fun <T : View?> `$`(id: Int): T {
        return super.findViewById<View>(id) as T
    }


}