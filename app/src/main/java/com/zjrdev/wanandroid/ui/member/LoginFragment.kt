package com.zjrdev.wanandroid.ui.member

import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.ui.base.BaseVMFragment
import com.zjrdev.wanandroid.vm.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 *Created by 张金瑞.
 *Data: 2020-12-31
 */
class LoginFragment: BaseVMFragment<LoginViewModel>() {
    override fun initVM(): LoginViewModel  = getViewModel()

    override fun startObserve() {
        TODO("Not yet implemented")
    }

    override fun setLayoutResId(): Int  = R.layout.fragment_login

    override fun initView() {

    }

    override fun initData() {

    }
}