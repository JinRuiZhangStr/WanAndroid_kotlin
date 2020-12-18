package com.zjrdev.wanandroid.ext

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.zjrdev.wanandroid.app.WanApplication

/**
 *Created by 张金瑞.
 *Data: 2020-12-17
 */
object CookieClass {
    //实例化 cookie持久化工具
    val cookiePersistor = SharedPrefsCookiePersistor(WanApplication.CONTEXT)
    val cookieJar = PersistentCookieJar(SetCookieCache(), cookiePersistor)

    /**
     * 清除 cookie
     */
    fun clearCookie() = cookieJar.clear()

    /**
     * 是否有 cookie存在
     */
    fun hasCookie(): Boolean = cookiePersistor.loadAll().isNotEmpty()
}