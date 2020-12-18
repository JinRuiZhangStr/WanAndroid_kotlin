package com.zjrdev.wanandroid.ui.base

import android.os.Bundle
import com.zjrdev.wanandroid.vm.BaseViewModel

/**
 * 抽象类、方法是默认带 open  代表子类必须重写该方法
 * open关键字 与java 中的 final相反:它允许别的类继承这个类。默认情形下，kotlin 中所有的类都是 final ,用来表示他可以被继承。
 * 若子类要重写父类中的方法，则需在父类的方法前面加open关键字，然后在子类重写的方法前加override关键字
 * lateinit var延迟初始化 只能用来修饰类属性，不能用来修饰局部变量，并且只能用来修饰对象，不能用来修饰基本类型(因为基本类型的属性在类加载后的准备阶段都会被初始化为默认值)
 * by lazy要求属性声明为val，即不可变变量
 */
abstract class BaseVMActivity<VM: BaseViewModel>: BaseActivity() {
    lateinit var mViewModel: VM

    override fun initActivity(savedInstanceState: Bundle?) {
        mViewModel =initVM()
        startObserver()
        super.initActivity(savedInstanceState)
    }

    abstract fun initVM():VM

    abstract fun startObserver()
}