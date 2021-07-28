package com.zjrdev.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import com.zjrdev.wanandroid.data.bean.Article
import com.zjrdev.wanandroid.data.bean.ClassifyResponse
import com.zjrdev.wanandroid.data.repository.ArticleUserCase
import com.zjrdev.wanandroid.data.repository.ProjectRepository
import com.zjrdev.wanandroid.util.ListModel
import com.zjrdev.wanandroid.view.loadpage.LoadPageStatus
import com.zjrdev.wanandroid.vm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Created by 张金瑞.
 *Data: 2021-1-26
 */
class HomeProjectViewModel(
    private val projectRepository: ProjectRepository,
    private val articaleUserCase: ArticleUserCase
): BaseViewModel() {

    val mMainProjectListModel = MutableLiveData<ListModel<ClassifyResponse>>()
    val mProjectListModel = MutableLiveData<ListModel<Article>>()
    private val mProjectLoadPageStatus = MutableLiveData<LoadPageStatus>()

    fun loadProjectClassify() = launchUI {
        withContext(Dispatchers.IO) {
            projectRepository.getProjectClassify(mMainProjectListModel)
        }
    }

    fun loadProjectArticles(isRefresh: Boolean = false,cid: Int = 0) = launchUI {
        withContext(Dispatchers.IO) {
            when (cid ) {
                //如果 cid == 0 查询最新项目
                0 -> {
                    articaleUserCase.getLatesProjectList(
                        isRefresh,
                        mProjectListModel,
                        mProjectLoadPageStatus
                    )
                }
                //分类项目数据
                else -> {
                    articaleUserCase.getProjectDetailList(
                        isRefresh,
                        mProjectListModel,
                        mProjectLoadPageStatus,
                        cid
                    )
                 }
            }
        }
    }
}