package com.zjrdev.wanandroid.app

import com.zjrdev.wanandroid.data.repository.*
import com.zjrdev.wanandroid.data.repository.datasource.RemoteDataSource
import com.zjrdev.wanandroid.vm.HomePageViewModel
import com.zjrdev.wanandroid.vm.HomeProjectViewModel
import com.zjrdev.wanandroid.vm.LoginViewModel
import com.zjrdev.wanandroid.vm.MyHomePageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MyHomePageViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomePageViewModel(get(),get()) }
    viewModel { HomeProjectViewModel(get(),get()) }
}

val repositoryModule = module {
    single { RemoteDataSource() }
    single { GetUserMsgUserCase() }
    single { LoginRepository(get()) }
    single { MainRepository(get()) }
    single { ProjectRepository(get()) }
    single { ArticleUserCase(get()) }
}

val appModule = listOf(viewModelModule, repositoryModule)