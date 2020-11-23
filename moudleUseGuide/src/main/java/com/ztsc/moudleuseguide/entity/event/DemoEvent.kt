package com.ztsc.moudleuseguide.entity.event

import com.jeremyliao.liveeventbus.core.LiveEvent
/**
 * Created by benchengzhou on 2020/10/29  23:27 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 
 * 类    名： 消息事件
 * 备    注： 
 */

class DemoEvent : LiveEvent {
    lateinit var event: String

    constructor(msg: String)
}