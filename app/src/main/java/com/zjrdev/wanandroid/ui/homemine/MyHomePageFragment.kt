package com.zjrdev.wanandroid.ui.homemine

import android.view.View
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.ext.OnLazyClickListener
import com.zjrdev.wanandroid.ui.base.BaseFragment
import com.zjrdev.wanandroid.ui.base.BaseVMFragment
import com.zjrdev.wanandroid.vm.MyHomePageViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 *Created by 张金瑞.
 *Data: 2020-12-21
 */
class MyHomePageFragment: BaseVMFragment<MyHomePageViewModel>(),OnLazyClickListener {
    override fun setLayoutResId(): Int = R.layout.fragment_my_home_page
    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }

    override fun initVM(): MyHomePageViewModel = getViewModel()

    override fun startObserve() {
        TODO("Not yet implemented")
    }

    override fun onLazyClick(v: View?) {

    }


}