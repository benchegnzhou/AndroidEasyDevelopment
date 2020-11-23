package com.ztsc.commonutils.editutils

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by benchengzhou on 2020/10/20  13:20 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： EditText 工具类
 * 类    名：
 * 备    注：
 */

class EditTextUtils {

    companion object {


        /**
         * 设置EditText 文本最大长度
         * @maxTvCount:最大长度
         */
        @JvmStatic
        fun etTvMaxNum(editText: EditText, maxTvCount: Int) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                    val ctx = charSequence.toString()
                    if (!TextUtils.isEmpty(ctx) && ctx.length > maxTvCount) {
                        editText.setText(ctx.substring(0, maxTvCount))
                        cursorMoveLast(editText)
                    }
                }

                override fun afterTextChanged(editable: Editable) {}
            })
        }

        /**
         * 将光标移动到文字末尾
         */
        @JvmStatic
        fun cursorMoveLast(editText: EditText) {
            try {
                editText.setSelection(editText.text.length) //将光标移至文字末尾
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /**
         * 将光标移动到文本头部
         */
        @JvmStatic
        fun cursorMoveFirst(editText: EditText) {
            try {
                editText.setSelection(0) //将光标移至文本头部
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        /**
         * 限制输入金额只能小数点后两位
         */
        @JvmStatic
        fun restrict2Decimal(editText: EditText) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(
                    s: CharSequence, start: Int, before: Int,
                    count: Int
                ) {
                    var s = s
                    if (s.toString().contains(".")) {
                        if (s.length - 1 - s.toString().indexOf(".") > 2) {
                            s = s.toString().subSequence(
                                0,
                                s.toString().indexOf(".") + 3
                            )
                            editText.setText(s)
                            editText.setSelection(s.length)
                        }
                    }
                    if (s.toString().trim { it <= ' ' }.substring(0) == ".") {
                        s = "0$s"
                        editText.setText(s)
                        editText.setSelection(2)
                    }
                    if (s.toString().startsWith("0")
                        && s.toString().trim { it <= ' ' }.length > 1
                    ) {
                        if (s.toString().substring(1, 2) != ".") {
                            editText.setText(s.subSequence(0, 1))
                            editText.setSelection(1)
                            return
                        }
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int, count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {

                }
            })
        }
    }


}