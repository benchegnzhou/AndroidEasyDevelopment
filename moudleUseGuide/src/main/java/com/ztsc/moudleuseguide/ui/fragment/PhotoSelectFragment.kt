package com.ztsc.moudleuseguide.ui.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.animators.AnimationType
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.language.LanguageConfig
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.luck.picture.lib.style.PictureCropParameterStyle
import com.luck.picture.lib.style.PictureParameterStyle
import com.luck.picture.lib.style.PictureWindowAnimationStyle
import com.tbruyelle.rxpermissions3.Permission
import com.tbruyelle.rxpermissions3.RxPermissions
import com.ztsc.commonuimoudle.BaseFragment
import com.ztsc.commonutils.logcat.LoggerUtil
import com.ztsc.commonutils.toast.ToastUtils
import com.ztsc.moudleuseguide.R
import com.ztsc.moudleuseguide.helper.GlideCacheEngine
import com.ztsc.moudleuseguide.helper.GlideEngine
import com.ztsc.moudleuseguide.ui.adapter.ImageAdapter
import com.ztsc.moudleuseguide.ui.base.addClick
import io.reactivex.rxjava3.functions.Consumer


import kotlinx.android.synthetic.main.fragment_photo_select.*
import java.lang.ref.WeakReference

import java.util.*

class PhotoSelectFragment : BaseFragment() {
    private val chooseMode = PictureMimeType.ofImage()
    private val themeId = R.style.picture_default_style
    private val isWeChatStyle = false
    private val maxSelectNum = 8
    private val animationMode = AnimationType.DEFAULT_ANIMATION
    private val aspect_ratio_x = 4
    private val aspect_ratio_y = 3


    override fun getContentView(): Int {
        return R.layout.fragment_photo_select
    }

    private lateinit var commonImageAdapter: ImageAdapter
    var localMedias: ArrayList<LocalMedia> =
        ArrayList<LocalMedia>() //用于存放选中后的照片

    private val localMediasShow: ArrayList<LocalMedia> =
        ArrayList<LocalMedia>() //用于存放选中后的照片如果小于最大张数会在最后拼接一个空白的栈位图


    private var mPhotoChangeCallBack: OnPhotoChangeCallBack? = null


    private var photoMaxEnable = 8 //默认可以选择8张

    private val messageHint: String? = null


    /**
     * 权限组
     */
    val permissionsGroup = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )


    companion object {
        @JvmStatic
        fun newInstance(msg: String?): PhotoSelectFragment {
            val f: PhotoSelectFragment = PhotoSelectFragment()
            val bundle = Bundle(1)
            bundle.putString(EXTRA_MSG, msg)
            f.setArguments(bundle)
            return f
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        msg = requireArguments().getString(EXTRA_MSG)
    }

    lateinit var rxPermissions: RxPermissions


    override fun onAttach(context: Context) {
        super.onAttach(context)
        //传入的是下文对象如果是fragment请传入this，否则会抛出异常
        rxPermissions = RxPermissions(this)
    }

    override fun initListener() {
        addClick(rlAddPhoto)
        rlAddPhoto.setOnClickListener(this)
//        rvPhotosSelect.setLayoutManager(new FullyGridLayoutManager(mContext, 4));
        //        rvPhotosSelect.setLayoutManager(new FullyGridLayoutManager(mContext, 4));
        rvPhotosSelect.setLayoutManager(GridLayoutManager(activity, 3))
        commonImageAdapter =
            ImageAdapter(R.layout.item_photo_gridview_layout, null)
        commonImageAdapter.animationEnable = true
        commonImageAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn)


        commonImageAdapter.setOnItemClickListener(OnItemClickListener { adapter, view, position ->
            if (position == localMedias.size) {
                cameraTask()
            }
        })
        commonImageAdapter.setOnItemChildClickListener(OnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.iv_delete) {
                localMedias.removeAt(position)
                transformPhotoShowListUpdataUi()
                if (mPhotoChangeCallBack != null) {  //数据变化回调
                    mPhotoChangeCallBack!!.onPhotoChange(localMedias)
                }
            }
        })
        rvPhotosSelect.setNestedScrollingEnabled(false)
        rvPhotosSelect.setAdapter(commonImageAdapter)

        //获取默认样式
        getDefaultStyle()
    }

    override fun onLazyLoad() {

    }

    override fun initData() {
        if (!TextUtils.isEmpty(messageHint)) {
            tvPhotoHint.setText(messageHint)
        }
        transformPhotoShowListUpdataUi()
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.rlAddPhoto -> cameraTask()
        }
    }


    fun cameraTask() {
        rxPermissions.requestEach(*permissionsGroup)
            .subscribe(object : Consumer<Permission> {
                @Throws(Exception::class)
                override fun accept(permission: Permission) {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        // ToastUtils.normal("用户同意了权限${permission}")
                        addNewPhotos()
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
     * 添加图片
     */
    private fun addNewPhotos() {

        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(this@PhotoSelectFragment)
            .openGallery(chooseMode) // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            .imageEngine(GlideEngine.createGlideEngine()) // 外部传入图片加载引擎，必传项
            .theme(themeId) // 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
            .isWeChatStyle(isWeChatStyle) // 是否开启微信图片选择风格
            .isUseCustomCamera(false) // 是否使用自定义相机
            .setLanguage(LanguageConfig.CHINESE) // 设置语言，默认中文
            .setPictureStyle(mPictureParameterStyle) // 动态自定义相册主题
            .setPictureCropStyle(mCropParameterStyle) // 动态自定义裁剪主题
            .setPictureWindowAnimationStyle(mWindowAnimationStyle) // 自定义相册启动退出动画
            .isWithVideoImage(true) // 图片和视频是否可以同选
            .maxSelectNum(maxSelectNum) // 最大图片选择数量
            //.minSelectNum(1)// 最小选择数量
            //.minVideoSelectNum(1)// 视频最小选择数量，如果没有单独设置的需求则可以不设置，同用minSelectNum字段
            .maxVideoSelectNum(1) // 视频最大选择数量，如果没有单独设置的需求则可以不设置，同用maxSelectNum字段
            .imageSpanCount(4) // 每行显示个数
            .isReturnEmpty(false) // 未选择数据时点击按钮是否可以返回
            //.isAndroidQTransform(false)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对.isCompress(false); && .isEnableCrop(false);有效,默认处理
            .loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine()) // 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) // 设置相册Activity方向，不设置默认使用系统
            .isOriginalImageControl(true) // 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
            //.cameraFileName("test.png")    // 重命名拍照文件名、注意这个只在使用相机时可以使用，如果使用相机又开启了压缩或裁剪 需要配合压缩和裁剪文件名api
            //.renameCompressFile("test.png")// 重命名压缩文件名、 注意这个不要重复，只适用于单张图压缩使用
            //.renameCropFileName("test.png")// 重命名裁剪文件名、 注意这个不要重复，只适用于单张图裁剪使用
            .selectionMode(if (true) PictureConfig.MULTIPLE else PictureConfig.SINGLE) // 多选 or 单选
            .isSingleDirectReturn(true) // 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
            .isPreviewImage(true) // 是否可预览图片
            .isPreviewVideo(false) // 是否可预览视频
            //.querySpecifiedFormatSuffix(PictureMimeType.ofJPEG())// 查询指定后缀格式资源
            .isEnablePreviewAudio(false) // 是否可播放音频
            .isCamera(true) // 是否显示拍照按钮
            //.isMultipleSkipCrop(false)// 多图裁剪时是否支持跳过，默认支持
            .isZoomAnim(true) // 图片列表点击 缩放效果 默认true
            //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
            .isEnableCrop(false) // 是否裁剪
            .isCompress(true) // 是否压缩
            .compressQuality(80) // 图片压缩后输出质量 0~ 100
            .synOrAsy(true) //同步false或异步true 压缩 默认同步
            //.queryMaxFileSize(10)// 只查多少M以内的图片、视频、音频  单位M
            //.compressSavePath(getPath())//压缩图片保存地址
            //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效 注：已废弃
            //.glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度 注：已废弃
            .withAspectRatio(aspect_ratio_x, aspect_ratio_y) // 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
            .hideBottomControls(false) // 是否显示uCrop工具栏，默认不显示
            .isGif(false) // 是否显示gif图片
            .freeStyleCropEnabled(true) // 裁剪框是否可拖拽
            .circleDimmedLayer(false) // 是否圆形裁剪
            //.setCircleDimmedColor(ContextCompat.getColor(this, R.color.app_color_white))// 设置圆形裁剪背景色值
            //.setCircleDimmedBorderColor(ContextCompat.getColor(getApplicationContext(), R.color.app_color_white))// 设置圆形裁剪边框色值
            //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
            .showCropFrame(true) // 是否显示裁剪矩形边框 圆形裁剪时建议设为false
            .showCropGrid(false) // 是否显示裁剪矩形网格 圆形裁剪时建议设为false
            .isOpenClickSound(false) // 是否开启点击声音
            .selectionData(localMedias) // 是否传入已选图片
            //.isDragFrame(false)// 是否可拖动裁剪框(固定)
            //.videoMaxSecond(15)
            //.videoMinSecond(10)
            .isPreviewEggs(true) // 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
            //.cropCompressQuality(90)// 注：已废弃 改用cutOutQuality()
            .cutOutQuality(90) // 裁剪输出质量 默认100
            .minimumCompressSize(300) // 小于100kb的图片不压缩
            //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
            //.rotateEnabled(true) // 裁剪是否可旋转图片
            //.scaleEnabled(true)// 裁剪是否可放大缩小图片
            //.videoQuality()// 视频录制质量 0 or 1
            //.recordVideoSecond()//录制视频秒数 默认60s
            //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径  注：已废弃
            //.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            .forResult(MyResultCallback(this))

    }


    fun notifiyDataChangeCallBack(){
        if (mPhotoChangeCallBack != null) {  //数据变化回调
            mPhotoChangeCallBack!!.onPhotoChange(localMedias)
        }
    }

    private var mPictureParameterStyle: PictureParameterStyle? = null
    private var mCropParameterStyle: PictureCropParameterStyle? = null
    private var mWindowAnimationStyle: PictureWindowAnimationStyle? = null
    private fun getDefaultStyle() {
        // 相册主题
        mPictureParameterStyle = PictureParameterStyle()
        // 是否改变状态栏字体颜色(黑白切换)
        mPictureParameterStyle?.isChangeStatusBarFontColor = false
        // 是否开启右下角已完成(0/9)风格
        mPictureParameterStyle?.isOpenCompletedNumStyle = false
        // 是否开启类似QQ相册带数字选择风格
        mPictureParameterStyle?.isOpenCheckNumStyle = false
        // 相册状态栏背景色
        mPictureParameterStyle?.pictureStatusBarColor = Color.parseColor("#393a3e")
        // 相册列表标题栏背景色
        mPictureParameterStyle?.pictureTitleBarBackgroundColor =
            Color.parseColor("#393a3e")
        // 相册父容器背景色
        mPictureParameterStyle?.pictureContainerBackgroundColor =
            ContextCompat.getColor(requireContext(), R.color.common_ui_black10)
        // 相册列表标题栏右侧上拉箭头
        mPictureParameterStyle?.pictureTitleUpResId = R.drawable.picture_icon_arrow_up
        // 相册列表标题栏右侧下拉箭头
        mPictureParameterStyle?.pictureTitleDownResId = R.drawable.picture_icon_arrow_down
        // 相册文件夹列表选中圆点
        mPictureParameterStyle?.pictureFolderCheckedDotStyle = R.drawable.picture_orange_oval
        // 相册返回箭头
        mPictureParameterStyle?.pictureLeftBackIcon = R.drawable.picture_icon_back
        // 标题栏字体颜色
        mPictureParameterStyle?.pictureTitleTextColor =
            ContextCompat.getColor(requireContext(), R.color.picture_color_white)
        // 相册右侧取消按钮字体颜色  废弃 改用.pictureRightDefaultTextColor和.pictureRightDefaultTextColor
        mPictureParameterStyle?.pictureCancelTextColor =
            ContextCompat.getColor(requireContext(), R.color.picture_color_white)
        // 选择相册目录背景样式
        mPictureParameterStyle?.pictureAlbumStyle = R.drawable.selector_submit_fail_dialog_btn
        // 相册列表勾选图片样式
        mPictureParameterStyle?.pictureCheckedStyle = R.drawable.picture_checkbox_selector
        // 相册列表底部背景色
        mPictureParameterStyle?.pictureBottomBgColor =
            ContextCompat.getColor(requireContext(), R.color.picture_color_grey)
        // 已选数量圆点背景样式
        mPictureParameterStyle?.pictureCheckNumBgStyle = R.drawable.picture_num_oval
        // 相册列表底下预览文字色值(预览按钮可点击时的色值)
        mPictureParameterStyle?.picturePreviewTextColor =
            ContextCompat.getColor(requireContext(), R.color.picture_color_fa632d)
        // 相册列表底下不可预览文字色值(预览按钮不可点击时的色值)
        mPictureParameterStyle?.pictureUnPreviewTextColor =
            ContextCompat.getColor(requireContext(), R.color.picture_color_white)
        // 相册列表已完成色值(已完成 可点击色值)
        mPictureParameterStyle?.pictureCompleteTextColor =
            ContextCompat.getColor(requireContext(), R.color.picture_color_fa632d)
        // 相册列表未完成色值(请选择 不可点击色值)
        mPictureParameterStyle?.pictureUnCompleteTextColor =
            ContextCompat.getColor(requireContext(), R.color.picture_color_white)
        // 预览界面底部背景色
        mPictureParameterStyle?.picturePreviewBottomBgColor =
            ContextCompat.getColor(requireContext(), R.color.picture_color_grey)
        // 外部预览界面删除按钮样式
        mPictureParameterStyle?.pictureExternalPreviewDeleteStyle = R.drawable.picture_icon_delete
        // 原图按钮勾选样式  需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle?.pictureOriginalControlStyle =
            R.drawable.picture_original_wechat_checkbox
        // 原图文字颜色 需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle?.pictureOriginalFontColor =
            ContextCompat.getColor(requireContext(), R.color.common_ui_white)
        // 外部预览界面是否显示删除按钮
        mPictureParameterStyle?.pictureExternalPreviewGonePreviewDelete = true
        // 设置NavBar Color SDK Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP有效
        mPictureParameterStyle?.pictureNavBarColor = Color.parseColor("#393a3e")
        //        // 自定义相册右侧文本内容设置
//        mPictureParameterStyle.pictureRightDefaultText = "";
//        // 自定义相册未完成文本内容
//        mPictureParameterStyle.pictureUnCompleteText = "";
//        // 自定义相册完成文本内容
//        mPictureParameterStyle.pictureCompleteText = "";
//        // 自定义相册列表不可预览文字
//        mPictureParameterStyle.pictureUnPreviewText = "";
//        // 自定义相册列表预览文字
//        mPictureParameterStyle.picturePreviewText = "";
//
//        // 自定义相册标题字体大小
//        mPictureParameterStyle.pictureTitleTextSize = 18;
//        // 自定义相册右侧文字大小
//        mPictureParameterStyle.pictureRightTextSize = 14;
//        // 自定义相册预览文字大小
//        mPictureParameterStyle.picturePreviewTextSize = 14;
//        // 自定义相册完成文字大小
//        mPictureParameterStyle.pictureCompleteTextSize = 14;
//        // 自定义原图文字大小
//        mPictureParameterStyle.pictureOriginalTextSize = 14;

        // 裁剪主题
        mCropParameterStyle = PictureCropParameterStyle(
            ContextCompat.getColor(requireContext(), R.color.colorAccent),
            ContextCompat.getColor(requireContext(), R.color.colorAccent),
            Color.parseColor("#393a3e"),
            ContextCompat.getColor(requireContext(), R.color.colorAccent),
//            mPictureParameterStyle?.isChangeStatusBarFontColor
            false
        )
    }

    /**
     * 图片数目改变监听
     *
     * @param callBack
     */
    fun setOnPhoneChangCallBack(callBack: OnPhotoChangeCallBack) {
        mPhotoChangeCallBack = callBack
    }


    /**
     * 设置每次可选图片的最大数目
     * 每次设置的图片的数目不可以小于0
     *
     * @param photoCount
     */
    fun setPhotoSelectMax(photoCount: Int) {
        if (photoCount <= 0) {
            throw RuntimeException("the value of photoCount can't below zone!")
        }
        photoMaxEnable = photoCount
    }


    /**
     * 获取可以选择的图片的最大数目
     *
     * @return
     */
    fun getPhotoSelectMax(): Int {
        return photoMaxEnable
    }


    /**
     * 权限
     *
     * @param s
     */
    private fun showFailDialog(s: String) {
        if ("android.permission.CAMERA" == s) {
            val builder =
                AlertDialog.Builder(activity)
            // 设置参数
            builder.setTitle("请允许获取图库使用权限")
                .setMessage("我们需要获取手机相册，以帮助您完成照片选择；否则您将无法正常使用此功能")
                .setPositiveButton(
                    "确定"
                ) { dialog, which -> cameraTask() }
            builder.setCancelable(false).create().show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


    //内部调用的方法

    //内部调用的方法
    /**
     * 将会图片的数据转换成显示的样式
     */
    private fun transformPhotoShowList() {
        rlAddPhoto.setVisibility(View.GONE)
        if (localMedias == null) {
            localMedias = ArrayList()
        }
        if (localMedias.size == 0) {
            rlAddPhoto.setVisibility(View.VISIBLE)
            localMediasShow.clear()
        } else if (localMedias.size < photoMaxEnable) {  //小于最大的张数
            localMediasShow.clear()
            localMediasShow.addAll(localMedias)
            localMediasShow.add(LocalMedia())
        } else {
            localMediasShow.clear()
            localMediasShow.addAll(localMedias)
        }

        //图片回传
        if (mPhotoChangeCallBack != null) {
            mPhotoChangeCallBack?.onPhotoChange(localMedias)
        }
    }

    /**
     * 数据转换，界面刷新
     */
    fun transformPhotoShowListUpdataUi() {
        transformPhotoShowList()
        commonImageAdapter.setList(localMediasShow)
    }

}


/**
 * 图片选择回调
 */
open interface OnPhotoChangeCallBack {
    fun onPhotoChange(localMedias: ArrayList<LocalMedia>?)
}


/**
 * 返回结果回调
 */
private class MyResultCallback(photoSelectFragment: PhotoSelectFragment) :
    OnResultCallbackListener<LocalMedia> {
    private val mAdapterWeakReference: WeakReference<PhotoSelectFragment?>

    override fun onResult(result: List<LocalMedia>) {
        for (media in result) {
            LoggerUtil.i("是否压缩:" + media.isCompressed)
            LoggerUtil.i("压缩:" + media.compressPath)
            LoggerUtil.i("原图:" + media.path)
            LoggerUtil.i("是否裁剪:" + media.isCut)
            LoggerUtil.i("裁剪:" + media.cutPath)
            LoggerUtil.i("是否开启原图:" + media.isOriginal)
            LoggerUtil.i("原图路径:" + media.originalPath)
            LoggerUtil.i("Android Q 特有Path:" + media.androidQToPath)
            LoggerUtil.i("宽高: " + media.width + "x" + media.height)
            LoggerUtil.i("Size: " + media.size)
            // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
        }
        if (mAdapterWeakReference.get() != null) {
            val photoSelectFragment: PhotoSelectFragment? = mAdapterWeakReference.get()
            photoSelectFragment?.localMedias = result as ArrayList<LocalMedia>
            photoSelectFragment?.transformPhotoShowListUpdataUi()
            photoSelectFragment?.notifiyDataChangeCallBack()
        }
    }

    override fun onCancel() {
        LoggerUtil.i("PictureSelector Cancel")
    }

    init {
        mAdapterWeakReference = WeakReference<PhotoSelectFragment?>(photoSelectFragment)
    }
}