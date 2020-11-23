package com.ztsc.commonutils.logcat

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.orhanobut.logger.Logger

class LoggerUtil {
    companion object {
        @JvmStatic
        fun i(
            @NonNull message: String,
            @Nullable vararg args: Any?
        ) {
            Logger.i(message, *args)
        }

        @JvmStatic
        fun d(
            @NonNull message: String,
            @Nullable vararg args: Any?
        ) {
            Logger.d(message, *args)
        }
        @JvmStatic
        fun d(@Nullable any: Any?) {
            Logger.d(any)
        }
        @JvmStatic
        fun e(
            @NonNull message: String,
            @Nullable vararg args: Any?
        ) {
            Logger.e(null, message, *args)
        }
        @JvmStatic
        fun e(
            @Nullable throwable: Throwable?,
            @NonNull message: String,
            @Nullable vararg args: Any?
        ) {
            Logger.e(throwable, message, *args)
        }

        @JvmStatic
        fun v(
            @NonNull message: String,
            @Nullable vararg args: Any?
        ) {
            Logger.v(message, *args)
        }
        @JvmStatic
        fun w(
            @NonNull message: String,
            @Nullable vararg args: Any?
        ) {
            Logger.w(message!!, *args)
        }

        /**
         * Tip: Use this for exceptional situations to log
         * ie: Unexpected errors etc
         */
        @JvmStatic
        fun wtf(
            @NonNull message: String,
            @Nullable vararg args: Any?
        ) {
            Logger.wtf(message, *args)
        }

        /**
         * Formats the given json content and print it
         */
        @JvmStatic
        fun json(@Nullable json: String) {
            Logger.json(json)
        }

        /**
         * Formats the given xml content and print it
         */
        @JvmStatic
        fun xml(@Nullable xml: String?) {
            Logger.xml(xml)
        }


    }
}