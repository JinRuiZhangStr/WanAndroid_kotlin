package com.zjrdev.wanandroid.ui.homemine

import android.view.View
import androidx.lifecycle.Observer
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.ext.OnLazyClickListener
import com.zjrdev.wanandroid.ui.base.BaseFragment
import com.zjrdev.wanandroid.ui.base.BaseVMFragment
import com.zjrdev.wanandroid.ui.member.LoginActivity
import com.zjrdev.wanandroid.vm.MyHomePageViewModel
import kotlinx.android.synthetic.main.fragment_my_home_page.*
import org.jetbrains.anko.support.v4.startActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 *Created by 张金瑞.
 *Data: 2020-12-21
 */
class MyHomePageFragment: BaseVMFragment<MyHomePageViewModel>(),OnLazyClickListener {
    override fun setLayoutResId(): Int = R.layout.fragment_my_home_page
    override fun initView() {
        tvLoginImmediately.setOnClickListener(this)
    }

    override fun initData() {

    }

    override fun initVM(): MyHomePageViewModel = getViewModel()

    override fun startObserve() {
        mViewModel.run {
            userLiveData.observe(viewLifecycleOwner, Observer {

            })
        }
    }

    override fun onLazyClick(v: View?) {
        when (v?.id) {
            R.id.tvLoginImmediately -> startActivity<LoginActivity>()
        }
    }


}