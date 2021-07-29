package com.zjrdev.wanandroid.ui.homemine

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.adapter.FunctionAdapter
import com.zjrdev.wanandroid.data.bean.MyFunction
import com.zjrdev.wanandroid.ext.OnLazyClickListener
import com.zjrdev.wanandroid.ext.text
import com.zjrdev.wanandroid.ui.base.BaseFragment
import com.zjrdev.wanandroid.ui.base.BaseVMFragment
import com.zjrdev.wanandroid.ui.homemine.activity.MyThemeActivity
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

    private var functions = mutableListOf<MyFunction>()
    private val functionAdapter = FunctionAdapter()

    override fun setLayoutResId(): Int = R.layout.fragment_my_home_page
    override fun initView() {
        tvLoginImmediately.setOnClickListener(this)

        rvMyFunction.apply {
            adapter = functionAdapter
        }

        functionAdapter.setOnItemClickListener { _, view, position ->
            when(position) {
                4->{
                    startActivity<MyThemeActivity>()
                }

            }
        }
    }

    override fun initData() {
        activity?.let {
            functions.add(MyFunction(R.drawable.ic_outline_share, it.text(R.string.my_share)))
            functions.add(MyFunction(R.drawable.ic_my_collect, it.text(R.string.my_collect)))
            functions.add(MyFunction(R.drawable.ic_ku_tu, it.text(R.string.my_ku_tu)))
            functions.add(MyFunction(R.drawable.ic_my_tool, it.text(R.string.my_tool)))
            functions.add(MyFunction(R.drawable.ic_theme, it.text(R.string.theme)))

            functionAdapter.setList(functions)
        }
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