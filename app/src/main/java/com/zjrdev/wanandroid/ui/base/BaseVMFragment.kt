package com.zjrdev.wanandroid.ui.base

import androidx.lifecycle.ViewModel

/**
 *Created by 张金瑞.
 *Data: 2020-12-17
 */
abstract class BaseVMFragment<VM: ViewModel>: BaseFragment() {

    protected lateinit var mViewModel: VM

    override fun onFragmentFirstVisible() {
        mViewModel = initVM()
        startObserve()
        super.onFragmentFirstVisible()
    }

    abstract fun initVM(): VM

    abstract fun startObserve()
}