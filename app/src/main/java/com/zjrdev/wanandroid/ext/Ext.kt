package com.zjrdev.wanandroid.ext

import android.content.res.Resources
import android.util.TypedValue

//获取主题属性id
fun TypedValue.resourceId(resId: Int,theme: Resources.Theme): Int{
    theme.resolveAttribute(resId,this,true)
    return this.resourceId
}