package com.zjrdev.wanandroid.vm

import androidx.lifecycle.MutableLiveData
import com.zjrdev.wanandroid.data.repository.LoginRepository
import com.zjrdev.wanandroid.util.ListModel
import com.zjrdev.wanandroid.vm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *Created by 张金瑞.
 *Data: 2020-12-31
 */
class LoginViewModel(private val repository: LoginRepository): BaseViewModel() {
    val mLoginStatus = MutableLiveData<ListModel<Int>>()
    val mRegisterStatus = MutableLiveData<ListModel<Int>> ()

    fun login(userName: String, passWord: String) {
        launchUI {
            withContext(Dispatchers.IO){
                repository.login(userName,passWord,mLoginStatus)
            }
        }
    }
}