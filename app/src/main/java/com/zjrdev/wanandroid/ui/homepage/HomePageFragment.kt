package com.zjrdev.wanandroid.ui.homepage

import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.coder.zzq.smartshow.toast.SmartToast
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.adapter.HomePageAdapter
import com.zjrdev.wanandroid.adapter.ImageAdapter
import com.zjrdev.wanandroid.data.bean.Article
import com.zjrdev.wanandroid.ui.MainActivity
import com.zjrdev.wanandroid.ui.base.BaseVMFragment
import com.zjrdev.wanandroid.view.HomePageHeadView
import com.zjrdev.wanandroid.view.loadpage.BasePageViewForStatus
import com.zjrdev.wanandroid.view.loadpage.LoadPageViewForStatus
import com.zjrdev.wanandroid.vm.HomePageViewModel
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.layout_banner.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 *Created by 张金瑞.
 *Data: 2021-1-26
 */
class HomePageFragment: BaseVMFragment<HomePageViewModel>(),OnLoadMoreListener {

    private var rootView: LoadPageViewForStatus?= null
    private val loadPageViewForStatus: BasePageViewForStatus by inject()
    private lateinit var homePageHeadView: HomePageHeadView
    private val homePageStickAdapter = HomePageStickAdapter()
    //
    private val homePageAdapter = HomePageAdapter()

    override fun initVM(): HomePageViewModel = getViewModel()

    override fun startObserve() {
        mViewModel.run {
            mListModel.observe(this@HomePageFragment, Observer {
                if (it.isRefresh) refreshLayout.finishRefresh(it.isRefreshSuccess)
                if (it.showEnd) homePageAdapter.loadMoreModule.loadMoreEnd()
                it.loadPageStatus?.value?.let {loadPageStatus ->
                    rootView?.let { rootview ->
                        loadPageViewForStatus.convert(
                            rootview,
                            loadPageStatus = loadPageStatus
                        )
                        homePageAdapter.setEmptyView(rootview)

                    }
                }

                it.showSuccess?.let { list ->
                    homePageAdapter.run {
                        //如果是 刷新状态的话，代表数据要重新加载 使用 setList
                        if (it.isRefresh) setList(list) else addData(list)
                        loadMoreModule.isEnableLoadMore = true
                        loadMoreModule.loadMoreComplete()
                        //列表加载成功后再加载banner
                        mViewModel.loadBanner()
                    }
                }

                it.showError?.let { errorMsg ->
                    homePageAdapter.loadMoreModule.loadMoreFail()
                    SmartToast.show(errorMsg)
                }

            })

            mBanner.observe(this@HomePageFragment, Observer {
                banner?.adapter = activity?.let { act ->
                    ImageAdapter(it,act)
                }
                mViewModel.loadStickArticles()
            })

            mStickArticles.observe(this@HomePageFragment, Observer {
                homePageStickAdapter.setList(it)
            })
        }
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
            loadMoreModule.setOnLoadMoreListener(this@HomePageFragment)
            isAnimationFirstOnly = true
            setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)
            activity?.let {
                addHeaderView(homePageHeadView)
            }

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
            item.let {
                holder.setText(R.id.tvStickContent,it.title)
                if ((data.size -1) == holder.layoutPosition) holder.setVisible(
                    R.id.viewDivision,
                    false
                )
            }
        }

    }

    override fun onLoadMore() {
        mViewModel.loadHomeArticles(false)
    }
}