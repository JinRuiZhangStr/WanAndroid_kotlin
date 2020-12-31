package com.zjrdev.wanandroid.app

import com.zjrdev.wanandroid.repository.GetUserMsgUserCase
import com.zjrdev.wanandroid.vm.MyHomePageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MyHomePageViewModel(get()) }
}

val repositoryModule = module {
    single { GetUserMsgUserCase() }
}

val appModule = listOf(viewModelModule, repositoryModule)