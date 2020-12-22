package com.zjrdev.wanandroid.vm

import com.zjrdev.wanandroid.repository.GetUserMsgUserCase
import com.zjrdev.wanandroid.repository.ReadHistoryUserCase
import com.zjrdev.wanandroid.vm.base.BaseViewModel

/**
 *Created by 张金瑞.
 *Data: 2020-12-21
 */
class MyHomePageViewModel(userCase: GetUserMsgUserCase, readHistoryUserCase: ReadHistoryUserCase) :
    BaseViewModel() {
    var userLiveData = userCase
}