package com.ztsc.commonutils.filter;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.UnderlineSpan;

/**
 * Created by benchengzhou on 2019/8/21  11:48 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 身份证过滤
 * 类    名： TitleFilter
 * 备    注：
 */
public class IDcardNumberInputFilter extends BaseFilter {


    /**
     * @param source 新输入的字符串
     * @param start  新输入的字符串起始下标，一般为0
     * @param end    新输入的字符串终点下标，一般为source长度-1
     * @param dest   输入之前文本框内容
     * @param dstart 原内容起始坐标，一般为0
     * @param dend   原内容终点坐标，一般为dest长度-1
     * @return
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        //如果含有智能拼写直接过滤掉，避免填充进入edittext
        SpannableString ss = new SpannableString(source);
        Object[] spans = ss.getSpans(0, ss.length(), Object.class);
        if (spans != null) {
            for (Object span : spans) {
                if (span instanceof UnderlineSpan) {
                    return null;
                }
            }
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (int i = 0; i < source.length(); i++) {
            if (isIDcardNumber(source.charAt(i))) {
                builder.append(source.charAt(i));
            }
        }
        return builder;
    }



}
