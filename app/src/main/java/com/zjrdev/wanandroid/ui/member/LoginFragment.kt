package com.zjrdev.wanandroid.ui.member

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.coder.zzq.smartshow.snackbar.SmartSnackbar
import com.zjrdev.wanandroid.R
import com.zjrdev.wanandroid.ext.OnLazyClickListener
import com.zjrdev.wanandroid.ext.clickWithTrigger
import com.zjrdev.wanandroid.ext.hideSoftInput
import com.zjrdev.wanandroid.ui.base.BaseVMFragment
import com.zjrdev.wanandroid.vm.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.layout_custom_navigationbar_view.view.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 *Created by 张金瑞.
 *Data: 2020-12-31
 */
class LoginFragment: BaseVMFragment<LoginViewModel>() {
    override fun initVM(): LoginViewModel  = getViewModel()

    override fun startObserve() {
        mViewModel.run {
            mLoginStatus.observe(viewLifecycleOwner, Observer {
                if (it.showLoading) showProgressDialog(R.string.login_loading) else dismissProgressDialog()
                if (it.showEnd) {
                    SmartSnackbar.get(activity).show(R.string.login_success)
                    requireActivity().finish()
                }
                it.showError?.let { errorMsg ->
                    SmartSnackbar.get(activity).show(errorMsg)
                }
            })
        }
    }

    override fun setLayoutResId(): Int  = R.layout.fragment_login

    override fun initView() {
        loginNv.ivBackNavigationBar.clickWithTrigger {
            requireActivity().finish()
        }

        goRegister.clickWithTrigger {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        loginButton.clickWithTrigger {
            it.hideSoftInput()
            mViewModel.login(
                userNameLayout.editText?.text.toString().trim(),
                passwordLayout.editText?.text.toString().trim()
            )
        }
    }

    override fun initData() {

    }


}