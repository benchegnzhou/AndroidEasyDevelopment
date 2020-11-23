package com.ztsc.moudleuseguide.entity.bean

import com.ztsc.commonutils.annotations.Poko
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import java.lang.StringBuilder

@Poko
class DemoBean :Serializable{
    var id: Int = 0
    var msg: String? = null
    var desc: String? = null

    constructor(@NotNull id: Int, msg: String?, desc: String?) {
        this.id = id
        this.msg = msg
        this.desc = desc
    }

    override fun toString(): String {
        return StringBuilder().append("[ id = ").append(id).append(" , ").append("msg = ")
            .append(msg).append(" , ").append("desc = ").append(desc).append(" ]").toString()
    }
}