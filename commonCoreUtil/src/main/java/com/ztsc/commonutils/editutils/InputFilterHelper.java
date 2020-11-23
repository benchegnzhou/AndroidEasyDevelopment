package com.ztsc.commonutils.editutils;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import com.ztsc.commonutils.filter.EmojiFilter;
import com.ztsc.commonutils.filter.MoneyValueFilter;
import com.ztsc.commonutils.filter.NameFilter;
import com.ztsc.commonutils.filter.OneDecimalsFilter;
import com.ztsc.commonutils.filter.TitleOnlyCharFilter;
import com.ztsc.commonutils.filter.TitleOnlyCharLetterFilter;
import com.ztsc.commonutils.filter.TitleOnlyCharLetterNumberFilter;
import com.ztsc.commonutils.filter.TitleOnlyCharLetterNumberSpaceFilter;
import com.ztsc.commonutils.filter.TitleOnlyCharLetterNumberSymbolFilter;
import com.ztsc.commonutils.filter.TitleOnlyCharNumberFilter;
import com.ztsc.commonutils.filter.TitleOnlyNumLetterFilter;
import com.ztsc.commonutils.filter.TitleOnlyNumberFilter;


/**
 * Created by benchengzhou on 2018/11/15  10:08 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 设置editText过滤匹配类型
 * 类    名： InputFilterHelper
 * 备    注：
 */

public class InputFilterHelper {

    /**
     * 无表情，可以换行
     * 给定一个editText ,向其中插入表情过滤
     * 这个操作会保留原有的filter 属性不变
     */
    public static void setFilterTextEnterNoEmoji(EditText edit) {
        InputFilter[] filters = edit.getFilters();
        InputFilter[] inputFilters = new InputFilter[filters.length + 1];
        if (filters.length > 0) {
            for (int i = 0; i < filters.length; i++) {
                inputFilters[i] = filters[i];
            }
        }
        inputFilters[inputFilters.length - 1] = new EmojiFilter();
        edit.setFilters(inputFilters);

    }


    /**
     * 无表情，禁止换行
     * 给定一个editText ,向其中插入表情过滤和禁止换行过滤
     * 这个操作会保留原有的filter 属性不变
     */
    public static void setFilterTextNoEmojiNoEnter(EditText edit) {
        InputFilter[] filters = edit.getFilters();
        InputFilter[] inputFilters = new InputFilter[filters.length + 1];
        if (filters.length > 0) {
            for (int i = 0; i < filters.length; i++) {
                inputFilters[i] = filters[i];
            }
        }
        inputFilters[inputFilters.length - 1] = new NameFilter();
        edit.setFilters(inputFilters);

    }

    /**
     * 用户输入过滤器，用户仅可以录入数字和字母
     * 给定一个editText ,向其中插入表情过滤和禁止换行过滤
     * 这个操作会保留原有的filter 属性不变
     */
    public static void setFilterTextOnlyLetterNum(EditText edit) {
        InputFilter[] filters = edit.getFilters();
        InputFilter[] inputFilters = new InputFilter[filters.length + 1];
        if (filters.length > 0) {
            for (int i = 0; i < filters.length; i++) {
                inputFilters[i] = filters[i];
            }
        }
        inputFilters[inputFilters.length - 1] = new TitleOnlyNumLetterFilter();
        edit.setFilters(inputFilters);

    }


    /**
     * 无表情，禁止换行
     * 给定一个editText ,用户仅可以录入汉字和字母
     * 这个操作会保留原有的filter 属性不变
     */
    public static void setFilterOnlyChineseLetter(EditText edit) {
        InputFilter[] filters = edit.getFilters();
        InputFilter[] inputFilters = new InputFilter[filters.length + 1];
        if (filters.length > 0) {
            for (int i = 0; i < filters.length; i++) {
                inputFilters[i] = filters[i];
            }
        }
        inputFilters[inputFilters.length - 1] = new TitleOnlyCharLetterFilter();
        edit.setFilters(inputFilters);

    }


    /**
     * 文本仅保留汉字
     * 给定一个editText ,用户仅可以录入汉字
     * 这个操作会保留原有的filter 属性不变
     */
    public static void setFilterOnlyChinese(EditText edit) {
        InputFilter[] filters = edit.getFilters();
        InputFilter[] inputFilters = new InputFilter[filters.length + 1];
        if (filters.length > 0) {
            for (int i = 0; i < filters.length; i++) {
                inputFilters[i] = filters[i];
            }
        }
        inputFilters[inputFilters.length - 1] = new TitleOnlyCharFilter();
        edit.setFilters(inputFilters);

    }

    /**
     * 禁止EditText输入空格 回车 表情
     * 人名录入用
     *
     * @param editText
     */
    public static void setFilterOnlyNoSpaceNoEmojiNoEnter(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        NameFilter nameFilter = new NameFilter();
        editText.setFilters(new InputFilter[]{filter, nameFilter, new InputFilter.LengthFilter(6)});
    }


    /**
     * 用户金额录入
     */
    public static void setFilterMoney(EditText editText) {
        InputFilter[] filters = editText.getFilters();
        InputFilter[] inputFilters = new InputFilter[filters.length + 1];
        if (filters.length > 0) {
            for (int i = 0; i < filters.length; i++) {
                inputFilters[i] = filters[i];
            }
        }
        inputFilters[inputFilters.length - 1] = new MoneyValueFilter();
        editText.setFilters(inputFilters);
    }

    /**
     * 用户仅可以录入数字
     */
    public static void setFilterOnlyNumber(EditText editText) {
        InputFilter[] filters = editText.getFilters();
        InputFilter[] inputFilters = new InputFilter[filters.length + 1];
        if (filters.length > 0) {
            for (int i = 0; i < filters.length; i++) {
                inputFilters[i] = filters[i];
            }
        }
        inputFilters[inputFilters.length - 1] = new TitleOnlyNumberFilter();
        editText.setFilters(inputFilters);
    }


    /**
     * 设置editText仅仅可以录入汉字和数字
     *
     * @param editText
     */
    public static void setFilterOnlyCharNum(EditText editText) {
        InputFilter[] filters = editText.getFilters();
        InputFilter[] inputFilters = new InputFilter[filters.length + 1];
        if (filters.length > 0) {
            for (int i = 0; i < filters.length; i++) {
                inputFilters[i] = filters[i];
            }
        }
        inputFilters[inputFilters.length - 1] = new TitleOnlyCharNumberFilter();
        editText.setFilters(inputFilters);
    }

    /**
     * 设置editText仅仅可以录入汉字、字母和数字
     *
     * @param editText
     */
    public static void setFilterOnlyCharLetterNum(EditText editText) {
        InputFilter[] filters = editText.getFilters();
        InputFilter[] inputFilters = new InputFilter[filters.length + 1];
        if (filters.length > 0) {
            for (int i = 0; i < filters.length; i++) {
                inputFilters[i] = filters[i];
            }
        }
        inputFilters[inputFilters.length - 1] = new TitleOnlyCharLetterNumberFilter();
        editText.setFilters(inputFilters);
    }


    /**
     * 设置editText仅仅可以录入汉字、字母和数字和空格
     *
     * @param editText
     */
    public static void setFilterOnlyCharLetterNumberSpaceFilter(EditText editText) {
        InputFilter[] filters = editText.getFilters();
        InputFilter[] inputFilters = new InputFilter[filters.length + 1];
        if (filters.length > 0) {
            for (int i = 0; i < filters.length; i++) {
                inputFilters[i] = filters[i];
            }
        }
        inputFilters[inputFilters.length - 1] = new TitleOnlyCharLetterNumberSpaceFilter();
        editText.setFilters(inputFilters);
    }

    /**
     * 设置editText 内容类 用户仅可以输入 汉字 字母 数字 特殊符号
     *
     * @param editText
     */
    public static void setFilterOnlyCharLetterNumberSymbol(EditText editText) {
        InputFilter[] filters = editText.getFilters();
        InputFilter[] inputFilters = new InputFilter[filters.length + 1];
        if (filters.length > 0) {
            for (int i = 0; i < filters.length; i++) {
                inputFilters[i] = filters[i];
            }
        }
        inputFilters[inputFilters.length - 1] = new TitleOnlyCharLetterNumberSymbolFilter();
        editText.setFilters(inputFilters);
    }

    /**
     * 设置editText 输入一位小数
     *
     * @param editText
     */
    public static void setFilterOneDecimals(EditText editText) {
        InputFilter[] filters = editText.getFilters();
        InputFilter[] inputFilters = new InputFilter[filters.length + 1];
        if (filters.length > 0) {
            for (int i = 0; i < filters.length; i++) {
                inputFilters[i] = filters[i];
            }
        }
        inputFilters[inputFilters.length - 1] = new OneDecimalsFilter();
        editText.setFilters(inputFilters);
    }



}
