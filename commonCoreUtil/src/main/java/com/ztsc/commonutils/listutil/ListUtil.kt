package com.ztsc.commonutils.listutil

class ListUtil {
    companion object {
        /**
         * @return
         */
        @JvmStatic
        fun isEmpty(list: Collection<*>?): Boolean {
            return list == null || list.size == 0
        }

        /**
         * @return
         */
        @JvmStatic
        fun isNotEmpty(list: Collection<*>?): Boolean {
            return list != null && list.size > 0
        }
    }


}