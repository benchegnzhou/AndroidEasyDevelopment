package com.ztsc.moudleuseguide.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.luck.picture.lib.entity.LocalMedia
import com.ztsc.moudleuseguide.R
import java.io.File
import java.util.*

/**
 * Created by benchengzhou on 2020/10/10  14:08 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名：
 * 备    注：
 */

class ImageAdapter :
    BaseQuickAdapter<LocalMedia, BaseViewHolder> {
    //图片数组
    private var localMedias: ArrayList<LocalMedia>? = null

    constructor(layoutResId: Int, data: MutableList<LocalMedia>?) : super(layoutResId, data)


    /**
     * 更新图片资源
     *
     * @param localMedias
     */
    fun setImagesList(localMedias: ArrayList<LocalMedia>?) {
        this.localMedias = localMedias
        notifyDataSetChanged()

    }


    override fun convert(helper: BaseViewHolder, item: LocalMedia) {
        val RlPhotoSelect = helper.getView<RelativeLayout>(R.id.rl_photo_select)
        val RlHolderView = helper.getView<RelativeLayout>(R.id.rl_holder_view)
        addChildClickViewIds(R.id.iv_delete)
        if (item == null || TextUtils.isEmpty(item.path)) { //展位图
            RlPhotoSelect.visibility = View.GONE
            RlHolderView.visibility = View.VISIBLE
        } else {
            RlPhotoSelect.visibility = View.VISIBLE
            RlHolderView.visibility = View.GONE
            //圆角图片的加载
            Glide.with(context)
                .load(File(item.realPath))
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.banner_default)
                        .error(R.drawable.banner_default)
                )
                .into(helper.getView<View>(R.id.iv_pic) as ImageView)
        }

    }

}