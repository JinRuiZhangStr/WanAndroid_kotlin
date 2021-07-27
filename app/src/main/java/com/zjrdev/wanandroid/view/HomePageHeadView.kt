package com.zjrdev.wanandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.ui.homepage.HomePageFragment
import kotlinx.android.synthetic.main.layout_banner.view.*
import org.jetbrains.anko.include

class HomePageHeadView @JvmOverloads constructor(
    context: Context?,
    homePageStickAdapter: HomePageFragment.HomePageStickAdapter,
    attrs: AttributeSet?= null,
    defStyleAttr: Int = 0
) : LinearLayout(context,attrs,defStyleAttr){
    init {
        include<View>(R.layout.layout_banner)
        rvHomePageHeadView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = homePageStickAdapter
        }

        homePageStickAdapter.run {
            isAnimationFirstOnly = true
            setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
            setOnItemClickListener{adapter, view, position ->
                val article = data[position]
//                context?.startActivity<Details>()
            }
        }

    }
}