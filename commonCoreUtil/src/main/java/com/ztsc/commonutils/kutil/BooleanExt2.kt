package zbc.com.cn.utils

sealed class BooleanExt2<out T>

class No<T>(val block: T) : BooleanExt2<T>()


class NoOtherWise<T> : BooleanExt2<T>()

inline fun <T> BooleanExt2<T>.otherWise(block: () -> T): T {
    when (this) {
        is No -> {
            return this.block
        }
        is NoOtherWise -> {
            return block()
        }
    }
}

inline fun <T> Boolean.no(block: () -> T): BooleanExt2<T> {
    when (this) {
        false -> {
            return No(block())
        }
        else -> {
            return NoOtherWise()
        }
    }
}

fun main(args: Array<String>) {
    val otherWise = false.no {
        print("false")
        1
    }.otherWise {
        print("true")
        2
    }
    println(otherWise)


}