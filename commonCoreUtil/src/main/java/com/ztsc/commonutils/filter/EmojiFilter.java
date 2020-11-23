package com.ztsc.commonutils.filter;

import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.UnderlineSpan;

import java.util.regex.Pattern;

/**
 * Created by benchengzhou on 2018/10/19  11:34 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 表情符号过滤
 * 类    名： EmojiFilter
 * 备    注：
 */

public class EmojiFilter  implements InputFilter {
    public static final Pattern EMOJI_PATTERN = Pattern.compile("(?:[\uD83C\uDF00-\uD83D\uDDFF]|[\uD83E\uDD00-\uD83E\uDDFF]|[\uD83D\uDE00-\uD83D\uDE4F]|[\uD83D\uDE80-\uD83D\uDEFF]|[\u2600-\u26FF]\uFE0F?|[\u2700-\u27BF]\uFE0F?|\u24C2\uFE0F?|[\uD83C\uDDE6-\uD83C\uDDFF]{1,2}|[\uD83C\uDD70\uD83C\uDD71\uD83C\uDD7E\uD83C\uDD7F\uD83C\uDD8E\uD83C\uDD91-\uD83C\uDD9A]\uFE0F?|[\u0023\u002A\u0030-\u0039]\uFE0F?\u20E3|[\u2194-\u2199\u21A9-\u21AA]\uFE0F?|[\u2B05-\u2B07\u2B1B\u2B1C\u2B50\u2B55]\uFE0F?|[\u2934\u2935]\uFE0F?|[\u3030\u303D]\uFE0F?|[\u3297\u3299]\uFE0F?|[\uD83C\uDE01\uD83C\uDE02\uD83C\uDE1A\uD83C\uDE2F\uD83C\uDE32-\uD83C\uDE3A\uD83C\uDE50\uD83C\uDE51]\uFE0F?|[\u203C\u2049]\uFE0F?|[\u25AA\u25AB\u25B6\u25C0\u25FB-\u25FE]\uFE0F?|[\u00A9\u00AE]\uFE0F?|[\u2122\u2139]\uFE0F?|\uD83C\uDC04\uFE0F?|\uD83C\uDCCF\uFE0F?|[\u231A\u231B\u2328\u23CF\u23E9-\u23F3\u23F8-\u23FA]\uFE0F?)",
            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

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
        }catch (Exception e){

        }
        return source;
    }

    private boolean needFilter(CharSequence source) {
        return EMOJI_PATTERN.matcher(source).find();
    }

    private boolean isEmoji(CharSequence str) {
        // return EMOJI_PATTERN.matcher(str).match();
        return EMOJI_PATTERN.matcher(str).matches();
    }

}
