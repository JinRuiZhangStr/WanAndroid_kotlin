package com.zjrdev.wanandroid.app

import android.app.Application
import android.app.UiModeManager
import android.content.Context
import android.util.TypedValue
import com.coder.zzq.smartshow.core.SmartShow
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import com.zjrdev.wanandroid.BuildConfig
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.ext.getNightMode
import com.zjrdev.wanandroid.ext.resourceId
import com.zjrdev.wanandroid.util.ActivityHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import kotlin.properties.Delegates

class WanApplication : Application() {

    companion object {
        var CONTEXT : Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        //初始化 ActivityHelper 绑定应用生命周期与activity的生命周期
        ActivityHelper.init(this)
        //初始化Timber日志
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        //适用于 Kotlin 开发人员的实用轻量级依赖注入框架。
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@WanApplication)
            modules(appModule)
        }

        /**
         * 初始化LiveEventBus
         * 1、supportBroadcast配置支持跨进程、跨APP通信
         * 2、配置LifecycleObserver（如Activity）接收消息的模式（默认值true）：
         * true：整个生命周期（从onCreate到onDestroy）都可以实时收到消息
         * false：激活状态（Started）可以实时收到消息，非激活状态（Stoped）无法实时收到消息，需等到Activity重新变成激活状
         * 态，方可收到消息
         * 3、autoClear
         * 配置在没有Observer关联的时候是否自动清除LiveEvent以释放内存（默认值false）
         * */
        LiveEventBus.config()

        //设置全局的Header构建起
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(
                TypedValue().resourceId(R.attr.colorPrimary,context.theme),
                TypedValue().resourceId(R.attr.textColorPrimary,context.theme)
            )
            ClassicsHeader(context)
        }

        /**
         * 初始化 MMKv 腾讯团队出的类似于sp存储
         */
        MMKV.initialize(this)
        //切换白天/夜间模式
        (getSystemService(Context.UI_MODE_SERVICE) as UiModeManager).nightMode = getNightMode()

        /**
         * 主要做 Toast、顶部snackbar、dialog、snackbar提示显示
         */
        SmartShow.init(this)

    }
}