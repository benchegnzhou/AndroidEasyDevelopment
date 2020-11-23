package com.ztsc.commonutils.customview.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.commonstatusbarlib.R;
import com.ztsc.commonutils.bitmap.BitmapUtils;


/**
 * Created by benchengzhou on 2017/8/22.
 * 作者邮箱：mappstore@163.com
 * 功能描述：
 * 备    注：
 */


public class RoundImageView extends AppCompatImageView {


    /**
     * 图片的类型，圆形or圆角
     */
    private int type;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;
    /**
     * 5、状态的存储与恢复
     * 当然了，如果内存不足，而恰好我们的Activity置于后台，不幸被重启，或者用户旋转屏幕造成Activity重启，我们的View应该也能尽可能的去保存自己的属性。
     * 状态保存什么用处呢？比如，现在一个的圆角大小是10dp，用户点击后变成50dp；当用户旋转以后，或者长时间置于后台以后，返回我们的Activity应该还是50dp；
     */
    private static final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";
    private static final String STATE_BORDER_RADIUS = "state_border_radius";

    private Context mContext;


    /**
     * 圆角的大小
     */
    private int mBorderRadius;
    /**
     * 圆角大小的默认值，单位dp
     */
    private static final int BODER_RADIUS_DEFAULT = 10;
    /**
     * 绘图的Paint
     */
    private Paint mBitmapPaint;
    /**
     * 圆角的半径
     */
    private int mRadius;
    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private Matrix mMatrix;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader mBitmapShader;
    /**
     * view的宽度
     */
    private int mWidth;
    private RectF mRoundRect;


    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData(attrs);
    }

    private void initData(AttributeSet attrs) {
        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);    //设置抗锯齿


        TypedArray a = mContext.obtainStyledAttributes(attrs,
                R.styleable.RoundImageView);

        // 默认为10dp
        mBorderRadius = a.getDimensionPixelSize(
                R.styleable.RoundImageView_borderRadius, (int) TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP, //单位的类型
                                BODER_RADIUS_DEFAULT, getResources()  //
                                        .getDisplayMetrics()));
        type = a.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);// 默认为Circle

        a.recycle();    //为了方便回收利用，使用完成之后一定记得回收

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        /**
         * 如果类型是圆形，则强制改变view的宽高一致，以小值为准
         */
        if (type == TYPE_CIRCLE) {
            ScaleType scaleType = getScaleType();
            switch (scaleType) {
                case CENTER_CROP:
                    mWidth = Math.max(getMeasuredWidth(), getMeasuredHeight());
                    mRadius = mWidth / 2;
                    setMeasuredDimension(mWidth, mWidth);
                    break;
                case CENTER_INSIDE:
                    mWidth = Math.max(getMeasuredWidth(), getMeasuredHeight());
                    mRadius = mWidth / 2;
                    setMeasuredDimension(mWidth, mWidth);
                    break;
                case FIT_XY:
                    mWidth = Math.max(getMeasuredWidth(), getMeasuredHeight());
                    mRadius = mWidth / 2;
                    setMeasuredDimension(mWidth, mWidth);
                    break;
                default:
                    mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
                    mRadius = mWidth / 2;
                    setMeasuredDimension(mWidth, mWidth);
                    break;
            }

        }
    }


    /**
     * 初始化BitmapShader
     */
    private void setUpShader() {
        Drawable drawable = getDrawable();   //获取设置的src
        if (drawable == null) {
            return;
        }

        Bitmap bmp = BitmapUtils.getInstance().drawable2Bitmap(drawable);
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        mBitmapShader = new BitmapShader(bmp, TileMode.CLAMP, TileMode.CLAMP);
        float scale = 1.0f;    //设置缩放
        if (type == TYPE_CIRCLE) {
            // 拿到bitmap宽或高的小值
            int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
            scale = mWidth * 1.0f / bSize;

        } else if (type == TYPE_ROUND) {
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            scale = Math.max(getWidth() * 1.0f / bmp.getWidth(), getHeight()
                    * 1.0f / bmp.getHeight());
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        // 设置shader
        mBitmapPaint.setShader(mBitmapShader);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null)   //判断是否已经设置了图片
        {
            return;
        }
        setUpShader();  //重新绘制图形

        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius,  //矩形图片绘制
                    mBitmapPaint);
        } else {
            canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);   //圆角图片绘制
            // drawSomeThing(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 圆角图片的范围
        if (type == TYPE_ROUND) {
            mRoundRect = new RectF(0, 0, getWidth(), getHeight());
        }
    }


    /**
     * 用于系统内存不足回收后的数据恢复,存储方法
     *
     * @return
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_TYPE, type);
        bundle.putInt(STATE_BORDER_RADIUS, mBorderRadius);
        return bundle;
    }

    /**
     * 用于系统内存不足回收后的数据恢复,恢复方法
     *
     * @param state
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(((Bundle) state)
                    .getParcelable(STATE_INSTANCE));
            this.type = bundle.getInt(STATE_TYPE);
            this.mBorderRadius = bundle.getInt(STATE_BORDER_RADIUS);
        } else {
            super.onRestoreInstanceState(state);
        }

    }


    /**
     * 对外界暴露方法，设置圆角半径，和形状类型
     *
     * @param borderRadius
     */
    public void setBorderRadius(int borderRadius) {
        int pxVal = dp2px(borderRadius);
        if (this.mBorderRadius != pxVal) {
            this.mBorderRadius = pxVal;
            invalidate();
        }
    }

    public void setType(int type) {
        if (this.type != type) {
            this.type = type;
            if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE) {
                this.type = TYPE_CIRCLE;
            }
            requestLayout();
        }

    }

    public int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }
}
