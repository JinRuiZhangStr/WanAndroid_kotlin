package com.zjrdev.wanandroid.vm

import com.zjrdev.wanandroid.data.repository.GetUserMsgUserCase
import com.zjrdev.wanandroid.vm.base.BaseViewModel

/**
 *Created by 张金瑞.
 *Data: 2020-12-21
 */
class MyHomePageViewModel(userCase: GetUserMsgUserCase) :
    BaseViewModel() {
    var userLiveData = userCase.getUserMsg()
}