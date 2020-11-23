package com.ztsc.commonuimoudle.partline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.ztsc.commonuimoudle.R;


/**
 * Created by benchengzhou on 2018/3/16  15:47 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 一个横向的虚线view
 * 类    名： HorizontalPartLineView
 * 备    注： 网络参考链接： http://blog.csdn.net/cxmscb/article/details/51760528
 * http://blog.csdn.net/xmxkf/article/details/51468648
 * https://www.cnblogs.com/prophet-it/p/6651033.html
 */

public class HorizontalPartLineView extends View {

    private int DEFAULT_SOLID_WIDTH = 1;
    private int DEFAULT_HOLLOW_WIDTH = 1;
    private int DEFAULT_BG_COLOR = 0xffffffff;
    private int DEFAULT_SOLID_COLOR = 0xffdcdcdc;


    private int SolidW;
    private int hollowW;
    private int SolidColor;
    private int bgColor;
    private int offset=0;
    private Paint mPaint;

    private int DEFAULT_LINE_HEIGHT = 30;
    private int mWidth;
    private int mHeight;

    public HorizontalPartLineView(Context context) {
        this(context, null);
    }

    public HorizontalPartLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalPartLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Horizontal_PartLine_View, defStyleAttr, 0);

        //边框线宽
        SolidW = a.getDimensionPixelSize(R.styleable.Horizontal_PartLine_View_solid_width, DEFAULT_SOLID_WIDTH);
        hollowW = a.getDimensionPixelSize(R.styleable.Horizontal_PartLine_View_hollow_width, DEFAULT_HOLLOW_WIDTH);
        SolidColor = a.getColor(R.styleable.Horizontal_PartLine_View_solid_color, DEFAULT_SOLID_COLOR);
        bgColor = a.getColor(R.styleable.Horizontal_PartLine_View_bg_color, DEFAULT_BG_COLOR);
        offset = a.getColor(R.styleable.Horizontal_PartLine_View_offset, 0);


        if (SolidW % 2 != 0) {
            SolidW++;
        }
        if (hollowW % 2 != 0) {
            hollowW++;
        }

        if (SolidW == 0) {
            new Throwable(new IllegalArgumentException("the value SolidW san not be zone !"));
        }
        if (hollowW == 0) {
            new Throwable(new IllegalArgumentException("the value hollowW san not be zone !"));
        }

        a.recycle();   //使用完成之后记得回收
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         mWidth = MeasureSpec.getSize(widthMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
         mHeight = MeasureSpec.getSize(heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(bgColor);
        canvas.drawRect(0, 0, mWidth, mHeight, paint);



       mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(SolidColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(/*dip2px(ctx,2)*/mHeight);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(mWidth, 0);
        PathEffect effects = new DashPathEffect(new float[]{SolidW, hollowW, SolidW, hollowW}, offset);
        mPaint.setPathEffect(effects);
        canvas.drawPath(path, mPaint);
    }


}
