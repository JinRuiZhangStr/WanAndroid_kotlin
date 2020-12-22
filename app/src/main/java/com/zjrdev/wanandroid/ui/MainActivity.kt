package com.zjrdev.wanandroid.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.coder.zzq.smartshow.snackbar.SmartSnackbar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.ext.SET_THEME
import com.zjrdev.wanandroid.ui.base.BaseActivity
import com.zjrdev.wanandroid.ui.homemine.MyHomePageFragment
import com.zjrdev.wanandroid.ui.homepage.MainFragment
import com.zjrdev.wanandroid.ui.homeplaze.MainPlazeFragment
import com.zjrdev.wanandroid.ui.homesyetem.HomeSystemFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    /*
    activity重建时Fragment选中的位置
     */
    private var last_position= 0
    /*
    装 fragment的集合容器
     */
    private val fragments = arrayListOf<Fragment>()
    /*
    要显示的fragment
     */
    private var currentFragment: Fragment?= null
    /*
    要隐藏的fragment
     */
    private var hideFragment: Fragment?= null

    /**
     * 监听按下back键的时间区直
     */
    private var previousTimeMillis = 0L

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //activity重建时保存页面的位置
        outState.putInt("last_postion",last_position)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //获取重建时的fragment的位置
        last_position = savedInstanceState.getInt("last_position")
        setSelectedFragment(last_position)
        bottom_nav_view.selectedItemId = bottom_nav_view.menu.getItem(last_position).itemId
    }

    override fun setLayoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {

        LiveEventBus.get(SET_THEME).observe(this, Observer {
            recreate()
        })

        //将主页需要展示的fragment添加到集合容器中
        fragments.apply {
            add(MainFragment())
            add(HomeSystemFragment())
            add(MainPlazeFragment())
            add(MyHomePageFragment())
        }

        //根据传入的Bundle对象判断是正常启动还是重建 true表示正常启动，false表示重建
        if(savedInstanceState == null) {
            setSelectedFragment(0)
        }

        //BottomNavigationView 点击事件监听
        bottom_nav_view.setOnNavigationItemSelectedListener { item: MenuItem ->
            // 跳转指定页面：Fragment
            when (item.itemId) {
                R.id.home_page -> setSelectedFragment(0)
                R.id.home_system -> setSelectedFragment(1)
                R.id.home_plaza -> setSelectedFragment(2)
                R.id.home_mine -> setSelectedFragment(3)
            }
            true
        }
    }

    override fun initData() {}

    /**
     * 根据位置选择显示的Fragment
     */
    private fun setSelectedFragment(position: Int) {
        //获取底部菜单栏选中的item  checked状态为true
        bottom_nav_view.menu.getItem(position).isChecked = true
        // 实例化 碎片管理者
        val fragmentManager = supportFragmentManager
        // 通过supportFragmentManager 获取到事务管理者
        val transaction = fragmentManager.beginTransaction()
        //通过 findFragmentTag(解决了activity重建时新建实例的问题)  position 获取到当前显示的fragment
        currentFragment = fragmentManager.findFragmentByTag("fragment$position")
        //通过 findFragmentTag(解决了activity重建时新建实例的问题) last_position  获取到需要隐藏的fragment
        hideFragment = fragmentManager.findFragmentByTag("fragment$last_position")
        //如果位置不同
        if (position!=last_position) {
            //将需要隐藏的fragment hide
            hideFragment?.let { transaction.hide(it) }
            //如果需要显示的fragment是null
            if (currentFragment == null) {
                //通过装有fragment的集合容器的下表索引为position实例化为 需要显示的fragment
                currentFragment = fragments[position]
                //添加到事务管理者中并显示 通过查看源码，我们会看到  add()/show()最后都会执行addOp()方法
                currentFragment?.let{transaction.add(R.id.fl_container,it,"fragment$position")}
            } else {
                //不为空的话  直接 show
                currentFragment?.let { transaction.show(it) }
            }
        }
        //如果位置相同
        if (position == last_position) {
            //需要显示的fragment为null
            if (currentFragment == null) {
                //通过装有fragment的集合容器的下表索引为position实例化为 需要显示的fragment
                currentFragment = fragments[position]
                //添加到事务管理者中并显示
                currentFragment?.let{transaction.add(R.id.fl_container,it,"fragment$position")}
            }
        }
        //提交事务管理者
        transaction.commit()
        //将当前Fragment显示的位置设置为activity重建时Fragment选中的位置
        last_position = position

    }

    /**
     * recreate是指当内存不足时，Activity被回收，但再次来到此Activity时，系统重新恢复的过程
     */
    override fun recreate() {
        val beginTransaction = supportFragmentManager.beginTransaction()
        for (i in 1..2) {
            supportFragmentManager.findFragmentByTag("fragment$i")?.let {
                beginTransaction.remove(it)
            }

        }
        //当activity再次被恢复时commit之后的状态将丢失。如果丢失也没关系，那么使用commitAllowingStateLoss()方法
        beginTransaction.commitAllowingStateLoss()
        super.recreate()
    }

    /**
     *监听返回键 退出程序返回桌面
     */
    override fun onBackPressed() {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - previousTimeMillis > 2000) {
            super.onBackPressed()
        } else {
            SmartSnackbar.get(this).show(R.string.press_again_to_exit)
        }
        super.onBackPressed()
    }
}