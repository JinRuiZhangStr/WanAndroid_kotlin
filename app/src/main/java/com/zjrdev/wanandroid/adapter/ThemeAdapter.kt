package com.zjrdev.wanandroid.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.data.bean.Theme
import com.zjrdev.wanandroid.ext.color
import com.zjrdev.wanandroid.ext.text
import kotlinx.android.synthetic.main.item_theme.view.*
import org.jetbrains.anko.textColor

/**
 *Created by 张金瑞.
 *Data: 2021-7-29
 */
class ThemeAdapter: BaseQuickAdapter<Theme,BaseViewHolder>(R.layout.item_theme) {
    override fun convert(holder: BaseViewHolder, item: Theme) {
        holder.apply {
            addChildClickViewIds(R.id.tvUserTheme)
            itemView.apply {
                if (item.color == R.color.accent_white) {
                    flThemeColor.delegate.backgroundColor =
                        color(R.color.text_color_primary_alpha_50)
                    tvThemeColor.textColor = color(R.color.text_color_primary_alpha_50)
                } else {
                    flThemeColor.delegate.backgroundColor =
                        color(item.color)
                    tvThemeColor.textColor = color(item.color)
                    tvUserTheme.delegate.strokeColor =
                        color(if (item.isChoose == 0) R.color.text_color_primary_alpha_50 else item.color)
                    tvUserTheme.textColor =
                        color(if (item.isChoose == 0) R.color.text_color_primary_alpha_50 else item.color)
                }
                ivChooseTheme.visibility = if (item.isChoose == 0) View.GONE else View.VISIBLE
                tvUserTheme.text =
                    if (item.isChoose == 0) text(R.string.theme_user) else text(R.string.theme_user_ing)
                tvThemeColor.text = item.colorName
            }
        }
    }
}