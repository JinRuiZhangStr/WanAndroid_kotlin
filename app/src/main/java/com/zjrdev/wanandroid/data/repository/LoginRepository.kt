package com.zjrdev.wanandroid.data.repository

import androidx.lifecycle.MutableLiveData
import com.zjrdev.wanandroid.app.WanApplication
import com.zjrdev.wanandroid.data.bean.User
import com.zjrdev.wanandroid.data.bean.base.ResultData
import com.zjrdev.wanandroid.data.repository.datasource.RemoteDataSource
import com.zjrdev.wanandroid.db.AppDatabase
import com.zjrdev.wanandroid.ext.setLoginState
import com.zjrdev.wanandroid.util.ListModel

/**
 *Created by 张金瑞.
 *Data: 2021-1-25
 */
class LoginRepository(private val remoteDataSource: RemoteDataSource) {
    private val userDao = AppDatabase.getInstance().userDao()
    suspend fun login(
        username: String,
        password: String,
        listModel: MutableLiveData<ListModel<Int>>
    ) {
        listModel?.postValue(ListModel(showLoading = true))
        val result = remoteDataSource.login(username,password)
        if (result is ResultData.Success) {
            setLoggedInUser(result.data)
            listModel?.postValue(ListModel(showLoading = false,showEnd = true))
        } else if (result is ResultData.ErrorMessage) {
            listModel?.postValue(ListModel(showLoading = false,showError = result.message))
        }
    }

    private suspend fun setLoggedInUser(loggedInUser: User?){
        userDao.deleteAll()
        userDao.insert(loggedInUser)
        setLoginState(true)
    }
}