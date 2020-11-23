package zbc.com.cn.utils


import com.zbc.commonutils.sharepreference.PreferencesExt
import com.ztsc.commonutils.CommonUtil
import kotlin.reflect.jvm.jvmName

/**
 * Created by benny on 6/23/17.
 */
inline fun <reified R, T> R.pref(default: T) = PreferencesExt(CommonUtil.getInstance().applicationContext, "", default, R::class.jvmName)
