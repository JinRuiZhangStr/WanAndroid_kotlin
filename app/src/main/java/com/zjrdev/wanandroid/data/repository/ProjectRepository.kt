package com.zjrdev.wanandroid.data.repository

import androidx.lifecycle.MutableLiveData
import com.zjrdev.wanandroid.data.bean.ClassifyResponse
import com.zjrdev.wanandroid.data.bean.base.ResultData
import com.zjrdev.wanandroid.data.repository.datasource.RemoteDataSource
import com.zjrdev.wanandroid.util.ListModel
import com.zjrdev.wanandroid.view.loadpage.LoadPageStatus

/**
 *Created by 张金瑞.
 *Data: 2021-2-22
 */
class ProjectRepository(private val projectRemoteDataSource: RemoteDataSource) {
    suspend fun getProjectClassify(listModel: MutableLiveData<ListModel<ClassifyResponse>>?) {
        val loadPageStatus = MutableLiveData<LoadPageStatus>()
        loadPageStatus.postValue(LoadPageStatus.Loading)
        listModel?.postValue(ListModel(loadPageStatus = loadPageStatus))
        val projectClassify = projectRemoteDataSource.getProjectClassify()
        if (projectClassify is ResultData.Success) { //数据处理成功
            if (projectClassify.data.isNullOrEmpty()) {
                loadPageStatus.postValue(LoadPageStatus.Empty)
                listModel?.postValue(ListModel(loadPageStatus = loadPageStatus))
            }

            listModel?.postValue(
                ListModel(
                    showLoading = false,
                    showSuccess = projectClassify.data
                )
            )
        } else if (projectClassify is ResultData.Error) { //数据处理失败
            loadPageStatus.postValue(LoadPageStatus.Fail)
            listModel?.postValue(
                ListModel(
                    showLoading =  false,
                    showError = projectClassify.exception.message,
                    loadPageStatus = loadPageStatus
                )
            )
        }
    }
}