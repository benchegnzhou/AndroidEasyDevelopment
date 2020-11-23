package com.ztsc.commonuimoudle.commonlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ztsc.commonuimoudle.R;

/**
 * @author 许阳
 * 2020/10/27 10:39
 **/
public class CommonLayoutOnlyRead extends RelativeLayout {

    private ImageView ivLeft;
    private TextView tvTittle;
    private TextView tvContent;

    public CommonLayoutOnlyRead(Context context) {
        super(context);
        initView(context);
    }

    public CommonLayoutOnlyRead(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonLayoutOnlyRead);
        String tittle = typedArray.getString(R.styleable.CommonLayoutOnlyRead_tittle_text);
        tvTittle.setText(TextUtils.isEmpty(tittle) ? "" : tittle);
        String content = typedArray.getString(R.styleable.CommonLayoutOnlyRead_content_text);
        tvContent.setText(TextUtils.isEmpty(content) ? "" : content);
        int resourceId = typedArray.getResourceId(R.styleable.CommonLayoutOnlyRead_icon_src, R.drawable.list_icon_name_blue);
        ivLeft.setImageResource(resourceId);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.layout_common_only_read, this);
        ivLeft = findViewById(R.id.iv_left);
        tvTittle = findViewById(R.id.tv_tittle);
        tvContent = findViewById(R.id.tv_content);
    }

    public void setTvLeft(String tittle) {
        tvTittle.setText(tittle);
    }

    public void setTvContent(String content) {
        tvContent.setText(content);
    }
}
