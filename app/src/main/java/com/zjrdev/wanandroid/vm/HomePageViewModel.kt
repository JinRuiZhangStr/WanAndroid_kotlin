package com.zjrdev.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import com.zjrdev.wanandroid.data.bean.Article
import com.zjrdev.wanandroid.data.bean.Banner
import com.zjrdev.wanandroid.data.bean.base.ResultData
import com.zjrdev.wanandroid.data.repository.ArticleUserCase
import com.zjrdev.wanandroid.data.repository.MainRepository
import com.zjrdev.wanandroid.util.ListModel
import com.zjrdev.wanandroid.view.loadpage.LoadPageStatus
import com.zjrdev.wanandroid.vm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Created by 张金瑞.
 *Data: 2021-1-26
 */
class HomePageViewModel(private val mainRepository: MainRepository,private val articleUserCase: ArticleUserCase): BaseViewModel() {

    val mBanner: MutableLiveData<List<Banner>> = MutableLiveData()
    val mListModel = MutableLiveData<ListModel<Article>>()
    private val loadPageStatus = MutableLiveData<LoadPageStatus>()

    fun loadBanner() = launchUI {
        val result = withContext(Dispatchers.IO){
            mainRepository.getBanner()
        }

        if (result is ResultData.Success) {
            mBanner.value = result.data
        }
    }

    fun loadHomeArticles(isRefresh: Boolean = false) = launchUI {
        withContext(Dispatchers.IO) {
            articleUserCase.getHomeArticleList(
                isRefresh,
                mListModel,
                loadPageStatus
            )
        }
    }
}