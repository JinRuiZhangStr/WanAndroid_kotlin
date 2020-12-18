package com.zjrdev.wanandroid.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.zjrdev.wanandroid.ui.main.ProgressDialogFragment

/**
 *Created by 张金瑞.
 *Data: 2020-12-17
 */
abstract class BaseFragment :Fragment() {

    private lateinit var progressDialogFragment: ProgressDialogFragment
    // 简化版 fragment懒加载
    private var isLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(setLayoutResId(),container,false)
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            onFragmentFirstVisible()
            isLoaded = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isLoaded = false
    }

    abstract fun setLayoutResId() : Int

    abstract fun initView()

    abstract fun initData()

    protected open fun onFragmentFirstVisible() {
        initView()
        initData()
    }

    /**
     * 显示加载对话框
     */
    fun showProgressDialog(@StringRes message: Int) {
        if (!this::progressDialogFragment.isInitialized) {
            progressDialogFragment = ProgressDialogFragment.newInstance()
        }

        if (!progressDialogFragment.isAdded) {
            /**
             * 内联扩展函数 let ?.判断 activity?.supportFragmentManager不为null的条件下，才会执行let函数体
             * 两种写法都是一样的效果，必须保证 activity?.supportFragmentManager 不为null
             */

            activity?.supportFragmentManager?.let { progressDialogFragment.show(it,message,false) }
//            progressDialogFragment.show(activity!!.supportFragmentManager,message,false)
        }
    }

    /**
     * 隐藏加载对话框
     */
    fun dismissProgressDialog() {
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) {
            progressDialogFragment.dismissAllowingStateLoss()
        }
    }
}