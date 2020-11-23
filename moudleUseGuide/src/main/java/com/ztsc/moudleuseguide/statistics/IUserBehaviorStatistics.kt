package com.ztsc.house.statistics

import android.content.Context
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.ztsc.moudleuseguide.helper.JGJanalyticsHelper

/**
 * Created by benchengzhou on 2020/8/26  13:53 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名：IUserBehaviorStatistics
 * 备    注：
 */

interface IUserBehaviorStatistics {
    fun onUserOpen(@NonNull context: Context, @NonNull eventId: String, @Nullable pageName: String)
    fun onUserOpen(@NonNull context: Context, @NonNull eventId: JGJanalyticsHelper.CustomEventId, @Nullable pageName: String)
    fun onUserOpen(@NonNull context: Context, @NonNull eventId: JGJanalyticsHelper.CustomEventId, @Nullable pageName: String,@NonNull  jgEvent: JGJanalyticsHelper.JGEvent)
}