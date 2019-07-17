package com.zbc.androideasydevelopment.callback;

/**
 * Created by benchengzhou on 2018/1/5  17:34 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 用户登录的回调
 * 类    名： LoginCallBackInterface
 * 备    注：
 */

public interface LoginCallBackInterface {


    //登录成功
    void onloginSuccess();

    //登录失败
    void onloginFail();


}
