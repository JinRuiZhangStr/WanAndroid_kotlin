package com.zjrdev.wanandroid.view.loadpage

import android.app.Activity
import android.view.View

/**
 *Created by 张金瑞.
 *Data: 2021-7-27
 * 自定义列表加载界面
 */
class SimplePageViewForStatus: BasePageViewForStatus() {
    override fun getRootView(activity: Activity): View {
        return LoadPageViewForStatus(activity)
    }

    override fun getLoadingView(LoadPageViewForStatus: LoadPageViewForStatus): View {
        return LoadPageViewForStatus.progressBarView()
    }

    override fun getLoadFailView(LoadPageViewForStatus: LoadPageViewForStatus): View {
        return LoadPageViewForStatus.failTextView()
    }

    override fun getLoadEmptyView(LoadPageViewForStatus: LoadPageViewForStatus): View {
        return LoadPageViewForStatus.emptyTextView()
    }

    override fun getLoadNoNetView(LoadPageViewForStatus: LoadPageViewForStatus): View {
        return LoadPageViewForStatus.noNetTextView()
    }
}