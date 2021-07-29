package com.zjrdev.wanandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.data.bean.MyFunction
import kotlinx.android.synthetic.main.item_my_function.view.*

/**
 *Created by 张金瑞.
 *Data: 2021-7-29
 */
class FunctionAdapter: BaseQuickAdapter<MyFunction,BaseViewHolder>(R.layout.item_my_function) {
    override fun convert(holder: BaseViewHolder, item: MyFunction) {
        holder.apply {
            itemView.apply {
                tvItemFunction.setCompoundDrawablesWithIntrinsicBounds(0,item.drawable,0,0)
                tvItemFunction.text = item.functionName
            }
        }
    }
}