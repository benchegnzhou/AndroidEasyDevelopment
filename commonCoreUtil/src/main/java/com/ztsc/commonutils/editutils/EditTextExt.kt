package com.ztsc.commonutils.editutils

import android.widget.EditText


/**
 * Created by benchengzhou on 2020/10/20  15:12 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 对EditText 的过滤器进行扩展
 * 类    名：
 * 备    注：
 */


/**
 * 设置EditText 文本最大长度
 * @maxTvCount:最大长度
 */

fun EditText.etTvMaxNum(maxTvCount: Int) {
    if (this == null) return
    EditTextUtils.etTvMaxNum(this, maxTvCount)
}

/**
 * 将光标移动到文字末尾
 */

fun EditText.cursorMoveLast() {
    if (this == null) return
    EditTextUtils.cursorMoveLast(this)
}

/**
 * 将光标移动到文本头部
 */
fun EditText.cursorMoveFirst() {
    if (this == null) return
    EditTextUtils.cursorMoveFirst(this)
}

/**
 * 限制输入金额只能小数点后两位
 */
fun EditText.restrict2Decimal() {
    if (this == null) return
    EditTextUtils.cursorMoveFirst(this)
}


/**
 * 无表情，可以换行
 * 给定一个editText ,向其中插入表情过滤
 * 这个操作会保留原有的filter 属性不变
 */
fun EditText.setFilterTextEnterNoEmoji() {
    if (this == null) return
    InputFilterHelper.setFilterTextEnterNoEmoji(this)
}

/**
 * 无表情，禁止换行
 * 给定一个editText ,向其中插入表情过滤和禁止换行过滤
 * 这个操作会保留原有的filter 属性不变
 */
fun EditText.setFilterTextNoEmojiNoEnter() {
    if (this == null) return
    InputFilterHelper.setFilterTextNoEmojiNoEnter(this)
}

/**
 * 用户输入过滤器，用户仅可以录入数字和字母
 * 给定一个editText ,向其中插入表情过滤和禁止换行过滤
 * 这个操作会保留原有的filter 属性不变
 */
fun EditText.setFilterTextOnlyLetterNum() {
    if (this == null) return
    InputFilterHelper.setFilterTextOnlyLetterNum(this)
}


/**
 * 无表情，禁止换行
 * 给定一个editText ,用户仅可以录入汉字和字母
 * 这个操作会保留原有的filter 属性不变
 */
fun EditText.setFilterOnlyChineseLetter() {
    if (this == null) return
    InputFilterHelper.setFilterOnlyChineseLetter(this)
}


/**
 * 文本仅保留汉字
 * 给定一个editText ,用户仅可以录入汉字
 * 这个操作会保留原有的filter 属性不变
 */

fun EditText.setFilterOnlyChinese() {
    if (this == null) return
    InputFilterHelper.setFilterOnlyChinese(this)
}


/**
 * 禁止EditText输入空格 回车 表情
 * 人名录入用
 *
 * @param editText
 */

fun EditText.setFilterOnlyNoSpaceNoEmojiNoEnter() {
    if (this == null) return
    InputFilterHelper.setFilterOnlyNoSpaceNoEmojiNoEnter(this)
}


/**
 * 用户金额录入
 */

fun EditText.setFilterMoney() {
    if (this == null) return
    InputFilterHelper.setFilterMoney(this)
}

/**
 * 用户仅可以录入数字
 */

fun EditText.setFilterOnlyNumber() {
    if (this == null) return
    InputFilterHelper.setFilterOnlyNumber(this)
}


/**
 * 设置editText仅仅可以录入汉字和数字
 *
 * @param editText
 */

fun EditText.setFilterOnlyCharNum() {
    if (this == null) return
    InputFilterHelper.setFilterOnlyCharNum(this)
}


/**
 * 设置editText仅仅可以录入汉字、字母和数字
 *
 * @param editText
 */

fun EditText.setFilterOnlyCharLetterNum() {
    if (this == null) return
    InputFilterHelper.setFilterOnlyCharLetterNum(this)
}

/**
 * 设置editText仅仅可以录入汉字、字母和数字和空格
 *
 * @param editText
 */

fun EditText.setFilterOnlyCharLetterNumberSpaceFilter() {
    if (this == null) return
    InputFilterHelper.setFilterOnlyCharLetterNumberSpaceFilter(this)
}

/**
 * 设置editText 内容类 用户仅可以输入 汉字 字母 数字 特殊符号
 *
 * @param editText
 */

fun EditText.setFilterOnlyCharLetterNumberSymbol() {
    if (this == null) return
    InputFilterHelper.setFilterOnlyCharLetterNumberSymbol(this)
}

/**
 * 设置editText 输入一位小数
 *
 * @param editText
 */

fun EditText.setFilterOneDecimals() {
    if (this == null) return
    InputFilterHelper.setFilterOneDecimals(this)
}


