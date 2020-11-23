package zbc.com.cn.utils

import com.google.gson.Gson

inline fun <reified T> Gson.fromJson(json:String)=fromJson<T>(json,T::class.java)