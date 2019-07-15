package com.zbc.androideasydevelopment.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;


/**
 * Created by benchengzhou on 2019/7/13 16:36.
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： BaseActivity
 * 备    注：
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        initListener();
        initData();
    }


    /**
     * 获取布局文件
     */
    protected abstract int getContentView();


    /**
     * 初始化监听任务和注册适配器
     */
    protected abstract void initListener();


    /**
     * 完成数据的初始化任务
     */
    protected abstract void initData();


    public void startAct(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void startAct(Bundle bundle, Class clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActForResult(Class clazz, int requestCode) {
        startActivityForResult(new Intent(this, clazz), requestCode);
    }

    public void startActForResult(Bundle bundle, Class clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    public void finishAct() {
        finish();
    }

    public void finishActWithCode(int resultCode) {
        setResult(resultCode);
        finishAct();
    }
}
