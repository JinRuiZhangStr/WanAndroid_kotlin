package com.zjrdev.wanandroid.ext

import androidx.appcompat.app.AppCompatDelegate
import com.tencent.mmkv.MMKV
import com.zjrdev.wanandroid.R

private const val KEY_NIGHT_MODE = "key_night_mode"
private const val THEME = "theme"

/**
 * 存储白天/夜间模式
 */
fun setNightMode(theme: Int) {
    MMKV.defaultMMKV().putInt(KEY_NIGHT_MODE,theme)
}

/**
 * 获取白天/页面模式
 */
fun getNightMode(): Int{
    return MMKV.defaultMMKV().getInt(KEY_NIGHT_MODE,AppCompatDelegate.MODE_NIGHT_NO)
}

/**
 * 存储 app主题
 */
fun setAppTheme(theme: Int) {
    MMKV.defaultMMKV().putInt(THEME,theme)
}

/**
 * 获取 app 主题
 */
fun getAppTheme(): Int {
    return MMKV.defaultMMKV().getInt(THEME, R.style.AppTheme)
}