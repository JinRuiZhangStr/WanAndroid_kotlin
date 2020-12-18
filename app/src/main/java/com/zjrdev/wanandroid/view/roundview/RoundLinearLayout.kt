package com.zjrdev.wanandroid.view.roundview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * Created by 张金瑞.
 * Data: 2020年12月17日
 * 圆角的线性布局
 * @JvmOverloads  代表Java中的构造函数，constructor中有几个参数，可以生成几个构造函数
 */
class RoundLinearLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet
) : LinearLayout(context,attrs){

    private var delegate = RoundViewDelegate(this,context, attrs)

    fun getDelegate(): RoundViewDelegate {
        return delegate
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        if (delegate.isWidthHeightEqual && width > 0 &&height > 0){
            var max = Math.max(width,height)
            var measureSpec = MeasureSpec.makeMeasureSpec(max,MeasureSpec.EXACTLY)
            super.onMeasure(measureSpec, measureSpec)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (delegate.isRadiusHalfHeight){
            delegate.cornerRadius = height/2
        } else {
            delegate.setBgSelector()
        }
    }
}