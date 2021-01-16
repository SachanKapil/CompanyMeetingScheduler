package com.companymeetingscheduler.base

import android.app.Dialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.airhireme.base.BaseFragment
import com.companymeetingscheduler.data.model.FailureResponse
import com.companymeetingscheduler.R
import com.companymeetingscheduler.constants.AppConstants
import com.companymeetingscheduler.data.DataManager
import com.companymeetingscheduler.dialogs.DialogUtils
import com.companymeetingscheduler.utils.AppUtils
import com.companymeetingscheduler.utils.ResourceUtil
import com.companymeetingscheduler.utils.gone
import com.companymeetingscheduler.utils.visible

/**
 * Created by {Kapil Sachan} on 16/01/2021.
 */

abstract class BaseActivity<MyDataBinding : ViewDataBinding> : AppCompatActivity() {

    private var mProgressDialog: Dialog? = null
    private lateinit var mViewDataBinding: MyDataBinding

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
    }

    open fun getViewDataBinding(): MyDataBinding {
        return mViewDataBinding
    }

    open fun connectionLiveDataCallback(isConnected: Boolean) {}

    fun addFragment(layoutResId: Int, fragment: BaseFragment<*>, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager.beginTransaction()
                .add(layoutResId, fragment, tag)
                .commit()
    }

    fun addFragmentWithAnimation(layoutResId: Int, fragment: BaseFragment<*>, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_from_right, R.anim.slide_to_left,
                    R.anim.slide_from_left, R.anim.slide_to_right
                )
                .add(layoutResId, fragment, tag)
                .commit()
    }

    fun addFragmentWithBackStack(
        layoutResId: Int,
        fragment: BaseFragment<*>,
        tag: String
    ) {
        if (supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager.beginTransaction()
                .add(layoutResId, fragment, tag)
                .addToBackStack(tag)
                .commit()
    }

    fun addFragmentWithBackStackWithAnimation(
        layoutResId: Int,
        fragment: BaseFragment<*>,
        tag: String
    ) {
        if (supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_from_right, R.anim.slide_to_left,
                    R.anim.slide_from_left, R.anim.slide_to_right
                )
                .add(layoutResId, fragment, tag)
                .addToBackStack(tag)
                .commit()
    }

    fun addFragmentWithBackStackWithUpAnimation(
        layoutResId: Int,
        fragment: BaseFragment<*>,
        tag: String
    ) {
        if (supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_up,
                    R.anim.slide_in_down,
                    R.anim.slide_out_down,
                    R.anim.slide_out_up
                )
                .add(layoutResId, fragment, tag)
                .addToBackStack(tag)
                .commit()
    }

    fun addFragmentWithStateLoss(layoutResId: Int, fragment: BaseFragment<*>, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager
                .beginTransaction()
                .add(layoutResId, fragment, tag)
                .commitAllowingStateLoss()
    }

    fun addFragmentWithBackStackWithStateLoss(
        layoutResId: Int,
        fragment: BaseFragment<*>,
        tag: String
    ) {
        if (supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager
                .beginTransaction()
                .add(layoutResId, fragment, tag)
                .addToBackStack(tag)
                .commitAllowingStateLoss()
    }

    fun replaceFragment(layoutResId: Int, fragment: BaseFragment<*>, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager
                .beginTransaction()
                .replace(layoutResId, fragment, tag)
                .commit()
    }

    fun replaceFragmentWithAnimation(layoutResId: Int, fragment: BaseFragment<*>, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null)
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_from_right, R.anim.slide_to_left,
                    R.anim.slide_from_left, R.anim.slide_to_right
                )
                .replace(layoutResId, fragment, tag)
                .commit()
    }


    fun replaceFragmentWithBackStack(layoutResId: Int, fragment: BaseFragment<*>, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) supportFragmentManager
            .beginTransaction()
            .replace(layoutResId, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    fun replaceFragmentWithBackStackWithStateLoss(
        layoutResId: Int,
        fragment: BaseFragment<*>,
        tag: String
    ) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) supportFragmentManager
            .beginTransaction()
            .replace(layoutResId, fragment, tag)
            .addToBackStack(tag)
            .commitAllowingStateLoss()
    }

    private fun getCurrentFragment(): Fragment? {
        val fragmentManager = supportFragmentManager
        return if (fragmentManager.backStackEntryCount > 0) {
            val fragmentTag =
                fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1)
                    .name
            fragmentManager.findFragmentByTag(fragmentTag)
        } else null
    }

    open fun startActivityWithEnterTransition(intent: Intent) {
        startActivity(intent)
        overridePendingTransitionEnter()
    }

    open fun startActivityForResultWithEnterTransition(intent: Intent, requestCode: Int) {
        startActivityForResult(intent, requestCode)
        overridePendingTransitionEnter()
    }

    open fun finishActivityWithExitTransition() {
        finish()
        overridePendingTransitionExit()
    }

    open fun startActivityWithEnterUpTransition(intent: Intent) {
        startActivity(intent)
        overridePendingTransitionEnterUp()
    }

    open fun startActivityForResultWithEnterUpTransition(intent: Intent, requestCode: Int) {
        startActivityForResult(intent, requestCode)
        overridePendingTransitionEnterUp()
    }

    open fun finishActivityWithExitDownTransition() {
        finish()
        overridePendingTransitionExitDown()
    }

    open fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    open fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    open fun overridePendingTransitionEnterUp() {
        overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
    }

    open fun overridePendingTransitionExitDown() {
        overridePendingTransition(R.anim.stay, R.anim.slide_out_up)
    }

    fun onFailure(failureResponse: FailureResponse) {
        if (failureResponse.errorCode == AppConstants.ApiErrorConstants.API_ERROR_CODE_SESSION_EXPIRED ||
            failureResponse.errorCode == AppConstants.ApiErrorConstants.API_ERROR_CODE_INVALID_TOKEN
        ) {
            logout()
        } else {
            failureResponse.errorMessage?.let { showToastShort(it) }
        }
    }

    fun logout() {
        clearAllNotification()
        DataManager.clearAllPreferenceData()
    }

    private fun clearAllNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }

    fun showToastLong(message: String?) {
        message?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    fun showToastShort(message: String?) {
        message?.let {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun showSnackBar(v: View, message: String?) {
        message?.let { AppUtils.showSnackBar(v, it) }
    }

    fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = DialogUtils.showProgressLoader(this)
        }
        (mProgressDialog as Dialog).show()
    }

    fun hideProgressDialog() {
        if (mProgressDialog != null && (mProgressDialog as Dialog).isShowing) {
            (mProgressDialog as Dialog).hide()
        }
    }

    fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visible()
    }

    fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.gone()
    }

    fun hideKeyboard() {
        AppUtils.hideKeyboard(this)
    }

    fun showKeyboard() {
        AppUtils.showKeyboard(this)
    }

    fun popFragment() {
        supportFragmentManager.popBackStackImmediate()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0) {
            popFragment()
        } else {
            finishActivityWithExitTransition()
        }
    }

    /**
     * Remove status bar for using full screen
     */
    fun setFullScreen() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        window.statusBarColor = Color.TRANSPARENT
    }

    /**
     * Remove status bar for using full screen
     */
    fun changeStatusBarColor(colorId: Int) {
        val window = window
        //        clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //        add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //        finally change the color
        window.statusBarColor = ResourceUtil.getColor(colorId)
    }

    /**
     * hides keyboard onClick anywhere besides edit text
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE)
            && view is EditText && !view.javaClass.name.startsWith("android.webkit.")
        ) {
            val coOrdinates = IntArray(2)
            view.getLocationOnScreen(coOrdinates)
            val x = ev.rawX + view.getLeft() - coOrdinates[0]
            val y = ev.rawY + view.getTop() - coOrdinates[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    this.window.decorView.applicationWindowToken,
                    0
                )
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}