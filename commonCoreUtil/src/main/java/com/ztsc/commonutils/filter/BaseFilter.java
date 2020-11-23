package com.ztsc.commonutils.filter;

import android.text.InputFilter;

import java.util.regex.Pattern;


/**
 * Created by benchengzhou on 2019/9/10  16:48 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 统一过滤器控制类
 * 类    名： BaseFilter
 * 备    注：
 */

public abstract class BaseFilter implements InputFilter {
    //数字字符集
    private static final String PATTERN_NUM = "[0-9]";
    //字母字符集
    private static final String PATTERN_LETTER = "[a-zA-Z]";
    //单个汉字字符集
    private static final String PATTERN_CHINESE = "[\\u4e00-\\u9fa5]";
    //中文、英文、数字包括下划线：\u4E00-\u9FA5A-Za-z0-9_
    private static final String PATTERN_CHINESE_LETTER_NUMBER_UNDERLINE = "[\\u4E00-\\u9FA5A-Za-z0-9_]";
    //private static final String PATTERN_STR = "[\n|\t|\\s]";
    private static final String PATTERN_BR_TABLE = "[\\n\\t]";
    //空格
    private static final String PATTERN_SPACE = "[\\s]";
    //身份证号0-9xX
    private static final String PATTERN_IDCARD_NUMBER = "[0-9xX]";

    /**
     * 判断是数字
     *
     * @param c
     */
    public boolean isNumber(char c) {
        return Pattern.compile(PATTERN_NUM).matcher(String.valueOf(c)).matches();
    }

    /**
     * 判断是字母
     *
     * @param c
     */
    public boolean isLetter(char c) {
        return Pattern.compile(PATTERN_LETTER).matcher(String.valueOf(c)).matches();
    }

    /**
     * 判断是字母
     *
     * @param c
     */
    public boolean isChinese(char c) {
        return Pattern.compile(PATTERN_CHINESE).matcher(String.valueOf(c)).matches();
    }

    /**
     * 判断是中文、英文、数字包括下划线：\u4E00-\u9FA5A-Za-z0-9_
     *
     * @param c
     */
    public boolean isChineseLetterNumberUnderline(char c) {
        return Pattern.compile(PATTERN_CHINESE_LETTER_NUMBER_UNDERLINE).matcher(String.valueOf(c)).matches();
    }

    /**
     * 判断是为回车和换行
     *
     * @param c
     */
    public boolean isBrTable(char c) {
        return Pattern.compile(PATTERN_BR_TABLE).matcher(String.valueOf(c)).matches();
    }

    /**
     * 判断是为身份证号字符0-9xX
     *
     * @param c
     */
    public boolean isIDcardNumber(char c) {
        return Pattern.compile(PATTERN_IDCARD_NUMBER).matcher(String.valueOf(c)).matches();
    }

    /**
     * 判断是为空格
     *
     * @param c
     */
    public boolean isSpace(char c) {
        return Pattern.compile(PATTERN_SPACE).matcher(String.valueOf(c)).matches();
    }


}
