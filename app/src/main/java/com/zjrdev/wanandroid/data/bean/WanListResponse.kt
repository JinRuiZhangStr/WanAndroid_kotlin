package com.zjrdev.wanandroid.data.bean

import java.io.Serializable

/**
 *Created by 张金瑞.
 *Data: 2021-2-23
 */
data class WanListResponse<T>(
    val offset: Int,
    val size: Int,
    val total: Int,
    val pageCount: Int,
    val curPage: Int,
    val over: Boolean,
    val datas: T
) : Serializable
