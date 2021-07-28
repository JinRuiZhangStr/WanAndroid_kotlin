package com.zjrdev.wanandroid.ui.homepage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.adapter.HomePageAdapter
import com.zjrdev.wanandroid.ui.MainActivity
import com.zjrdev.wanandroid.ui.base.BaseVMFragment
import com.zjrdev.wanandroid.view.loadpage.BasePageViewForStatus
import com.zjrdev.wanandroid.view.loadpage.LoadPageViewForStatus
import com.zjrdev.wanandroid.vm.HomeProjectViewModel
import kotlinx.android.synthetic.main.fragment_main_project.*
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 *Created by 张金瑞.
 *Data: 2021-7-28
 */
class ProjectTypeFragment: BaseVMFragment<HomeProjectViewModel>(),OnLoadMoreListener {

    companion object {
        private const val CID ="projectCid"
        //获取一个新的fragment
        fun newInstance(cid: Int): ProjectTypeFragment {
            val fragment = ProjectTypeFragment()
            val bundle = Bundle()
            bundle.putInt(CID,cid)
            fragment.arguments = bundle
            return fragment
        }
    }

    // cid == 0 是最新项目，否项目分类
    private val cid by lazy { arguments?.getInt(CID) }
    // 列表recyclerview适配器
    private val homePageAdapter = HomePageAdapter()
    private val loadPageViewForStatus: BasePageViewForStatus by inject()
    private var rootView: LoadPageViewForStatus ?= null
    private var i: Int = 0


    override fun initVM(): HomeProjectViewModel = getViewModel()

    override fun startObserve() {
        mViewModel.run {
            mProjectListModel.observe(this@ProjectTypeFragment, Observer {
                if (it.isRefresh) refreshLayout.finishRefresh(it.isRefreshSuccess)
                if (it.showEnd)  homePageAdapter.loadMoreModule.loadMoreEnd()
                it.loadPageStatus?.value?.let { loadPageStatus ->
                    rootView?.let { rootView ->
                        loadPageViewForStatus.convert(
                            rootView,
                            loadPageStatus = loadPageStatus
                        )
                        homePageAdapter.setEmptyView(rootView)
                    }
                }
                it.showSuccess?.let { list ->
                    homePageAdapter.apply {
                        loadMoreModule.isEnableLoadMore = false
                        if (it.isRefresh) setList(list) else addData(list)
                        loadMoreModule.isEnableLoadMore = true
                        loadMoreModule.loadMoreComplete()
                    }
                }

                it.showError?.let {
                    homePageAdapter.loadMoreModule.loadMoreFail()
                }
            })
        }
    }

    override fun setLayoutResId(): Int = R.layout.fragment_recyclerview

    override fun initView() {
        rootView = (loadPageViewForStatus.getRootView(activity as MainActivity) as LoadPageViewForStatus).apply {
            failTextView().onClick { refresh() }
            noNetTextView().onClick { refresh() }
        }
        ArticleRv.apply {
            adapter = homePageAdapter
        }

        homePageAdapter.apply {
            loadMoreModule.setOnLoadMoreListener(this@ProjectTypeFragment)
            isAnimationFirstOnly = true
            setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)
            setOnItemChildClickListener{adapter, view, position ->
                val article = data[position]
            }
        }
        refreshLayout.setOnRefreshListener { refresh() }
        refreshLayout.setEnableLoadMore(true)
    }

    override fun initData() {
        //viewpager 缓存4个界面，界面重新加载的时候清空数据重新获取
        if (i !=0) {
            homePageAdapter.setList(null)
        }
        refresh()
        i++
    }

    private fun refresh() {
        cid?.let {
            mViewModel.loadProjectArticles(
                true,
                it
            )
        }
    }

    override fun onLoadMore() {
        cid?.let {
            mViewModel.loadProjectArticles(false,it)
        }
    }
}