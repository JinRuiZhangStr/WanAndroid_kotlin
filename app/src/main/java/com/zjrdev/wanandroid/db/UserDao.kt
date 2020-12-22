package com.zjrdev.wanandroid.db

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.zjrdev.wanandroid.data.bean.User

/**
 *Created by 张金瑞.
 *Data: 2020-12-22
 */
interface UserDao {

    /**
     * 将User对象存储到本地数据库
     */
    @Transaction
    @Insert
    fun insert(user: User?)

    /**
     * 获取本地存储User对象
     */
    @Transaction
    @Query("SELECT * FROM User")
    fun loadUser(): LiveData<User>

    @Transaction
    @Query("SELECT user_id FROM User")
    fun loadUid(): LiveData<User>

    /**
     * 删除 User表中的全部数据
     */
    @Transaction
    @Query("DELETE FROM User")
    fun deleteAll()
}