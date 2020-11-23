package com.ztsc.moudleuseguide.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.ImmersionBar;
import com.ztsc.commonuimoudle.constant.CommonARouterContant;
import com.ztsc.commonutils.toast.ToastUtils;
import com.ztsc.house.BaseActivity;
import com.ztsc.moudleuseguide.R;
import com.ztsc.moudleuseguide.ui.activity.webview.CommonWebViewActivity;
import com.ztsc.moudleuseguide.ui.base.ClickActionKt;

import org.jetbrains.annotations.Nullable;


/**
 * Created by benchengzhou on 2020/11/2  14:35 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： activity数据保存实验
 * 类    名： DemoOnSaveInstanceActivity
 * https://www.jianshu.com/p/460a2e525359
 * 备    注：
 */


@Route(path = CommonARouterContant.DemoOnSaveInstanceActivity)
public class DemoOnSaveInstanceActivity extends BaseActivity {
    @Autowired(name = "title")
    public String title = "";

    @Override
    public int getContentView() {
        return R.layout.activity_demo_onsaveinstance;
    }

    @Override
    public void initListener() {
        ARouter.getInstance().inject(this);
        ImmersionBar.with(this).titleBar(R.id.common_toolbar_root)
                .addTag("PhotoSelectActivity")
                .init();
        ClickActionKt.addClick(this, R.id.tv_msg, R.id.tv_msg2);
    }

    @Override
    public void initData() {
        //一个特殊的使用方式
        TextView textView = $(R.id.tv_title);
        textView.setText(title);

        TextView tv_msg2 = $(R.id.tv_msg2);
        tv_msg2.setText("*onSaveInstanceState和onRestoreInstanceState用处后面会介绍到。\n" +
                "先介绍2种非默认情况下的操作\n" +
                "1：禁止屏幕旋转\n" +
                "在AndroidManifest.xml的Activity中配置\n" +
                "始终竖屏\n" +
                "android:screenOrientation=``\"portrait\"\n" +
                "始终横屏\n" +
                "android:screenOrientation=``\"landscape\"\n" +
                "2：Activity跟随旋转但不销毁和重启\n" +
                "这个实现原理是告诉系统这个Activity的旋转处理由我们自己去处理，不用帮我销毁和重启\n" +
                "在AndroidManifest.xml的Activity中配置\n" +
                "android:configChanges=\"keyboardHidden|orientation|screenSize\"` 然后在Activity中进行方法复写，监听屏幕旋转并处理 `@Override` `public` `void` `onConfigurationChanged(Configuration newConfig) {` `super.onConfigurationChanged(newConfig);` `if` `(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {` `Toast.makeText(MainActivity.this,` `\"竖屏模式\",3000).show();` `}else{` `Toast.makeText(MainActivity.this``,\"横屏模式\",3000).show();}}`\n" +
                "接下来介绍，Activity进行默认旋转时候的处理，当屏幕进行旋转的时候会按照横屏的分辨率进行重绘，当然你也可以不进行任何处理难看就难看呗：），理想状态的处理就是建立两套同名的Layout，当屏幕旋转时系统会自动帮我们加载横屏的Layout。\n" +
                "首先在工程res目录下新建一个layout-land 这个目录下是专门为横屏Layout准备的\n" +
                "\n" +
                "作者：mahongyin\n" +
                "链接：https://www.jianshu.com/p/460a2e525359\n" +
                "来源：简书\n" +
                "著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。");

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("接收到的数据为").append(" title= ").append(savedInstanceState.get("title"));
            sb.append("  ").append(" msg= ").append(savedInstanceState.get("msg"));


            TextView msg = $(R.id.tv_msg);
            msg.setText(sb.toString());
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("title", "测试activity数据保存示例");
        outState.putString("msg", "我是保存到的数据信息");
        super.onSaveInstanceState(outState);
    }

    private boolean isPORTRAIT = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_msg:
                setRequestedOrientation(isPORTRAIT ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                isPORTRAIT = !isPORTRAIT;
                break;

            case R.id.tv_msg2:
                CommonWebViewActivity.loadCommonWebview(this, "Android 屏幕旋转适配", "https://www.jianshu.com/p/460a2e525359");
                break;
            default:
        }
    }
}
