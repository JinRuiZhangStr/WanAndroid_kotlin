package com.zjrdev.wanandroid.data.repository.datasource

import com.zjrdev.wanandroid.data.api.RetrofitClient
import com.zjrdev.wanandroid.data.api.WAN_ANDROID
import com.zjrdev.wanandroid.data.bean.Banner
import com.zjrdev.wanandroid.data.bean.User
import com.zjrdev.wanandroid.data.bean.base.ResultData
import com.zjrdev.wanandroid.util.safaApiCall
import java.io.IOException

/**
 *Created by 张金瑞.
 *Data: 2021-1-5
 */
class RemoteDataSource {

    /**
     * 我的界面
     */
    suspend fun login(userName: String, password: String) = safaApiCall(
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
    suspend fun getBanners() = safaApiCall(
        call = {requestBanner()}
    )

    private suspend fun requestBanner(): ResultData<List<Banner>> {
        val banner = RetrofitClient.getInstance(WAN_ANDROID).service.getBanner()
        if (banner.errorCode == 0) {
            return ResultData.Success(banner.data)
        }
        return ResultData.Error(IOException("Failed to get banner${banner.errorMsg}"))
    }
}