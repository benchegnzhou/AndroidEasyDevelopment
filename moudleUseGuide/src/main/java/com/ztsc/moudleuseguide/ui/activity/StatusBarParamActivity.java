package com.ztsc.moudleuseguide.ui.activity;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.gyf.immersionbar.ImmersionBar;
import com.ztsc.house.BaseActivity;
import com.ztsc.moudleuseguide.R;


/**
 * Created by benchengzhou on 2020/9/27  11:31 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 获取状态栏参数
 * 类    名： StatusBarParamActivity
 * 备    注：
 */

public class StatusBarParamActivity extends BaseActivity {

    private boolean mIsHideStatusBar = false;

    @Override
    public int getContentView() {
        return R.layout.activity_statusbar_param;
    }


    @Override
    public void setStatusBar() {
        //*******  这种如果没有titlebar会顶上去，这种情况可以作为沉浸式状态栏使用的
        ImmersionBar.with(this).titleBar(findViewById(R.id.mToolbar))
                .setOnNavigationBarListener(show -> {
                    Toast.makeText(this, "导航栏" + (show ? "显示了" : "隐藏了"), Toast.LENGTH_SHORT).show();
                })
                .navigationBarColor(R.color.btn13).init();
    }


    @Override
    public void initListener() {

        //操作状态栏显示隐藏
        findViewById(R.id.mBtnStatus).setOnClickListener(v -> {
            if (!mIsHideStatusBar) {
                //状态栏隐藏
                ImmersionBar.hideStatusBar(getWindow());
                mIsHideStatusBar = true;
            } else {
                //状态栏显示
                ImmersionBar.showStatusBar(getWindow());
                mIsHideStatusBar = false;
            }
        });

        //设置状态栏
        TextView mTvInsets = findViewById(R.id.mTvInsets);
        ViewCompat.setOnApplyWindowInsetsListener(mTvInsets, (view, windowInsetsCompat) -> {
            mTvInsets.setText(getText(getTitle(mTvInsets) + windowInsetsCompat.getSystemWindowInsetTop()));
            return windowInsetsCompat.consumeSystemWindowInsets();
        });
    }

    @Override
    public void initData() {
        TextView mTvStatus = findViewById(R.id.mTvStatus);
        TextView mTvHasNav = findViewById(R.id.mTvHasNav);
        TextView mTvNav = findViewById(R.id.mTvNav);
        TextView mTvNavWidth = findViewById(R.id.mTvNavWidth);
        TextView mTvAction = findViewById(R.id.mTvAction);
        TextView mTvHasNotch = findViewById(R.id.mTvHasNotch);
        TextView mTvNotchHeight = findViewById(R.id.mTvNotchHeight);
        TextView mTvFits = findViewById(R.id.mTvFits);
        TextView mTvStatusDark = findViewById(R.id.mTvStatusDark);
        TextView mTvNavigationDark = findViewById(R.id.mTvNavigationDark);
        mTvStatus.setText(getText(getTitle(mTvStatus) + ImmersionBar.getStatusBarHeight(this)));
        mTvHasNav.setText(getText(getTitle(mTvHasNav) + ImmersionBar.hasNavigationBar(this)));
        mTvNav.setText(getText(getTitle(mTvNav) + ImmersionBar.getNavigationBarHeight(this)));
        mTvNavWidth.setText(getText(getTitle(mTvNavWidth) + ImmersionBar.getNavigationBarWidth(this)));

        //只有绑定activitybar会生效
        mTvAction.setText(getText(getTitle(mTvAction) + ImmersionBar.getActionBarHeight(this)));
        mTvHasNotch .post(() ->  mTvHasNotch.setText(getText(getTitle(mTvHasNotch) + ImmersionBar.hasNotchScreen(this))));
        mTvNotchHeight .post(() ->   mTvNotchHeight.setText(getText(getTitle(mTvNotchHeight) + ImmersionBar.getNotchHeight(this))));

        mTvFits.setText(getText(getTitle(mTvFits) + ImmersionBar.checkFitsSystemWindows(findViewById(android.R.id.content))));
        mTvStatusDark.setText(getText(getTitle(mTvStatusDark) + ImmersionBar.isSupportStatusBarDarkFont()));
        mTvNavigationDark.setText(getText(getTitle(mTvNavigationDark) + ImmersionBar.isSupportNavigationIconDark()));

    }


    private SpannableString getText(String text) {
        String[] split = text.split(" {3}");
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.btn3));
        spannableString.setSpan(colorSpan, text.length() - split[1].length(), text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private String getTitle(TextView textView) {
        String[] split = textView.getText().toString().split(" {3}");
        return split[0] + "   ";
    }

}
