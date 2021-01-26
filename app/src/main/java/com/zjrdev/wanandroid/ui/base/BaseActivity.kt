package com.zjrdev.wanandroid.ui.base

import android.os.Bundle
import android.util.TypedValue
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.coder.zzq.smartshow.snackbar.SmartSnackbar
import com.gyf.immersionbar.ktx.immersionBar
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.ext.getAppTheme
import com.zjrdev.wanandroid.ext.resourceId
import com.zjrdev.wanandroid.ui.main.ProgressDialogFragment
import timber.log.Timber

private const val TAG = "BaseActivity"

abstract class BaseActivity: AppCompatActivity() {

    private lateinit var  progressDialogFragment: ProgressDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(getAppTheme())
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
        initImmersionBar()
        setSmartSnackBar()
        initActivity(savedInstanceState)
    }

    abstract fun setLayoutId(): Int
    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initData()

    /**
     * 沉浸式状态栏  open为方法增加，该方法表示可以重写
     */
    open fun initImmersionBar() {
//        immersionBar {
//            statusBarColor(
//                TypedValue().resourceId(
//                    R.attr.colorPrimary,
//                    theme
//                )
//            ).autoStatusBarDarkModeEnable(true,0.2f)
//        }
    }

    /**
     * 提示  Toast、snackbar
     */
    open fun setSmartSnackBar() {
        SmartSnackbar.setting()
            .backgroundColorRes(
                TypedValue().resourceId(
                    R.attr.colorAccent,
                    theme
                )
            ).dismissOnLeave(true)
    }


    protected open fun initActivity(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        initData()
    }

    /**
     * 显示加载框
     */
    fun showProgressDialog(@StringRes message: Int) {
        /**
         * isInitialized 判断此类变量是否已初始化赋值  true已赋值，false未赋值
         */
        if (!this::progressDialogFragment.isInitialized) {
            progressDialogFragment = ProgressDialogFragment.newInstance()
        }
        if (!progressDialogFragment.isAdded) {
            progressDialogFragment.show(supportFragmentManager,message,false)
        }
    }

    /**
     * 隐藏加载框
     */
    fun dismissProgressDialog() {
        //progressDialogFragment已经初始化赋值，并且 progressDialogFragment是显示状态
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) {
            progressDialogFragment.dismissAllowingStateLoss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(TAG).d("onDestroy:${localClassName}")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(TAG).d("onResume:${localClassName}")
    }
}