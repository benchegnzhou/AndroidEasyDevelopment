package com.ztsc.commonutils.filter;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;

import java.util.regex.Pattern;


/**
 * Created by benchengzhou on 2018/10/19  11:43 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 过滤回车换行和表情符号
 * 类    名： NameFilter
 * 备    注： 文章链接：https://www.jianshu.com/p/e2e8dfd92bab
 */


public class NameFilter extends EmojiFilter {

    private static final String PATTERN_STR = "[\n|\t]";
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
        // 将/n/t替换掉，这里不会出现奇怪的连带效果，亲测有效
        return PATTERN.matcher(super.filter(source, start, end, dest, dstart, dend)).replaceAll("");
    }



}
