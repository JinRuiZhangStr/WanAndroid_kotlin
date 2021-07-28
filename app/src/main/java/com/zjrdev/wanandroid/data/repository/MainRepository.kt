package com.zjrdev.wanandroid.data.repository

import com.zjrdev.wanandroid.data.bean.Article
import com.zjrdev.wanandroid.data.bean.Banner
import com.zjrdev.wanandroid.data.bean.base.ResultData
import com.zjrdev.wanandroid.data.repository.datasource.RemoteDataSource

/**
 *Created by 张金瑞.
 *Data: 2021-1-27
 */
class MainRepository(private val homeRemoteDataSource: RemoteDataSource) {

    private val currentPage = 0

    suspend fun getBanner(): ResultData<List<Banner>> {
        val bannerData = homeRemoteDataSource.getBanners()
        if (bannerData is ResultData.Success) {
            return bannerData
        }

        return bannerData
    }

    suspend fun getStickArticles(): ResultData<List<Article>> {
        val stickArticle = homeRemoteDataSource.getStickArticles()
        if (stickArticle is ResultData.Success) {
            return stickArticle
        }
        return stickArticle
    }
}