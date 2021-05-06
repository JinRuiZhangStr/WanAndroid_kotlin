package com.zjrdev.wanandroid.data.repository.datasource

import com.zjrdev.wanandroid.data.api.RetrofitClient
import com.zjrdev.wanandroid.data.api.WAN_ANDROID
import com.zjrdev.wanandroid.data.bean.*
import com.zjrdev.wanandroid.data.bean.base.ResultData
import com.zjrdev.wanandroid.util.safeApiCall
import java.io.IOException

/**
 *Created by 张金瑞.
 *Data: 2021-1-5
 */
class RemoteDataSource {

    /**
     * 我的界面
     */
    suspend fun login(userName: String, password: String) = safeApiCall(
        call = { requestLogin(userName, password)}
    )

    private suspend fun requestLogin(userName: String,password: String): ResultData<User> {
        val login = RetrofitClient.getInstance(WAN_ANDROID).service.login(userName,password)
        if (login.errorCode == 0) {
            return ResultData.Success(login.data)
        }

        return ResultData.ErrorMessage(login.errorMsg)
    }

    /**
     * 首页 轮播图
     */
    suspend fun getBanners() = safeApiCall(
        call = {requestBanner()}
    )

    private suspend fun requestBanner(): ResultData<List<Banner>> {
        val banner = RetrofitClient.getInstance(WAN_ANDROID).service.getBanner()
        if (banner.errorCode == 0) {
            return ResultData.Success(banner.data)
        }
        return ResultData.Error(IOException("Failed to get banner${banner.errorMsg}"))
    }

    /**
     * 项目数据源
     *  @param getProjectClassify 项目tab
     *  @param getLatestProjectList 最新项目列表数据
     *  @param getProjectTypeDetailList 项目tab下数据
     */
    suspend fun getProjectClassify() = safeApiCall(
        call = {requestProjectClassify()}
    )

    private suspend fun requestProjectClassify() : ResultData<List<ClassifyResponse>> {
        val projectClassify = RetrofitClient.getInstance(WAN_ANDROID).service.getProjectTypes()
        if (projectClassify.errorCode == 0) {
            return ResultData.Success(projectClassify.data)
        }
        return ResultData.Error(IOException("Failed to get projectClassify${projectClassify.errorMsg}"))
    }

    suspend fun getProjectTypeDetailList(page: Int, cid: Int) = safeApiCall(
        call = {requestProjectTypeDetailList(page,cid)}
    )

    private suspend fun requestProjectTypeDetailList(page: Int,cid: Int): ResultData<WanListResponse<List<Article>>> {
        val projectDataByType = RetrofitClient.getInstance(WAN_ANDROID).service.getProjectDataByType(page,cid)
        if (projectDataByType.errorCode == 0) {
            return ResultData.Success(projectDataByType.data)
        }
        return ResultData.Error(IOException("Failed to get projectTypeDetailList${projectDataByType.errorMsg}"))
    }

    suspend fun getLatestProjectList(page: Int) = safeApiCall(
        call = { requestLatestProjectList(page)}
    )

    private suspend fun requestLatestProjectList(page: Int) : ResultData<WanListResponse<MutableList<Article>>> {
        val projectNewData = RetrofitClient.getInstance(WAN_ANDROID).service.getProjectNewData(page)
        if (projectNewData.errorCode == 0) {
            return ResultData.Success(projectNewData.data)
        }
        return ResultData.Error(IOException("Failed to get requestLatestProjectList${projectNewData.errorMsg}"))
    }
}