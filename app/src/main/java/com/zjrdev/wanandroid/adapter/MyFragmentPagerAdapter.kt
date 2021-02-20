package com.zjrdev.wanandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 *Created by 张金瑞.
 *Data: 2021-1-26
 */
class MyFragmentPagerAdapter(fragmentActivity: FragmentActivity,private val fragments: List<Fragment>): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}