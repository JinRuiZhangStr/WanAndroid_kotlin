package com.zjrdev.wanandroid.ui.homepage

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.adapter.HomePageAdapter
import com.zjrdev.wanandroid.data.bean.Article
import com.zjrdev.wanandroid.ui.MainActivity
import com.zjrdev.wanandroid.ui.base.BaseVMFragment
import com.zjrdev.wanandroid.view.HomePageHeadView
import com.zjrdev.wanandroid.view.loadpage.BasePageViewForStatus
import com.zjrdev.wanandroid.view.loadpage.LoadPageViewForStatus
import com.zjrdev.wanandroid.vm.HomePageViewModel
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 *Created by 张金瑞.
 *Data: 2021-1-26
 */
class HomePageFragment: BaseVMFragment<HomePageViewModel>() {

    private var rootView: LoadPageViewForStatus?= null
    private val loadPageViewForStatus: BasePageViewForStatus by inject()
    private lateinit var homePageHeadView: HomePageHeadView
    private val homePageStickAdapter = HomePageStickAdapter()
    //
    private val homePageAdapter = HomePageAdapter()

    override fun initVM(): HomePageViewModel = getViewModel()

    override fun startObserve() {

    }

    override fun setLayoutResId(): Int  = R.layout.fragment_recyclerview

    override fun initView() {
        rootView = (loadPageViewForStatus.getRootView(activity as MainActivity) as LoadPageViewForStatus).apply {
            failTextView().onClick { refresh() }
            noNetTextView().onClick { refresh() }
        }

        ArticleRv.apply {
            adapter = homePageAdapter
        }
        homePageAdapter.apply {
            homePageHeadView = HomePageHeadView(activity,homePageStickAdapter)

        }
    }

    override fun initData() {
        refresh()
    }

    private fun refresh() {
        mViewModel.loadHomeArticles(true)
    }


    class HomePageStickAdapter:
            BaseQuickAdapter<Article,BaseViewHolder>(R.layout.layout_stick_article) {
        override fun convert(holder: BaseViewHolder, item: Article) {
            TODO("Not yet implemented")
        }

    }
}