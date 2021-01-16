package com.companymeetingscheduler.custom_works

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.companymeetingscheduler.R
import com.companymeetingscheduler.databinding.LayoutProgressBarBinding

class ProgressBarLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private lateinit var binding: LayoutProgressBarBinding

    init {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_progress_bar, this, true
        )
    }

    fun showLoading() {
        binding.root.visibility = View.VISIBLE
        binding.pbLoading.visibility = View.VISIBLE
        binding.btnTryAgain.visibility = View.GONE
        binding.tvErrorMessage.visibility = View.GONE
    }

    fun setErrorWithRetryButton(message: String?) {
        binding.root.visibility = View.VISIBLE
        binding.pbLoading.visibility = View.GONE
        binding.btnTryAgain.visibility = View.VISIBLE
        binding.tvErrorMessage.visibility = View.VISIBLE
        binding.tvErrorMessage.text = message
    }

    fun setErrorWithOutRetryButton(message: String?) {
        binding.root.visibility = View.VISIBLE
        binding.pbLoading.visibility = View.GONE
        binding.btnTryAgain.visibility = View.GONE
        binding.tvErrorMessage.visibility = View.VISIBLE
        binding.tvErrorMessage.text = message
    }

    fun hideLoading() {
        binding.pbLoading.visibility = View.GONE
        binding.btnTryAgain.visibility = View.GONE
        binding.tvErrorMessage.visibility = View.GONE
        binding.root.visibility = View.GONE
    }
}