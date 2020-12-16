package com.zjrdev.wanandroid.util

import android.app.Activity
import android.app.Application
import com.zjrdev.wanandroid.adapter.ActivityLifecycleCallbacksAdapter

/**
 * 对应用、activity生命周期的一个管理  类似有java中 list中装activity
 */
object ActivityHelper {

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksAdapter(
            //activity初始化的时候 添加到装activity的List集合中
            onActivityCreated = {activity,_ ->
                activities.add(activity)
            },
            //activity销毁的时候，从装有该activity的list集合中移除
            onActivityDestroyed = {activity ->
                activities.remove(activity)
            }
        ))
    }

    val activities = mutableListOf<Activity>()

    /**
     * 应用退出 将装有activity的list集合中的activity全部销毁
     */
    fun finish(vararg clazz: Class<out Activity>){

        activities.forEach { activity ->
            if (clazz.contains(activity::class.java)){
                activity.finish()
            }
         }

    }
}