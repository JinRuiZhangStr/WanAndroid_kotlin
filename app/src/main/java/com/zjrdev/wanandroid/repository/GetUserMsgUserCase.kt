package com.zjrdev.wanandroid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zjrdev.wanandroid.data.bean.User
import com.zjrdev.wanandroid.db.AppDatabase
import com.zjrdev.wanandroid.util.ListModel

/**
 *Created by 张金瑞.
 *Data: 2020-12-21
 */
class GetUserMsgUserCase {
    private val userDao = AppDatabase.getInstance().userDao()

    /**
     * 获取 本地用户存储的用户信息
     */
    fun getUserMsg(): LiveData<User> {
        return userDao.loadUser()
    }

    /**
     * suspend 协程方法的关键字 清除本地的存储的用户信息
     */
    suspend fun clearUserMsg(listModel: MutableLiveData<ListModel<Int>>) {
        listModel?.let {
            it.postValue(ListModel(showLoading = true))
            userDao.deleteAll()
            it.postValue(ListModel(showLoading = false,showEnd = false))
        }


    }
}