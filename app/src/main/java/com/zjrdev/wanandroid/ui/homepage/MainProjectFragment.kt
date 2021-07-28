package com.zjrdev.wanandroid.ui.homepage

import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.jeremyliao.liveeventbus.LiveEventBus
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.data.bean.ClassifyResponse
import com.zjrdev.wanandroid.ext.HOME_PAGE_CUT
import com.zjrdev.wanandroid.ext.inflate
import com.zjrdev.wanandroid.ui.base.BaseVMFragment
import com.zjrdev.wanandroid.view.loadpage.BasePageViewForStatus
import com.zjrdev.wanandroid.vm.HomeProjectViewModel
import kotlinx.android.synthetic.main.fragment_main_project.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 *Created by 张金瑞.
 *Data: 2021-1-26
 *  首页   项目Tab  fragment
 */
class MainProjectFragment: BaseVMFragment<HomeProjectViewModel>() {

    private val mFragmentList = mutableListOf<Fragment>()
    private val loadPageViewForStatus: BasePageViewForStatus by inject()

    override fun initVM(): HomeProjectViewModel {
        return getViewModel ()
    }

    override fun startObserve() {
        mViewModel.run {
            mMainProjectListModel.observe(this@MainProjectFragment, Observer {
                it.loadPageStatus?.value?.let { loadPageStatus ->
                    llMainProjectLoadPageViewForStatus.visibility = View.VISIBLE
                    loadPageViewForStatus.convert(
                        llMainProjectLoadPageViewForStatus,
                        loadPageStatus
                    )
                }
                it.showSuccess?.let { list ->
                    mFragmentList.clear()
                    val classifyResponse = ClassifyResponse(
                        null,0,0,getString(R.string.newest_project),0,0,false,0
                    )
                    llMainProjectLoadPageViewForStatus.visibility = View.GONE
                    tlMainProject.removeAllViews()
                    list.toMutableList().apply {
                        add(0,classifyResponse)
                        forEach{
                            tlMainProject?.let { tlMainProject ->
                                tlMainProject.inflate(R.layout.layout_project_tab,false).apply {
                                    findViewById<TextView>(R.id.tvTabLayoutTitle)?.text = it.name
                                    tlMainProject.addView(this)
                                }
                            }

                        }
                    }
                }
            })
        }
    }

    override fun setLayoutResId(): Int  = R.layout.fragment_main_project

    override fun initView() {
        llMainProjectLoadPageViewForStatus.failTextView().onClick { mViewModel.loadProjectClassify() }
        ViewPager2Delegate(vpMainProject,tlMainProject)
        vpMainProject.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            private var currentPosition = 0  //当前滑动位置
            private var oldPosition = 0  //上一个滑动位置

            override fun onPageScrollStateChanged(state: Int) {
                if (state == 0) {
                    if (currentPosition == oldPosition) {
                        if (currentPosition == 0) {
                            LiveEventBus.get(HOME_PAGE_CUT).post("")
                        }
                    }
                    oldPosition = currentPosition
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                currentPosition = position
            }
        })
    }

    override fun initData() {
        mViewModel.loadProjectClassify()
    }
}