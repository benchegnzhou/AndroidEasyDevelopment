package com.ztsc.commonutils.stringutils
/**
 * Created by benchengzhou on 2020/4/17  13:28 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： String字符串操作工具类
 * 类    名： StringUtils
 * 备    注：
 */

class StringUtils {

    companion object{
        /**
         * 判断一个字符串是不是空字符串
         *
         * @param str
         * @return
         */
        @JvmStatic
        fun isEmpty(str: String?): Boolean {
            return str == null || str.length == 0
        }


        /**
         * 判读字符不为空
         *
         * @param str
         * @return
         */
        @JvmStatic
        private fun isNotEmpty(str: String?): Boolean {
            return str != null && str.length > 0
        }

    }

}