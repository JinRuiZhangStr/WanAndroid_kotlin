package com.zjrdev.wanandroid.ui.base

import com.zjrdev.wanandroid.vm.base.BaseViewModel

/**
 *Created by 张金瑞.
 *Data: 2020-12-17
 */
abstract class BaseVMFragment<VM: BaseViewModel>: BaseFragment() {

    protected lateinit var mViewModel: VM

    override fun onFragmentFirstVisible() {
        mViewModel = initVM()
        startObserve()
        super.onFragmentFirstVisible()
    }

    abstract fun initVM(): VM

    abstract fun startObserve()
}