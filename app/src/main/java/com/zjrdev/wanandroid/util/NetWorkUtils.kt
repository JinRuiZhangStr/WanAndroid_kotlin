package com.zjrdev.wanandroid.util

import com.zjrdev.wanandroid.data.bean.base.ResultData
import java.lang.Exception

/**
 *Created by 张金瑞.
 *Data: 2021-1-5
 */
suspend fun <T: Any> safaApiCall(call: suspend () -> ResultData<T>): ResultData<T> {

    return try {
        call()
    } catch (e: Exception) {
        ResultData.Error(e)
    }
}