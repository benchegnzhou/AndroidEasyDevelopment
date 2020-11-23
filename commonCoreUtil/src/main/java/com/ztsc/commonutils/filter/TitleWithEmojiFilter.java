package com.ztsc.commonutils.filter;

import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.UnderlineSpan;

import java.util.regex.Pattern;

/**
 * Created by benchengzhou on 2019/8/21  11:54 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 标题表情符号
 * 类    名： TitleWithEmojiFilter
 * 备    注：
 */

public class TitleWithEmojiFilter implements InputFilter {
    private static final String PATTERN_STR = "[\n|\t|\\s]";
    private static final Pattern PATTERN = Pattern.compile(PATTERN_STR, Pattern.CASE_INSENSITIVE);


    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
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
            if (needFilter(source)) {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                int abStart = start;
                for (int i = start; i < end; i++) {
                    if (isEmoji(String.valueOf(source.charAt(i)))) {
                        if (i != abStart) {
                            builder.append(source.subSequence(abStart, i));
                        }
                        abStart = i + 1;
                    } else {
                        // 所有的emoji不是一个字符就是两个字符，所以单独处理
                        if (i + 1 <= end && isEmoji(source.subSequence(i, i + 2))) {
                            if (i != abStart) {
                                builder.append(source.subSequence(abStart, i));
                            }
                            abStart = i + 2;
                            i += 1;  // 纠正角标
                        }
                    }
                }

                if (abStart < end) {
                    builder.append(source.subSequence(abStart, end));
                }
                return builder;
            }
        } catch (Exception e) {
        }

        return source;
    }

    private boolean needFilter(CharSequence source) {
        return PATTERN.matcher(source).find();
    }

    private boolean isEmoji(CharSequence str) {
        // return EMOJI_PATTERN.matcher(str).match();
        return PATTERN.matcher(str).matches();
    }
}
