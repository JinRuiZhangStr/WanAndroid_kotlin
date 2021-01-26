package com.zjrdev.wanandroid.util

/**
 *Created by 张金瑞.
 *Data: 2020-12-22
 */

data class ListModel<T>(
    val showSuccess: List<T>?=null,
    val showLoading: Boolean = false,
    val showEnd: Boolean = false,
    val showError: String? = null
)