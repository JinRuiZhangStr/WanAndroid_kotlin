package com.zjrdev.wanandroid.ui.member

import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.ui.base.BaseVMFragment
import com.zjrdev.wanandroid.vm.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 *Created by 张金瑞.
 *Data: 2020-12-31
 */
class RegisterFragment: BaseVMFragment<RegisterViewModel>() {
    override fun initVM(): RegisterViewModel {
        return getViewModel()
    }

    override fun startObserve() {

    }

    override fun setLayoutResId(): Int  = R.layout.fragment_register

    override fun initView() {

    }

    override fun initData() {

    }
}