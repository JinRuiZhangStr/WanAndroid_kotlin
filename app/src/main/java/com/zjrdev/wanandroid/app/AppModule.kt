package com.zjrdev.wanandroid.app

import org.koin.dsl.module

val viewModelModule = module {

}

val repositoryModule = module {

}

val appModule = listOf(viewModelModule, repositoryModule)