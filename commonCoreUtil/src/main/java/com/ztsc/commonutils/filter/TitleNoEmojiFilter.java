package com.ztsc.commonutils.filter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;

import java.util.regex.Pattern;

/**
 * Created by benchengzhou on 2019/8/21  11:48 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 过滤器 标题类 不可输入表情符号
 * 类    名： TitleFilter
 * 备    注：
 */

public class TitleNoEmojiFilter  extends EmojiFilter {


    private static final String PATTERN_STR = "[\n|\t|\\s]";
    private static final Pattern PATTERN = Pattern.compile(PATTERN_STR, Pattern.CASE_INSENSITIVE);

    /**
     *
     * @param source
     * @param start
     * @param end
     * @param dest
     * @param dstart
     * @param dend
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
        // 将/n/t/空格 替换掉，这里不会出现奇怪的连带效果，亲测有效
        return PATTERN.matcher(super.filter(source, start, end, dest, dstart, dend)).replaceAll("");
    }


}
