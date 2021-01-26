package com.zjrdev.wanandroid.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.ext.clickWithTrigger
import com.zjrdev.wanandroid.ext.color
import com.zjrdev.wanandroid.ext.text
import kotlinx.android.synthetic.main.layout_custom_navigationbar_view.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.startActivity

/**
 *Created by 张金瑞.
 *Data: 2020-12-22
 */
@SuppressLint("NewApi")
class CustomNavigationBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defstyleAttr: Int =0
) : Toolbar(context, attrs, defstyleAttr) {
    /*
    mLeftImg -> 最左侧图标
    mLeftTextStr -> 左侧title文本
    mLeftTextId -> 左侧title文本id
    mRightTextStr -> 右侧title文本
    mRightTextId -> 右侧title文本id
    mRightImg1 -> ivRightNavigationBarOne 资源文件 drawable、mipmap
    mRightImg2 -> ivRightNavigationBarTwo 资源文件 drawable、mipmap
    mRightImg3 -> ivRightNavigationBarThree 资源文件 drawable、mipmap
    mShowLeftImg -> tvRightTitleNavigationBar 控件是否显示
    mShowRightImg1 -> ivRightNavigationBarOne 控件是否显示
    mShowRightImg2 -> ivRightNavigationBarTwo 控件是否显示
    mShowRightImg3 -> ivRightNavigationBarThree 控件是否显示
    mTitleTextStr -> 中间title文本
    mTitleTextId -> 中间title文本 id
    mTextColor -> 文本颜色
    mNavigationBar -> navigationbarbg 背景颜色
    mShowTabLayout -> navigationTablayout 是否显示
     */
    private var mLeftImg: Int = 0
    private var mLeftTextStr: String? = null
    private var mLeftTextId: Int = 0
    private var mRightTextStr: String? = null
    private var mRightTextId: Int = 0
    private var mRightImg1: Int = 0
    private var mRightImg2: Int = 0
    private var mRightImg3: Int = 0
    private var mShowLeftImg: Boolean = false
    private var mShowRightImg1: Boolean = false
    private var mShowRightImg2: Boolean = false
    private var mShowRightImg3: Boolean = false
    private var mTitleTextStr: String? = null
    private var mTitleTextId: Int = 0
    private var mTextColor: Int = 0
    private var mNavigationBar: Int = 0
    private var mShowTabLayout: Boolean = false

    init {
        val obtainStyledAttributes =
            context.obtainStyledAttributes(attrs, R.styleable.CustomNavigationBarView)
        mLeftTextId =
            obtainStyledAttributes.getResourceId(R.styleable.CustomNavigationBarView_left_textId, 0)
        mLeftTextStr =
            obtainStyledAttributes.getString(R.styleable.CustomNavigationBarView_left_textStr)
        mRightTextId =
            obtainStyledAttributes.getResourceId(
                R.styleable.CustomNavigationBarView_right_textId,
                0
            )
        mRightTextStr =
            obtainStyledAttributes.getString(R.styleable.CustomNavigationBarView_right_textStr)
        mLeftImg =
            obtainStyledAttributes.getResourceId(R.styleable.CustomNavigationBarView_left_img, 0)
        mShowLeftImg = obtainStyledAttributes.getBoolean(
            R.styleable.CustomNavigationBarView_isShow_left_img,
            false
        )
        mRightImg1 =
            obtainStyledAttributes.getResourceId(R.styleable.CustomNavigationBarView_right_img1, 0)
        mRightImg2 =
            obtainStyledAttributes.getResourceId(R.styleable.CustomNavigationBarView_right_img2, 0)
        mRightImg3 =
            obtainStyledAttributes.getResourceId(R.styleable.CustomNavigationBarView_right_img3, 0)
        mShowRightImg1 = obtainStyledAttributes.getBoolean(
            R.styleable.CustomNavigationBarView_isShow_right_img1,
            false
        )
        mShowRightImg2 = obtainStyledAttributes.getBoolean(
            R.styleable.CustomNavigationBarView_isShow_right_img2,
            false
        )
        mRightImg3 =
            obtainStyledAttributes.getResourceId(R.styleable.CustomNavigationBarView_right_img3, 0)
        mShowRightImg3 = obtainStyledAttributes.getBoolean(
            R.styleable.CustomNavigationBarView_isShow_right_img2,
            false
        )
        mTitleTextId = obtainStyledAttributes.getResourceId(
            R.styleable.CustomNavigationBarView_title_textId,
            0
        )
        mTitleTextStr =
            obtainStyledAttributes.getString(R.styleable.CustomNavigationBarView_title_textStr)
        mTextColor =
            obtainStyledAttributes.getColor(R.styleable.CustomNavigationBarView_text_color, 0)
        mShowTabLayout = obtainStyledAttributes.getBoolean(
            R.styleable.CustomNavigationBarView_isShow_tab_layout,
            false
        )
        mNavigationBar =
            obtainStyledAttributes.getColor(R.styleable.CustomNavigationBarView_bg_color, 0)
        initView()
        setContentInsetsRelative(0,0)
        obtainStyledAttributes.recycle()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_custom_navigationbar_view, this)
        mLeftTextStr?.let {
            tvLeftTitleNavigationBar.text = it
            tvLeftTitleNavigationBar.visibility = View.VISIBLE
        }

        if (mLeftTextId != 0) {
            tvLeftTitleNavigationBar.setText(mLeftTextId)
            tvLeftTitleNavigationBar.visibility = View.VISIBLE
        }
        mRightTextStr?.let {
            tvRightTitleNavigationBar.text = mRightTextStr
            tvRightTitleNavigationBar.visibility = View.VISIBLE
        }
        if (mRightTextId != 0) {
            tvRightTitleNavigationBar.setText(mRightTextId)
            tvRightTitleNavigationBar.visibility = View.VISIBLE
        }
        if (mLeftImg != 0) {
            ivBackNavigationBar.setImageResource(mLeftImg)
        }
        ivBackNavigationBar.visibility = if (mShowLeftImg) View.VISIBLE else View.GONE

        if (mRightImg1 != 0) {
            ivRightNavigationBarOne.setImageResource(mRightImg1)
        }
        ivRightNavigationBarOne.visibility = if (mShowRightImg1) View.VISIBLE else View.GONE
        if (mRightImg2 != 0) {
            ivRightNavigationBarSearch.setImageResource(mRightImg2)
        }
        ivRightNavigationBarSearch.visibility = if (mShowRightImg2) View.VISIBLE else View.GONE
        mTitleTextStr?.let {
            tvTitleNavigationBar.text = mTitleTextStr
            tvTitleNavigationBar.visibility = View.VISIBLE
        }
        if (mTitleTextId != 0) {
            tvTitleNavigationBar.setText(mTitleTextId)
            tvTitleNavigationBar.visibility = View.VISIBLE
        }
        if (mTextColor != 0) {
            tvTitleNavigationBar.setTextColor(mTextColor)
        }
        tlNavigationBar.visibility = if (mShowTabLayout) View.VISIBLE else View.GONE

        if (mNavigationBar != 0) {
            navigationBarBg.backgroundColor = mNavigationBar
        }
        ivRightNavigationBarSearch.clickWithTrigger {
//            context.startActivity<SearchActivity>()
        }
    }

    fun setNavigationBarBackgroundColor(colorRes: Int) {
        navigationBarBg.backgroundColor = color(colorRes)
    }

    fun setTitleNavigationBarColor(colorRes: Int) {
        tvTitleNavigationBar.setTextColor(color(colorRes))
    }

    fun setLeftTitleNavigationBarText(textRes: Int) {
        tvLeftTitleNavigationBar.visibility = View.VISIBLE
        tvLeftTitleNavigationBar.text = text(textRes)
    }

    fun setLeftTitleNavigationBarText(textRes: String) {
        tvLeftTitleNavigationBar.visibility = View.VISIBLE
        tvLeftTitleNavigationBar.text = textRes
    }

    fun setIvRightNavigationBarOne(drawableRes: Int) {
        ivRightNavigationBarOne.visibility = View.VISIBLE
        ivRightNavigationBarOne.setImageResource(drawableRes)
    }

    fun setTabLayoutData(tablayoutTitle: Array<String?>, viewPager2: ViewPager2) {
        //viewpager2 余 Tablayout联动
        TabLayoutMediator(tlNavigationBar, viewPager2) { tab, position ->
            tab.text = tablayoutTitle[position]
        }.attach()

        tlNavigationBar.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

        })
    }
}