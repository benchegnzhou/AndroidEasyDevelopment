package zbc.com.cn.utils

import android.app.Activity
import android.content.Intent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * reified泛型不擦除函数使用
 * https://www.jianshu.com/p/a3f082633859
 */




/**2.实际使用
 * Activity定义扩展函数
 */
inline fun <reified T : Activity> Activity.startActivityKtx() {
    this.startActivity(Intent(this, T::class.java))
}

inline fun <reified T : Activity> Activity.startActivityKtxWithParam(block: (intent: Intent) -> Any) {
    val intent = Intent(this, T::class.java)
    block.invoke(intent)
    this.startActivity(intent)
}




/**调用
 * 跳转Activity 带参数
 */
//startActivityKtxWithParam<TemplateListActivity> {
//    it.putExtra(TemplateListActivity.KEY_CATEGORY_NAME, datas[position].categoryName)
//}


/**
 * json字符串转对象
 */
inline fun <reified T> String.jsonToObj(): T? {
    return try {
        val g = Gson()
        val type = object : TypeToken<T>() {}.type
        g.fromJson<T>(this, type)
    } catch (e: Exception) {
        null
    }
}

/**
 * json数组转列表
 */
inline fun <reified T> String.jsonToObjList(): ArrayList<T>? {
    return try {
        val g = Gson()
        val type = object : TypeToken<ArrayList<T>>() {}.type
        return g.fromJson<ArrayList<T>>(this, type)
    } catch (e: Exception) {
        null
    }
}

