package com.zjrdev.wanandroid.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import com.zjrdev.wanandroid.ext.CookieClass.cookieJar
/**
 *Created by 张金瑞.
 *Data: 2020-12-17
 */
class RetrofitClient private constructor(hostType: Int): BaseRetrofitClient() {

    /**
     * mode = LazyThreadSafetyMode.SYNCHRONIZED : 锁定，用于确保只有一个线程可以初始化【lazy】实例
     */
    val service by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        getService(ApiService::class.java,hostType)
    }

    companion object {
        /**
         * volatile 可见性 保证对所有线程的可见性  单独使用的话并不能保证其并发下的安全性，需配合 synchronized同步锁、AtomicInteger
         * 单例模式
         */
        @Volatile
        private var instance: RetrofitClient ?= null
        fun getInstance(hostType: Int) = instance ?: synchronized(this){
            instance ?: RetrofitClient(hostType).also { instance = it }
        }
    }

    //okhttpClient 扩展
    override fun handleBuilder(builder: OkHttpClient.Builder) {
        builder.cookieJar(cookieJar)
    }

    //retrofitClient 扩展
    override fun retrofitBuilder(builder: Retrofit.Builder) {

    }

}