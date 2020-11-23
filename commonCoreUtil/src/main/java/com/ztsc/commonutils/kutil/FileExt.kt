package zbc.com.cn.utils

import java.io.File
import java.lang.Exception

/**
 * 多级目录文件创建
 */
fun File.ensureDir(): Boolean {
    try {
        isDirectory.no {
            isFile.yes {
                delete()
            }
        }
        return mkdirs()
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}