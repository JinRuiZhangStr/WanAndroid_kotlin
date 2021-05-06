package com.zjrdev.wanandroid.util

import androidx.lifecycle.LiveData
import com.zjrdev.wanandroid.view.loadpage.LoadPageStatus

/**
 *Created by 张金瑞.
 *Data: 2020-12-22
 */

data class ListModel<T>(
    val showSuccess: List<T>?=null,
    val showLoading: Boolean = false,
    val showError: String? = null,
    val loadPageStatus: LiveData<LoadPageStatus> ?= null,
    val showEnd: Boolean = false,//加载更多
    val isRefresh: Boolean = false,//刷新
    val isRefreshSuccess: Boolean = true//是否刷新成功
)