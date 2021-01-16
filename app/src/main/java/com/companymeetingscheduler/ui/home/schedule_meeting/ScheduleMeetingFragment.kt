package com.companymeetingscheduler.ui.home.schedule_meeting

import android.os.Bundle
import android.view.View
import com.airhireme.base.BaseFragment
import com.companymeetingscheduler.R
import com.companymeetingscheduler.databinding.FragmentScheduleMeetingBinding

class ScheduleMeetingFragment : BaseFragment<FragmentScheduleMeetingBinding>(),
    View.OnClickListener {
    private lateinit var binding: FragmentScheduleMeetingBinding

    companion object {
        fun getInstance(): ScheduleMeetingFragment {
            return ScheduleMeetingFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_schedule_meeting
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewDataBinding()
        setUpToolbar()
        initListener()
    }

    private fun setUpToolbar() {
        binding.appbar.tvTitle.text = getText(R.string.title_schedule_a_meeting)
        binding.appbar.tvLeft.text = getText(R.string.txt_back)
    }

    private fun initListener() {
        binding.appbar.tvLeft.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.appbar.tvLeft.id -> {
                requireActivity().onBackPressed()
            }
        }
    }
}