package com.zjrdev.wanandroid.ui.homepage

import androidx.viewpager2.widget.ViewPager2
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.adapter.MyFragmentPagerAdapter
import com.zjrdev.wanandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

/**
 *Created by 张金瑞.
 *Data: 2020-12-21
 */
class MainFragment : BaseFragment() {
    override fun setLayoutResId(): Int = R.layout.fragment_main

    override fun initView() {
        val mHomePageTabLayout = arrayOf(
            context?.getString(R.string.home_page),
            context?.getString(R.string.home_project)
        )
        homePageNv.setTabLayoutData(mHomePageTabLayout, homePageVp)
    }

    override fun initData() {

        val mainPageFragment = HomePageFragment()
        val mainProjectFragment = HomeProjectFragment()

        val fragments = listOf(mainPageFragment,mainProjectFragment)
        homePageVp.adapter = MyFragmentPagerAdapter(requireActivity(),fragments )
        homePageVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                homePageVp.isUserInputEnabled = position == 0
            }
        })
    }
}