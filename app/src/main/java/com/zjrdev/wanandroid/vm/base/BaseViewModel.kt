package com.zjrdev.wanandroid.vm.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel(),LifecycleObserver {

    /**
     * 运行在UI线程的协程 viewModelScope 已经实现了在onCleared取消协程
     * suspend 关键字是实现协程的关键，它表示这个函数是可以挂起的
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        block()
    }
}