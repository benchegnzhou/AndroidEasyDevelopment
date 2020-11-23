package com.ztsc.commonutils.toast


import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.annotation.IntRange
import com.example.commonstatusbarlib.R
import com.ztsc.commonutils.CommonUtil
import es.dmoral.toasty.Toasty
import org.jetbrains.annotations.NotNull


class ToastUtils {

    companion object {


        //----------------------正常------------------

        @JvmStatic
        fun normal(
            @NonNull message: CharSequence
        ) {
            Toasty
                .normal(
                    CommonUtil.getInstance().applicationContext
                    , message
                    , Toasty.LENGTH_SHORT
                    , null
                    , false
                ).show()
        }


        @JvmStatic
        fun normal(
            @NonNull message: CharSequence,
            @DrawableRes iconId: Int = -1,
            @IntRange(from = 0, to = 1) timeType: Int = 0
        ) {
            Toasty.normal(
                CommonUtil.getInstance().applicationContext
                , message
                , if (timeType == 0) Toasty.LENGTH_SHORT else Toasty.LENGTH_LONG
                , if (iconId == -1) null!! else getDrawableFormRes(iconId)
                , iconId != -1
            ).show()
        }

        @JvmStatic
        fun normal(
            @NonNull message: CharSequence,
            @Nullable icon: Drawable? = null,
            @IntRange(from = 0, to = 1) timeType: Int = 0
        ) {
            Toasty.normal(
                CommonUtil.getInstance().applicationContext
                , message
                , if (timeType == 0) Toasty.LENGTH_SHORT else Toasty.LENGTH_LONG
                , icon
                , icon != null
            ).show()
        }

        /**
         * 正常提示短吐司持续1-2秒，带有图标
         */
        @JvmStatic
        fun normalShortWithIcon(@NotNull message: CharSequence, @NotNull icon: Drawable) {
            Toasty.normal(
                CommonUtil.getInstance().applicationContext
                , message, Toasty.LENGTH_SHORT, icon, icon != null
            ).show()
        }

        /**
         * 正常提示短吐司持续1-2秒，无图标
         */
        @JvmStatic
        fun normalShortWithoutIcon(@NotNull message: CharSequence) {
            Toasty.normal(
                CommonUtil.getInstance().applicationContext
                , message, Toasty.LENGTH_SHORT, null, false
            ).show()
        }

        /**
         * 正常提示长吐司持续3-4秒，带有图标
         */
        @JvmStatic
        fun normalLongWithIcon(@NotNull message: CharSequence, @NotNull icon: Drawable) {
            Toasty.normal(
                CommonUtil.getInstance().applicationContext
                , message, Toasty.LENGTH_LONG, icon, true
            ).show()
        }

        /**
         * 正常提示长吐司持续3-4秒，无图标
         */
        @JvmStatic
        fun normalLongWithoutIcon(@NotNull message: CharSequence) {
            Toasty.normal(
                CommonUtil.getInstance().applicationContext
                , message, Toasty.LENGTH_LONG, null, false
            ).show()
        }


        //-----------------------------------错误--------------------
        @JvmStatic
        fun error(
            @StringRes message: Int,
            @IntRange(from = 0, to = 1) timeType: Int = 0,
            withIcon: Boolean = false
        ) {
            Toasty.error(
                CommonUtil.getInstance().applicationContext
                , message
                , if (timeType == 0) Toasty.LENGTH_SHORT else Toasty.LENGTH_LONG
                , withIcon
            ).show()
        }

        /**
         * 错误提示短吐司持续1-2秒，带有图标
         */
        @JvmStatic
        fun errorShortWithIcon(msg: Int) {
            Toasty.error(
                CommonUtil.getInstance().applicationContext
                , msg, Toasty.LENGTH_SHORT, true
            ).show()
        }

        /**
         * 错误提示短吐司持续1-2秒，无图标
         */
        @JvmStatic
        fun errorShortWithoutIcon(msg: Int) {
            Toasty.error(
                CommonUtil.getInstance().applicationContext
                , msg, Toasty.LENGTH_SHORT, false
            ).show()
        }

        /**
         * 错误提示长吐司持续3-4秒，带有图标
         */
        @JvmStatic
        fun errorLongWithIcon(msg: Int) {
            Toasty.error(
                CommonUtil.getInstance().applicationContext
                , msg, Toasty.LENGTH_LONG, true
            ).show()
        }

        /**
         * 错误提示长吐司持续3-4秒，无图标
         */
        @JvmStatic
        fun errorLongWithoutIcon(@StringRes message: Int) {
            Toasty.error(
                CommonUtil.getInstance().applicationContext
                , message, Toasty.LENGTH_LONG, false
            ).show()
        }

        @JvmStatic
        fun error(
            @NotNull msg: CharSequence,
            @IntRange(from = 0, to = 1) timeType: Int = 0,
            withIcon: Boolean = false
        ) {
            Toasty.error(
                CommonUtil.getInstance().applicationContext
                , msg
                , if (timeType == 0) Toasty.LENGTH_SHORT else Toasty.LENGTH_LONG
                , withIcon
            ).show()
        }

        /**
         * 错误提示短吐司持续1-2秒，带有图标
         */
        @JvmStatic
        fun errorShortWithIcon(@NotNull msg: CharSequence) {
            Toasty.error(
                CommonUtil.getInstance().applicationContext
                , msg, Toasty.LENGTH_SHORT, true
            ).show()
        }

        /**
         * 错误提示短吐司持续1-2秒，无图标
         */
        @JvmStatic
        fun errorShortWithoutIcon(@NotNull msg: CharSequence) {
            Toasty.error(
                CommonUtil.getInstance().applicationContext
                , msg, Toasty.LENGTH_SHORT, false
            ).show()
        }

        /**
         * 错误提示长吐司持续3-4秒，带有图标
         */
        @JvmStatic
        fun errorLongWithIcon(@NotNull msg: CharSequence) {
            Toasty.error(
                CommonUtil.getInstance().applicationContext
                , msg, Toasty.LENGTH_LONG, true
            ).show()
        }

        /**
         * 错误提示长吐司持续3-4秒，无图标
         */
        @JvmStatic
        fun errorLongWithoutIcon(@NotNull msg: CharSequence) {
            Toasty.error(
                CommonUtil.getInstance().applicationContext
                , msg, Toasty.LENGTH_LONG, false
            ).show()
        }

        //-----------------------------成功样式的toast----------------------------

        @JvmStatic
        fun success(@NotNull message: CharSequence, @IntRange(from = 0, to = 1) timeType: Int = 0) {
            Toasty.success(
                CommonUtil.getInstance().applicationContext,
                message,
                if (timeType == 0) Toasty.LENGTH_SHORT else Toasty.LENGTH_LONG,
                true
            ).show()
        }

        @JvmStatic
        fun success(@StringRes message: Int, @IntRange(from = 0, to = 1) timeType: Int = 0) {
            Toasty.success(
                CommonUtil.getInstance().applicationContext,
                message,
                if (timeType == 0) Toasty.LENGTH_SHORT else Toasty.LENGTH_LONG,
                true
            ).show()
        }


        //----------------提示信息信息提示样式的toast（蓝底叹号图标）----------

        @JvmStatic
        fun info(@NotNull message: CharSequence, @IntRange(from = 0, to = 1) timeType: Int = 0) {
            Toasty.info(
                CommonUtil.getInstance().applicationContext,
                message,
                if (timeType == 0) Toasty.LENGTH_SHORT else Toasty.LENGTH_LONG,
                true
            ).show()
        }

        @JvmStatic
        fun info(@StringRes message: Int, @IntRange(from = 0, to = 1) timeType: Int = 0) {
            Toasty.info(
                CommonUtil.getInstance().applicationContext,
                message,
                if (timeType == 0) Toasty.LENGTH_SHORT else Toasty.LENGTH_LONG,
                true
            ).show()
        }

        //----------------警示信息提示样式的toast（黄底叹号图标）----------

        @JvmStatic
        fun warning(@NotNull message: CharSequence, @IntRange(from = 0, to = 1) timeType: Int = 0) {
            Toasty.warning(
                CommonUtil.getInstance().applicationContext,
                message,
                if (timeType == 0) Toasty.LENGTH_SHORT else Toasty.LENGTH_LONG,
                true
            ).show()
        }

        @JvmStatic
        fun warning(@StringRes message: Int, @IntRange(from = 0, to = 1) timeType: Int = 0) {
            Toasty.warning(
                CommonUtil.getInstance().applicationContext,
                message,
                if (timeType == 0) Toasty.LENGTH_SHORT else Toasty.LENGTH_LONG,
                true
            ).show()
        }


        //----------------自定义样式的toast ----------

        @JvmStatic
        fun custom(@NotNull message: CharSequence, @IntRange(from = 0, to = 1) timeType: Int = 0) {
            Toasty.Config.getInstance()
                .setToastTypeface(
                    Typeface.createFromAsset(
                        CommonUtil.getInstance().applicationContext.getAssets(),
                        "PCap Terminal.otf"
                    )
                )
                .allowQueue(false)
                .apply()
            Toasty.custom(
                CommonUtil.getInstance().applicationContext,
                message,
                CommonUtil.getInstance().applicationContext.getResources()
                    .getDrawable(R.drawable.laptop512),
                getColorFormRes(R.color.black_common_util),
                getColorFormRes(R.color.holo_green_light),
                Toasty.LENGTH_SHORT,
                true,
                true
            ).show()
            Toasty.Config.reset() // Use this if you want to use the configuration above only once

        }

        @JvmStatic
        fun custom(@StringRes message: Int, @IntRange(from = 0, to = 1) timeType: Int = 0) {
            Toasty.Config.getInstance()
                .setToastTypeface(
                    Typeface.createFromAsset(
                        CommonUtil.getInstance().applicationContext.getAssets(),
                        "PCap Terminal.otf"
                    )
                )
                .allowQueue(false)
                .apply()
            Toasty.custom(
                CommonUtil.getInstance().applicationContext,
                message,
                CommonUtil.getInstance().applicationContext.getResources()
                    .getDrawable(R.drawable.laptop512),
                R.color.black_common_util,
                R.color.holo_green_light,
                Toasty.LENGTH_SHORT,
                true,
                true
            ).show()
            Toasty.Config.reset() // Use this if you want to use the configuration above only once

        }


        /**
         * 获取颜色值
         */
        fun getColorFormRes(@ColorRes colorId: Int): Int {
            return CommonUtil.getInstance().applicationContext.resources.getColor(colorId)
        }

        /**
         * 获取字符串
         */
        fun getStringFormRes(@StringRes stringId: Int) {
            CommonUtil.getInstance().applicationContext.getString(stringId)
        }

        /**
         * 获取Drawable
         */
        fun getDrawableFormRes(@DrawableRes drawableId: Int): Drawable {
            return CommonUtil.getInstance().applicationContext.getDrawable(drawableId)!!
        }


    }
}
