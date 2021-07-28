package com.zjrdev.wanandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.data.bean.Article
import com.zjrdev.wanandroid.ext.htmlToSpanned
import com.zjrdev.wanandroid.ext.text
import kotlinx.android.synthetic.main.item_home_page.view.*

/**
 *Created by 张金瑞.
 *Data: 2021-1-27
 */
class HomePageAdapter: BaseQuickAdapter<Article,BaseViewHolder>(R.layout.item_home_page),
    LoadMoreModule{
    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.apply {
            itemView.apply {
                tvHomePageItemAuthor.text = when {
                    !item.author.isNullOrEmpty() -> item.author
                    !item.shareUser.isNullOrEmpty() -> item.shareUser
                    else -> text(R.string.anonymous)
                }

                tvHomePageItemContent.text = item.title.htmlToSpanned()
                tvHomePageItemType.text = when {
                    !item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty()->
                    "${item.superChapterName.htmlToSpanned()}·${item.chapterName.htmlToSpanned()}"
                    item.superChapterName.isNullOrEmpty() && !item.chapterName.isNullOrEmpty() ->
                        item.chapterName.htmlToSpanned()
                    !item.superChapterName.isNullOrEmpty() && item.chapterName.isNullOrEmpty() ->
                        item.superChapterName.htmlToSpanned()
                    else -> ""
                }
            }
        }
    }
}