package com.ztsc.commonuimoudle.editutils

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.ztsc.commonutils.logcat.LoggerUtil
import java.util.regex.Pattern

/**
 * Created by benchengzhou on 2020/10/20  13:25 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： edittext实现手机号码格式化输入，输入格式如 136 2022 4221
 * 类    名：
 * 备    注：
 */

/*class PhoneEditText(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
) : AppCompatEditText(context, attrs, defStyleAttr), TextWatcher {*/

class PhoneEditText(
    context: Context,
    attrs: AttributeSet?

) : AppCompatEditText(context, attrs ), TextWatcher {

    // 特殊下标位置
    private val PHONE_INDEX_3 = 3
    private val PHONE_INDEX_4 = 4
    private val PHONE_INDEX_8 = 8
    private val PHONE_INDEX_9 = 9


    /**
     * 手机号录入规则
     */
    private var digists = "0123456789"


    constructor(context: Context) : this(context, null)

    init {
        viewInit()
    }

    private fun viewInit() {
        this.keyListener = DigitsKeyListener.getInstance(digists)
        this.maxLines = 1
        //手机号的长度 ，加上两个空格
        this.filters = arrayOf<InputFilter>(LengthFilter(13))
    }


    /**
     * 修改手机号录入规则
     * 输入一个不为空的手机号匹配规则
     *
     * @param digists
     */
    fun setPhoneDigists(digists: String) {
        if (TextUtils.isEmpty(digists)) {
            return
        }
        this.digists = digists
        this.keyListener = DigitsKeyListener.getInstance(digists)
    }


    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
        super.onTextChanged(s, start, before, count)
        if (s == null || s.length == 0) {
            return
        }
        val sb = StringBuilder()
        for (i in 0 until s.length) {
            if (i != PHONE_INDEX_3 && i != PHONE_INDEX_8 && s[i] == ' ') {
                continue
            } else {
                sb.append(s[i])
                if ((sb.length == PHONE_INDEX_4 || sb.length == PHONE_INDEX_9) && sb[sb.length - 1] != ' ') {
                    sb.insert(sb.length - 1, ' ')
                }
            }
        }
        if (sb.toString() != s.toString()) {
            var index = start + 1
            if (sb[start] == ' ') {
                if (before == 0) {
                    index++
                } else {
                    index--
                }
            } else {
                if (before == 1) {
                    index--
                }
            }
            setText(sb.toString())
            setSelection(index)
        }
    }

    override fun afterTextChanged(s: Editable?) {}


    fun isMobileNOChar(mobiles: String?): Boolean {
        val p = Pattern.compile("\\d")
        val m = p.matcher(mobiles)
        return m.matches()
    }


    // 获得不包含空格的手机号
    fun getPhoneText(): String? {
        val str = text.toString()
        return replaceBlank(str)
    }

    private fun replaceBlank(str: String?): String? {
        var dest = ""
        if (str != null) {
            val p = Pattern.compile("\\s*|\t|\r|\n")
            val m = p.matcher(str)
            if (m.find()) {
                dest = m.replaceAll("")
            }
        }
        return dest
    }


}