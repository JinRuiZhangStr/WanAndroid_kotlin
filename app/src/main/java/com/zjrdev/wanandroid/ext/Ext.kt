package com.zjrdev.wanandroid.ext

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat

const val SET_THEME = "set_theme"

//获取主题属性id
fun TypedValue.resourceId(resId: Int, theme: Resources.Theme): Int {
    theme.resolveAttribute(resId, this, true)
    return this.resourceId
}

/**
 *带延迟过滤的点击事件监听
 * 延迟时间根据triggerDelay获取：600毫秒
 */
interface OnLazyClickListener : View.OnClickListener {
    override fun onClick(v: View?) {
        if (v?.clickEnable() == true) {
            onLazyClick(v)
        }
    }

    fun onLazyClick(v: View?)
}

private var <T : View> T.triggerlastTime: Long
    get() = if (getTag(1123460103) != null) getTag(1123460103) as Long else -601
    set(value) {
        setTag(1123460103, value)
    }

private var <T : View> T.triggerDelay: Long
    get() = if (getTag(1123461123) != null) getTag(1123461123) as Long else 600
    set(value) {
        setTag(1123461123, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerlastTime >= triggerDelay) {
        flag = true
    }
    triggerlastTime = currentClickTime
    return flag
}

/**
 * 带延迟过滤的点击事件View扩展
 */
fun <T: View> T.clickWithTrigger(time: Long = 600,block:(T) -> Unit) {
    triggerlastTime = time
    setOnClickListener {
        if (clickEnable()) {
            block(it as T)
        }
    }
}

fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)
fun View.color(colorRes: Int) = context.color(colorRes)

fun Context.text(textRes: Int) = this.resources.getString(textRes)
fun View.text(textRes: Int) = context.text(textRes )

/**
 * 隐藏软键盘
 */
fun View.hideSoftInput() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken,0)
}

/**
 * 弹出软键盘
 */
fun View.ShowSoftInput() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this,InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Html类提供我们一些方法可以将HTML字符串转变成可显示的样式文本
 */
fun String?.htmlToSpanned() =
    if (this.isNullOrEmpty()) "" else HtmlCompat.fromHtml(this,HtmlCompat.FROM_HTML_MODE_LEGACY)
