package com.zjrdev.wanandroid.ui.homemine.activity

import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.os.Bundle
import androidx.recyclerview.widget.SimpleItemAnimator
import com.gyf.immersionbar.ktx.immersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.adapter.ThemeAdapter
import com.zjrdev.wanandroid.data.bean.Theme
import com.zjrdev.wanandroid.ext.*
import com.zjrdev.wanandroid.ui.base.BaseActivity
import com.zjrdev.wanandroid.view.rippleAnimation.RippleAnimation
import com.zjrdev.wanandroid.view.rippleAnimation.RippleAnimation.create
import kotlinx.android.synthetic.main.activity_my_theme.*
import kotlinx.android.synthetic.main.item_theme.view.*
import kotlinx.android.synthetic.main.layout_custom_navigationbar_view.*
import kotlinx.android.synthetic.main.layout_custom_navigationbar_view.ivBackNavigationBar
import kotlinx.android.synthetic.main.layout_custom_navigationbar_view.view.*
import org.jetbrains.anko.textColor
import java.net.URI.create

/**
 *Created by 张金瑞.
 *Data: 2021-7-29
 */
class MyThemeActivity: BaseActivity() {

    private var themes = mutableListOf<Theme>()
    private var themeAdapter = ThemeAdapter()
    private var mPosition: Int = 0
    private lateinit var mThemeBean: Theme
    private var isClick: Boolean = false

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            titleBar(R.id.themeNv)
        }
    }

    override fun setLayoutId(): Int = R.layout.activity_my_theme

    override fun initView(savedInstanceState: Bundle?) {
        themeNv.apply {
            setLeftTitleNavigationBarText(R.string.theme)
            ivBackNavigationBar.clickWithTrigger { finish() }
        }

        themeRv.apply {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            adapter = themeAdapter
        }

        themeAdapter.setOnItemClickListener  { _, view, position ->
            if (mPosition != position) {
                view.apply {
                    RippleAnimation.create(tvUserTheme).setDuration(1000).start()
                }
                mThemeBean = themeAdapter.data[position]
                setAppTheme(mThemeBean.theme)
                mThemeBean.isChoose = 1
                themeAdapter.setData(position,mThemeBean)
                immersionBar {
                    statusBarColor(mThemeBean.color)
                }
                if (mThemeBean.color == R.color.accent_white) {
                    themeNv.ivBackNavigationBar.imageTintList =
                        ColorStateList.valueOf(color(R.color.color_on_theme_text))
                    themeNv.tvLeftTitleNavigationBar.textColor = color(R.color.color_on_theme_text)
                } else {
                    themeNv.ivBackNavigationBar.imageTintList =
                        ColorStateList.valueOf(color(R.color.color_theme_text))
                    themeNv.tvLeftTitleNavigationBar.textColor = color(R.color.color_theme_text)
                }

                themeNv.setNavigationBarBackgroundColor(mThemeBean.color)
                LiveEventBus.get(MY_PAGE_SET_THEME_COLOR).post(mThemeBean.color)
                isClick = true

                if (mPosition != -1) {
                    val themeBean = themeAdapter.data[mPosition]
                    themeBean.isChoose = 0
                    themeAdapter.setData(mPosition,themeBean)
                }

                mPosition = position

            }
        }

    }

    override fun initData() {
        themes.add(
            Theme(
                R.color.accent_white,
                R.style.AppTheme_White,
                text(R.string.theme_white),
                0
            )
        )
        themes.add(Theme(R.color.accent_red, R.style.AppTheme_Red, text(R.string.theme_red), 0))
        themes.add(Theme(R.color.accent_pink, R.style.AppTheme_Pink, text(R.string.theme_pink), 0))
        themes.add(
            Theme(
                R.color.accent_orange,
                R.style.AppTheme_Orange,
                text(R.string.theme_orange),
                0
            )
        )
        themes.add(
            Theme(
                R.color.accent_pale_blue,
                R.style.AppTheme_PaleBlue,
                text(R.string.theme_blue),
                0
            )
        )
        themes.add(Theme(R.color.accent_green, R.style.AppTheme, text(R.string.theme_green), 0))
        themes.add(Theme(R.color.accent_cyan, R.style.AppTheme_Cyan, text(R.string.theme_cyan), 0))
        themes.add(
            Theme(
                R.color.accent_indigo,
                R.style.AppTheme_Indigo,
                text(R.string.theme_blue),
                0
            )
        )
        themes.add(
            Theme(
                R.color.accent_purple,
                R.style.AppTheme_Purple,
                text(R.string.theme_purple),
                0
            )
        )
        themes.add(
            Theme(
                R.color.accent_brown,
                R.style.AppTheme_Brown,
                text(R.string.theme_brown),
                0
            )
        )
        for (index in 0 until themes.size) {
            if (getAppTheme() == themes[index].theme) {
                themes[index].isChoose = 1
                mPosition = index
            }
        }

        themeAdapter.setList(themes)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isClick) {
            LiveEventBus.get(SET_THEME).post("")
            if (getAppTheme()!=mThemeBean.theme) {
                setAppTheme(mThemeBean.theme)
            }
        }
    }
}