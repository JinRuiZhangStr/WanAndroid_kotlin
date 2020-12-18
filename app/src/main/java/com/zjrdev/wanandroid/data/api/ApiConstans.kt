package com.zjrdev.wanandroid.data.api

/**
 *Created by 张金瑞.
 *Data: 2020-12-17
 */
const val WAN_ANDROID = 1
const val wanAndroidUrl = "https://www.wanandroid.com"
const val GANK_IO = 2
const val gankIoUrl = "https://gank.io"

fun getHost(hostType: Int): String {
    lateinit var host: String
    when (hostType) {
        WAN_ANDROID -> host = wanAndroidUrl
        GANK_IO -> host = gankIoUrl
    }

    return host
}