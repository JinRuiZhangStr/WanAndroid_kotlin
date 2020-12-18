package com.zjrdev.wanandroid.data.api
import com.zjrdev.wanandroid.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 *Created by 张金瑞.
 *Data:
 */
abstract class BaseRetrofitClient {

    companion object {
        private const val TIME_OUT = 5
    }

    /**
     *创建 okhttpclient
     */
    private val client: OkHttpClient
        get() {

            val builder = OkHttpClient.Builder()
            // okhttp 日志拦截器
            val logging = HttpLoggingInterceptor()

            if (BuildConfig.DEBUG) {
                //记录请求和响应行及其各自的头和正文（如果存在）。
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                //记录请求和响应行及其各自的头。
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }

            //okhttpclient 添加拦截器
            builder.addInterceptor(logging)
                .addInterceptor(mLoggingInterceptor)
                //设置连接超时时间
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                //设置读取超时时间
                .readTimeout(TIME_OUT.toLong(),TimeUnit.SECONDS)
            handleBuilder(builder)
            return builder.build()
        }

    private val mLoggingInterceptor = Interceptor {chain ->
        //请求参数
        val request = chain.request()
        val t1 = System.nanoTime()
        //响应数据体
        val response = chain.proceed(chain.request())
        val t2 = System.nanoTime()
        //响应的内容类型
        val contentType = response.body?.contentType()
        //服务器返回响应的具体数据
        val content = response.body?.string()

        //debug 类型的日志
        Timber.tag("zjr")
            .d("request url:${request.url}\ntime:${(t2 - t1) / 1e6}\nbody:${content}\n")

        response.newBuilder()
            .body(response.body)
            .build()
    }

    /**
     * 使用 okhttpclient build，继续在之中添加所需要加的方法  提升扩展性
     */
    protected abstract fun handleBuilder(builder:OkHttpClient.Builder)

    /**
     * retrofit build，继续在之中添加所需加的方法  提升扩展性
     */
    protected abstract fun retrofitBuilder(builder: Retrofit.Builder)

    /**
     * 获取 retrofit 请求api的类
     */
    fun <S> getService(serviceClass: Class<S>, hostType: Int) : S {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(getHost(hostType))
        retrofitBuilder(builder)
        return builder.build().create(serviceClass)

    }
}


