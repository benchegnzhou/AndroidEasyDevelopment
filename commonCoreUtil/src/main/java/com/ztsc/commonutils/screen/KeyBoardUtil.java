package com.ztsc.commonutils.screen;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by benchengzhou on 2020/6/10  13:10 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 键盘工具类
 * 类    名： KeyBoardUtil
 * 备    注：
 */

public class KeyBoardUtil {

    /**
     * 控制View跟随键盘升起和下降，防止输入控件被遮挡
     * 注意为了防止内存大额泄漏，这个方法需要结合 {@link #unRegisterKeyboardLayoutUpListener(final View root, ViewTreeObserver.OnGlobalLayoutListener listener) }
     *
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     * @return
     */
    public static ViewTreeObserver.OnGlobalLayoutListener registerKeyboardLayoutUpListener(final View root, final View scrollToView) {
        try {

            ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    try {
                        Rect rect = new Rect();
                        //获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);
                        //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                        int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                        //若不可视区域高度大于100，则键盘显示
                        if (rootInvisibleHeight > 100) {
                            int[] location = new int[2];
                            //获取scrollToView在窗体的坐标
                            scrollToView.getLocationInWindow(location);
                            //计算root滚动高度，使scrollToView在可见区域的底部
                            int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                            root.scrollTo(0, srollHeight);
                        } else {
                            //键盘隐藏
                            root.scrollTo(0, 0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            root.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
            return onGlobalLayoutListener;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 控制View跟随键盘升起和下降，防止输入控件被遮挡
     * 注意为了防止内存大额泄漏，这个方法需要结合
     *
     * @return
     */
    public static void unRegisterKeyboardLayoutUpListener(final View root, ViewTreeObserver.OnGlobalLayoutListener listener) {
        try {
            root.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
