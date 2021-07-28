package com.zjrdev.wanandroid.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.youth.banner.adapter.BannerAdapter
import com.zjrdev.wanandroid.data.bean.Banner
import com.zjrdev.wanandroid.util.GlideImageLoader


/**
 *Created by 张金瑞.
 *Data: 2021-7-27
 * banner 轮播器的适配器
 */
class ImageAdapter (
    mDatas: List<Banner?>?,
    var mContext: Context
    ): BannerAdapter<Banner,ImageAdapter.ImageHolder>(mDatas) {


    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        val imageView = ImageView(parent?.context)
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return ImageHolder(imageView)

    }

    override fun onBindView(holder: ImageHolder?, data: Banner?, position: Int, size: Int) {
        GlideImageLoader().displayImage(mContext,data!!.imagePath,holder!!.imageView)
    }

    class ImageHolder(view: View): RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view as ImageView
    }
}