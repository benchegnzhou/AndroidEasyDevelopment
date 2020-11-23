package zbc.com.cn.utils

import android.widget.TextView
import com.zzhoujay.richtext.RichText

/**
 * 富文本解释器
 *
 * https://blog.csdn.net/u013297881/article/details/92794467
 */
var TextView.markdownText: String
    set(value) {
        RichText.fromMarkdown(value).into(this)
    }
    get() = text.toString()
