package com.zjrdev.wanandroid.ui

import android.os.Bundle
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.ui.base.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/**
 *Created by 张金瑞.
 *Data: 2020-12-17
 */
class WelcomeActivity: BaseActivity(), CoroutineScope by MainScope() {
    override fun setLayoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun initView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }
}