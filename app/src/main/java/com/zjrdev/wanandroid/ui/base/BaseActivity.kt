package com.zjrdev.wanandroid.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zjrdev.wanandroid.ext.getAppTheme

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(getAppTheme())
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
    }

    abstract fun setLayoutId(): Int
    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initData()

    /**
     * 沉浸式状态栏  open为方法增加，该方法表示可以重写
     */
    open fun initImmersionBar() {

    }

    protected open fun initActivity(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        initData()
    }
}