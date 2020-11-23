package zbc.com.cn.utils



sealed class BooleanExt<out T>

object OtherWise : BooleanExt<Nothing>()

class withData<T>(val data: T) : BooleanExt<T>()

fun <T> BooleanExt<T>.otherwise(block: () -> T): T {
    when (this) {
        is OtherWise -> {
            return block()
        }
        is withData -> {
            return this.data
        }
    }
}


//https://blog.csdn.net/Jaden_hool/article/details/78437947
inline fun <T> Boolean.yes(block: () -> T): BooleanExt<T> {
    when {
        this -> {
            return withData(block())
        }
        else -> {
            return OtherWise
        }
    }
}

fun main(args: Array<String>) {
    val lambda = {
        print("OK")
        1
    }

    val lambda2 = {
        print("NOT OK !")
        2
    }
    val a = true.yes(lambda).otherwise(lambda2)
    print(a)

    for (i in fibonacci){
        println(i)
        //打印的时候，调Next方法，next方法把我们刚刚设置给next的方法返回
        //直到循环厕所超过100的时候，跳出
        if (i > 100)break
    }

}


val fibonacci = sequence {
    yield(1)
    //调这个值的时候，先把1赋给next
    var cur = 1
    var next = 1

    while (true){
        yield(next)
        //协程被挂起
        val tmp = cur + next
        cur = next
        next = tmp
    }
}