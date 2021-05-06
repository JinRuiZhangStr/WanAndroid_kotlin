package com.zjrdev.wanandroid.data.api

import com.zjrdev.wanandroid.data.bean.*
import com.zjrdev.wanandroid.data.bean.base.WanResponse
import retrofit2.http.*

/**
 *Created by 张金瑞.
 *Data: 2020-12-17
 */
interface ApiService {

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") passWord: String
    ): WanResponse<User>

    @GET("/banner/json")
    suspend fun getBanner(): WanResponse<List<Banner>>

    /**
     * 项目分类
     */
    @GET("/project/tree/json")
    suspend fun getProjectTypes(): WanResponse<MutableList<ClassifyResponse>>

    /**
     * 根据分类 id 获取项目数据
     */
    @GET("/project/list/{page}/json")
    suspend fun getProjectDataByType(
        @Path("page") pageNo: Int,
        @Query("cid") cid: Int
    ): WanResponse<WanListResponse<List<Article>>>

    /**
     * 获取最新项目数据
     */
    @GET("/article/listproject/{page}/json")
    suspend fun getProjectNewData(
        @Path("page") pageNo: Int
    ): WanResponse<WanListResponse<MutableList<Article>>>
}