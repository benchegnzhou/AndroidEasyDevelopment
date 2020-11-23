package com.ztsc.commonutils.parameterutil;

import android.text.TextUtils;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by benchengzhou on 2020/3/26  14:14 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 提交参数检查类
 * 类    名： ParameterCheckUtils
 * 备    注：
 */

public class ParameterCheckUtils {

    private ParameterCheckUtils() {
    }

    /**
     * 参数空检查
     *
     * @param parameter   需要检查的参数
     * @param reminderMsg 出错后需要弹出的提示
     */
    public static void checkStrNull(String parameter, @NonNull String reminderMsg) {
        if (TextUtils.isEmpty(parameter)) {
            throw new IllegalArgumentException(reminderMsg + "");
        }
    }

    /**
     * 参数空检查
     * 通过后
     * 参数长度不足检查
     *
     * @param parameter        需要检查的参数
     * @param reminderMsgNull  长度0出错后需要弹出的提示
     * @param minLength        要求的最小长度
     * @param reminderMsgShort 长度不足后的提示
     */
    public static void checkStrNullOrShortLength(String parameter, @NonNull String reminderMsgNull,
                                                 @IntRange(from = 0, to = 9223372036854775807L) int minLength,
                                                 @NonNull String reminderMsgShort) {

        checkStrNull(parameter, reminderMsgNull);

        if (parameter.length() < minLength) {
            throw new IllegalArgumentException(reminderMsgShort + "");
        }
    }


    /**
     * 参数空检查
     * 通过后
     * 检查身份证合法性
     *
     * @param idCard           身份证号码
     * @param reminderMsgEmpty 空数据提示
     */
    public static void checkIdCardNum(String idCard,
                                      @NonNull String reminderMsgEmpty) {
        checkIdCardNum(idCard, reminderMsgEmpty, "");
    }

    /**
     * 参数空检查
     * 通过后
     * 检查身份证合法性
     *
     * @param idCard           身份证号码
     * @param reminderMsgEmpty 空数据提示
     * @param reminderMsgError 不合法提示
     */
    public static void checkIdCardNum(String idCard,
                                      @NonNull String reminderMsgEmpty,
                                      @Nullable String reminderMsgError) {

        checkStrNull(idCard, reminderMsgEmpty);


        if (TextUtils.isEmpty(reminderMsgError)) {
            reminderMsgError = "身份证号码无效，请使用第二代身份证！！！";
        }

        if (!IdentityCardVerifyUtil.checkCardID(idCard)) {
            throw new IllegalArgumentException(reminderMsgError + "");
        }
    }

    /**
     * 参数空检查
     * 通过后
     * 检查手机号合法性
     *
     * @param mobiles          手机号码
     * @param reminderMsgEmpty 空数据提示
     * @param reminderMsgError 不合法提示
     */
    public static void checkMobileNum(String mobiles,
                                      @NonNull String reminderMsgEmpty,
                                      @Nullable String reminderMsgError) {

        checkStrNullOrShortLength(mobiles, reminderMsgEmpty, 11, reminderMsgError + "");

        if (mobiles.startsWith("13") || mobiles.startsWith("14") || mobiles.startsWith("15") ||
                mobiles.startsWith("16") || mobiles.startsWith("17") || mobiles.startsWith("19")) {
            throw new IllegalArgumentException(reminderMsgError + "");
        }
    }


}
