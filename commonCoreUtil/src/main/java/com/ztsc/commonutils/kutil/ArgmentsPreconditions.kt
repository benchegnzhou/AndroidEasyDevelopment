package zbc.com.cn.utils

import androidx.annotation.Nullable
import org.jetbrains.annotations.NotNull

fun <T> checkNotNull(@Nullable t: T,@NotNull message:String) {
    if (t==null) {
        IllegalArgumentException(message)
    }
}

fun main(args: Array<String>) {

    println(checkNotNull("","not be null"))
}