package com.zjrdev.wanandroid.data.api

import com.zjrdev.wanandroid.data.bean.User
import com.zjrdev.wanandroid.data.bean.base.WanResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
}