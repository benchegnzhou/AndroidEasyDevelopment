package com.ztsc.commonuimoudle.submitbtn;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ztsc.commonuimoudle.R;

/**
 * Created by benchengzhou on 2020/9/29  15:57 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： CommonSubmitBtn
 * 备    注：
 */

public class CommonSubmitBtn extends RelativeLayout {
    public Context context = null;
    private CharSequence btnTextEnable = "提交";
    private CharSequence btnTextUnEnable = "提交";
    private int btnTextColor = getResources().getColor(R.color.defaultTextColor);
    private TextView tvMessage;
    private ImageView ivIconLoading;
    private CharSequence loadingText = "加载中...";
    private BtnStatus mBtnStatus = BtnStatus.BTN_STATUS_ENABLE;

    public enum BtnStatus {
        BTN_STATUS_ENABLE,
        BTN_STATUS_UNENABLE,
        BTN_STATUS_LOADING
    }


    public CommonSubmitBtn(Context context) {
        this(context, null);
    }

    public CommonSubmitBtn(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonSubmitBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }


    public void init(AttributeSet attrs) {
        View view = View.inflate(context, R.layout.layout_common_submit_btn, null);
        tvMessage = view.findViewById(R.id.tv_message);
        ivIconLoading = view.findViewById(R.id.iv_icon_loading);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                addView(view);
//                setBtnStatus(BtnStatus.BTN_STATUS_ENABLE);
            }
        }, 5);
    }

    /**
     * 设置按钮文字
     *
     * @param btnText
     */
    public CommonSubmitBtn setBtnTextEnable(CharSequence btnText) {
        btnTextEnable = btnText;
        return this;
    }

    /**
     * 设置按钮文字
     *
     * @param btnText
     */
    public CommonSubmitBtn setBtnTextUnEnable(CharSequence btnText) {
        btnTextUnEnable = btnText;
        return this;
    }

    /**
     * 设置按钮字体颜色
     *
     * @param btnTextColor
     */
    public CommonSubmitBtn setBtnTextColor(int btnTextColor) {
        this.btnTextColor = btnTextColor;
        return this;
    }

    public CommonSubmitBtn setLoadingText(CharSequence loadingText) {
        if (loadingText != null && loadingText.length() != 0) {
            this.loadingText = loadingText;
        }
        return this;
    }

    public BtnStatus getBtnStatus() {
        return mBtnStatus;
    }


    /**
     * 调用同步状态进行刷新
     */
    public void sysn(){
        setBtnStatus(mBtnStatus);
    }
    public void setBtnStatus(BtnStatus mBtnStatus) {
        this.mBtnStatus = mBtnStatus;
        if (mBtnStatus == BtnStatus.BTN_STATUS_ENABLE) {
            onEnableStatus();
        } else if (mBtnStatus == BtnStatus.BTN_STATUS_UNENABLE) {
            onUnenableStatus();
        } else {
            onLoadingStatus();
        }
    }


    private void onLoadingStatus() {
        ivIconLoading.setVisibility(VISIBLE);
        tvMessage.setText(loadingText);
        tvMessage.setTextColor(btnTextColor);
        setEnabled(true);
        setClickable(false);

        Glide
                .with(this).asGif()
                .load(R.drawable.button_loading)
                .into(ivIconLoading);
    }

    private void onEnableStatus() {
        ivIconLoading.setVisibility(GONE);
        tvMessage.setText(btnTextEnable);
        tvMessage.setTextColor(btnTextColor);
        setEnabled(true);
        setClickable(true);
    }

    private void onUnenableStatus() {
        ivIconLoading.setVisibility(GONE);
        tvMessage.setText(btnTextEnable);
        tvMessage.setTextColor(btnTextColor);
        setEnabled(false);
        setClickable(false);
    }

}
