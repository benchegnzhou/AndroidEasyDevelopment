/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zbc.androideasydevelopment.callback;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.base.Request;
import com.zbc.androideasydevelopment.application.MApplicationInfo;
import com.ztsc.commonutils.devicemessage.DeviceMessageUtils;
import com.ztsc.commonutils.logcat.LogUtil;


import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2016/1/14
 * 描    述：默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * 修订历史：
 * ================================================
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Type type;
    private Class<T> clazz;
    private StringConvert convert;

    public JsonCallback() {
        convert = new StringConvert();
    }


    public JsonCallback(Type type) {
        this.type = type;
        convert = new StringConvert();
    }

    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
        convert = new StringConvert();
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
        try {
            request.headers("header1", "HeaderValue1")//
                    .params("machineId", DeviceMessageUtils.getIMEI(MApplicationInfo.sAppContext))//
                    .params("machineName", DeviceMessageUtils.getMobileInfo(MApplicationInfo.sAppContext))
                    .params("clientType", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {

        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用

        //详细自定义的原理和文档，看这里： https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback
/*
        if (type == null) {
            if (clazz == null) {
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                JsonConvert<T> convert = new JsonConvert<>(clazz);
                return convert.convertResponse(response);
            }
        }

        JsonConvert<T> convert = new JsonConvert<>(type);
        return convert.convertResponse(response);*/

     /*   ProgressRequestBody body = (ProgressRequestBody)response.request().body();
        body.writeTo(new Buffer());*/


        String s = convert.convertResponse(response);
        LogUtil.e("框架底层打印，网络请求的结果是：-----" + s);
        if (s == null) {
            return null;
        }

        T data = null;
        Gson gson = new Gson();

        if (type != null) {
            data = gson.fromJson(s, type);
        }
        if (clazz != null) {
            data = gson.fromJson(s, clazz);
        }
        return data;
    }
}
