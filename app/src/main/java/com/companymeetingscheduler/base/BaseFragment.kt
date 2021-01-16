package com.airhireme.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.companymeetingscheduler.data.model.FailureResponse
import com.companymeetingscheduler.base.BaseActivity

/**
 * Created by {Kapil Sachan} on 16/01/2021.
 */

abstract class BaseFragment<MyDataBinding : ViewDataBinding> : Fragment() {

    private lateinit var mViewDataBinding: MyDataBinding
    private lateinit var mRootView: View

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = mViewDataBinding.root
        return mRootView
    }

    open fun getViewDataBinding(): MyDataBinding {
        return mViewDataBinding
    }

    fun onFailure(failureResponse: FailureResponse) {
        (activity as? BaseActivity<*>)?.onFailure(failureResponse)
    }

    fun showToastLong(message: String?) {
        if (message != null) {
            (activity as? BaseActivity<*>)?.showToastLong(message)
        }
    }

    fun showToastShort(message: String?) {
        message?.let { (activity as? BaseActivity<*>)?.showToastShort(it) }
    }

    fun showSnackBar(v: View, message: String?) {
        message?.let { (activity as? BaseActivity<*>)?.showSnackBar(v, it) }
    }

    fun showProgressDialog() {
        (activity as? BaseActivity<*>)?.showProgressDialog()
    }

    fun hideProgressDialog() {
        (activity as? BaseActivity<*>)?.hideProgressDialog()
    }

    open fun showProgressBar(progressBar: ProgressBar) {
        (activity as? BaseActivity<*>)?.showProgressBar(progressBar)
    }

    open fun hideProgressBar(progressBar: ProgressBar) {
        (activity as? BaseActivity<*>)?.hideProgressBar(progressBar)
    }

    fun hideKeyboard() {
        (activity as? BaseActivity<*>)?.hideKeyboard()
    }

    fun showKeyboard() {
        (activity as? BaseActivity<*>)?.showKeyboard()
    }

    fun popFragment() {
        (activity as? BaseActivity<*>)?.popFragment()
    }

    fun finishActivityWithExitTransition() {
        (activity as? BaseActivity<*>)?.finishActivityWithExitTransition()
    }

    fun logout() {
        (activity as? BaseActivity<*>)?.logout()
    }

    fun setFullScreen() {
        (activity as? BaseActivity<*>)?.setFullScreen()
    }

    fun changeStatusBarColor(colorId: Int) {
        (activity as? BaseActivity<*>)?.changeStatusBarColor(colorId)
    }

    override fun onStop() {
        hideProgressDialog()
        super.onStop()
    }
}