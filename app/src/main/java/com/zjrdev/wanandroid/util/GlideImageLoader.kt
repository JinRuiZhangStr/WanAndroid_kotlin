package com.zjrdev.wanandroid.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.zjrdev.wanandroid.R

/**
 *Created by 张金瑞.
 *Data: 2021-7-27
 * glide 图片加载封装工具类
 */
@GlideModule
class GlideImageLoader: AppGlideModule() {

    fun displayImage(
        context: Context,
        path: Any,
        imageView: ImageView
    ) {
        Glide.with(context).load(path)
            .centerCrop()
            .placeholder(R.drawable.ic_img_code)
            .error(R.drawable.ic_img_code)
            .into(imageView)
    }
}