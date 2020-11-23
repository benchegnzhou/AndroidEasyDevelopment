package com.ztsc.commonutils.parametercheckutils

import android.text.TextUtils
import androidx.annotation.IntRange
import com.ztsc.commonutils.parameterutil.IdentityCardVerifyUtil

/**
 * Created by benchengzhou on 2020/3/26  14:14 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 提交参数检查类
 * 类    名： ParameterCheckUtils
 * 备    注：
 */

class ParameterCheck private constructor() {
    companion object {

        /**
         * 参数空检查
         *
         * @param parameter   需要检查的参数
         * @param reminderMsg 出错后需要弹出的提示
         */
        @JvmStatic
        fun checkStrNull(parameter: CharSequence?, reminderMsg: CharSequence) {
            require(!TextUtils.isEmpty(parameter)) { reminderMsg.toString() + "" }
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
        @JvmStatic
        fun checkStrNullOrShortLength(
            parameter: String, reminderMsgNull: String,
            @IntRange(from = 0, to = 9223372036854775807L) minLength: Int,
            reminderMsgShort: String
        ) {
            checkStrNull(parameter, reminderMsgNull)
            require(parameter.length >= minLength) { reminderMsgShort + "" }
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
        @JvmStatic
        fun checkStrNullOrShortLength(
            parameter: CharSequence, reminderMsgNull: CharSequence,
            @IntRange(from = 0, to = 9223372036854775807L) minLength: Int,
            reminderMsgShort: CharSequence
        ) {
            checkStrNull(parameter, reminderMsgNull)
            require(parameter.length >= minLength) { "${reminderMsgShort?.toString()}" }
        }


        /**
         * 参数空检查
         * 通过后
         * 检查身份证合法性
         *
         * @param idCard           身份证号码
         * @param reminderMsgEmpty 空数据提示
         */
        @JvmStatic
        fun checkIdCardNum(
            idCard: String?,
            reminderMsgEmpty: String
        ) {
            checkIdCardNum(idCard, reminderMsgEmpty, "")
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
        @JvmStatic
        fun checkIdCardNum(
            idCard: String?,
            reminderMsgEmpty: String,
            reminderMsgError: String?
        ) {
            var reminderMsgError = reminderMsgError
            checkStrNull(idCard, reminderMsgEmpty)
            if (TextUtils.isEmpty(reminderMsgError)) {
                reminderMsgError = "身份证号码无效，请使用第二代身份证！！！"
            }
            require(IdentityCardVerifyUtil.checkCardID(idCard)) { reminderMsgError + "" }
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
        @JvmStatic
        fun checkMobileNum(
            mobiles: String,
            reminderMsgEmpty: String,
            reminderMsgError: String?
        ) {
            checkStrNullOrShortLength(mobiles, reminderMsgEmpty, 11, reminderMsgError + "")
            require(
                !(mobiles.startsWith("13") || mobiles.startsWith("14") || mobiles.startsWith("15") ||
                        mobiles.startsWith("16") || mobiles.startsWith("17") || mobiles.startsWith("19"))
            ) { reminderMsgError + "" }
        }


    }
}