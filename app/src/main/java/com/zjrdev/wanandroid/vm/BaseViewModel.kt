package com.zjrdev.wanandroid.vm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BaseViewModel: ViewModel(),LifecycleObserver {

    /**
     * 运行在UI线程的协程 viewModelScope 已经实现了在onCleared取消协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        block()
    }
}