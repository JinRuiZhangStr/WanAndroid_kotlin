package com.zjrdev.wanandroid.data.repository

import androidx.lifecycle.MutableLiveData
import com.zjrdev.wanandroid.data.bean.Article
import com.zjrdev.wanandroid.data.bean.base.ResultData
import com.zjrdev.wanandroid.data.repository.datasource.RemoteDataSource
import com.zjrdev.wanandroid.util.ListModel
import com.zjrdev.wanandroid.view.loadpage.LoadPageStatus
import java.net.UnknownHostException

/**
 *Created by 张金瑞.
 *Data: 2021-2-22
 */
class ArticleUserCase(private val remoteDataSource: RemoteDataSource) {
    private var currentPage = 0  //分页加载
    private var currentKeywords = ""

    /**
     * sealed  java中的枚举enum
     */
    sealed class ArticleType {
        object Home: ArticleType() // 首页
        object LatestProject : ArticleType() //最新项目
        object ProjectDetailList : ArticleType() //项目列表
    }

    suspend fun getLatesProjectList(
        isRefresh: Boolean = false,
        listModel: MutableLiveData<ListModel<Article>>?,
        loadPageStatus: MutableLiveData<LoadPageStatus>
    ) = getArticleList(ArticleType.ProjectDetailList, isRefresh, listModel, loadPageStatus)

    suspend fun getProjectDetailList(
        isRefresh: Boolean = false,
        listModel: MutableLiveData<ListModel<Article>>?,
        loadPageStatus: MutableLiveData<LoadPageStatus>,
        cid: Int
    ) = getArticleList(ArticleType.ProjectDetailList, isRefresh, listModel, loadPageStatus, cid)


    private suspend fun getArticleList(
        articleType: ArticleType,
        isRefresh: Boolean = false,
        listModel: MutableLiveData<ListModel<Article>>?,
        loadPageStatus: MutableLiveData<LoadPageStatus>,
        cid: Int = 0,
        keywords: String = ""
    ) {
        loadPageStatus.postValue(LoadPageStatus.Loading)
        listModel?.postValue(ListModel(loadPageStatus = loadPageStatus))

        if (isRefresh) currentPage =
            if (articleType is ArticleType.ProjectDetailList) 1 else 0

        val result = when (articleType) {
            ArticleType.Home -> remoteDataSource.getHomeArticles(currentPage )
            ArticleType.ProjectDetailList -> remoteDataSource.getProjectTypeDetailList(
                currentPage,
                cid
            )
            ArticleType.LatestProject -> remoteDataSource.getLatestProjectList(currentPage)
        }

        if (result is ResultData.Success) {
            val data = result.data
            //如果 获取到的数据是空值 且 当前 currentpage 值为 0
            if (data.datas.isNullOrEmpty() && currentPage == if (articleType is ArticleType.ProjectDetailList) 1 else 0) {
                loadPageStatus.postValue(LoadPageStatus.Empty) // 显示空页面
                listModel?.postValue(
                    ListModel(
                        isRefreshSuccess = false,
                        loadPageStatus = loadPageStatus,
                        isRefresh = isRefresh
                    )
                )
                return
            }

            if (data.offset >= data.total) { //最后一页  showLoading 可以用来加载dialog
                listModel?.postValue(
                    ListModel(
                        isRefreshSuccess = true,
                        showEnd = true,
                        isRefresh = isRefresh
                    )
                )
                return
            }

            currentPage++
            listModel?.postValue(
                ListModel(
                    isRefreshSuccess = true,
                    showSuccess = result.data.datas,
                    isRefresh = isRefresh
                )
            )
        } else if (result is ResultData.Error) {
            if (result.exception is UnknownHostException) {
                if (currentPage == 0) loadPageStatus.postValue(LoadPageStatus.NoNet)
            } else {
                if (currentPage == 0) loadPageStatus.postValue(LoadPageStatus.Fail)
            }

            listModel?.postValue(
                ListModel(
                    isRefreshSuccess = false,
                    showError = result.exception.message,
                    loadPageStatus = loadPageStatus,
                    isRefresh = isRefresh
                    )
            )
        }

    }

    /**
     * 获取首页列表数据
     */
    suspend fun getHomeArticleList(
        isRefresh: Boolean = false,
        listModel: MutableLiveData<ListModel<Article>>?,
        loadPageStatus: MutableLiveData<LoadPageStatus>
    ) = getArticleList(ArticleType.Home,isRefresh,listModel, loadPageStatus)
}