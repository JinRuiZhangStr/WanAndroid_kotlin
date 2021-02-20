package com.zjrdev.wanandroid.ui.homepage

import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.adapter.HomePageAdapter
import com.zjrdev.wanandroid.ui.base.BaseVMFragment
import com.zjrdev.wanandroid.vm.HomePageViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 *Created by 张金瑞.
 *Data: 2021-1-26
 */
class HomePageFragment: BaseVMFragment<HomePageViewModel>() {

    //
    private val homePageAdapter = HomePageAdapter()


    override fun initVM(): HomePageViewModel = getViewModel()

    override fun startObserve() {

    }

    override fun setLayoutResId(): Int  = R.layout.fragment_recyclerview

    override fun initView() {

    }

    override fun initData() {

    }
}