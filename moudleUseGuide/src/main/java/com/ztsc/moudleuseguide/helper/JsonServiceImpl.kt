package com.ztsc.moudleuseguide.helper

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.SerializationService
import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * Created by benchengzhou on 2020/10/29  14:55 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： 使用arouter 这个类必须重写，否则无法实现Object传值
 * 备    注：
 */

@Route(path = "/service/json")
class JsonServiceImpl : SerializationService {

    private var mGson: Gson? = null


    override fun <T : Any?> json2Object(input: String?, clazz: Class<T>?): T {
        checkJson()
        return mGson?.fromJson(input, clazz) ?: null as T
    }

    override fun init(context: Context?) {
        mGson = Gson()
    }

    override fun object2Json(instance: Any?): String {
        checkJson()
        return mGson?.toJson(instance) ?: ""
    }

    override fun <T : Any?> parseObject(input: String?, clazz: Type?): T {
        checkJson()
        return mGson?.fromJson(input, clazz) ?: null as T
    }

    fun checkJson() {
        if (mGson == null) {
            mGson = Gson()
        }
    }

}