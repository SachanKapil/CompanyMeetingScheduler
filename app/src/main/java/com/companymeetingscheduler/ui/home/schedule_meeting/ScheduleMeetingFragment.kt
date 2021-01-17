package com.companymeetingscheduler.ui.home.schedule_meeting

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.airhireme.base.BaseFragment
import com.companymeetingscheduler.R
import com.companymeetingscheduler.constants.AppConstants
import com.companymeetingscheduler.databinding.FragmentScheduleMeetingBinding
import com.companymeetingscheduler.ui.home.HomeViewModel
import com.companymeetingscheduler.utils.AppUtils
import com.companymeetingscheduler.utils.inVisible
import com.companymeetingscheduler.utils.setMeetingDate
import org.threeten.bp.LocalDateTime

class ScheduleMeetingFragment : BaseFragment<FragmentScheduleMeetingBinding>(),
    View.OnClickListener {
    private lateinit var binding: FragmentScheduleMeetingBinding
    private lateinit var viewModel: HomeViewModel

    companion object {
        fun getInstance(): ScheduleMeetingFragment {
            return ScheduleMeetingFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_schedule_meeting
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewDataBinding()
        binding.viewModel = viewModel
        setUpToolbar()
        initListener()
        initObserver()
        handleUI()
    }

    private fun setUpToolbar() {
        binding.appbar.tvTitle.text = getText(R.string.title_schedule_a_meeting)
        binding.appbar.tvLeft.text = getText(R.string.txt_back)
        binding.appbar.tvRight.inVisible()
    }

    private fun handleUI() {
        binding.tvMeetingDate.setMeetingDate(viewModel.selectedDate.toString())
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListener() {
        binding.appbar.tvLeft.setOnClickListener(this)
        binding.tvStartTime.setOnClickListener(this)
        binding.tvEndTime.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)
        binding.etDescription.setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            if (event.action and MotionEvent.ACTION_MASK == MotionEvent.ACTION_UP) {
                v.parent.requestDisallowInterceptTouchEvent(false)
            }
            false
        }
    }

    private fun initObserver() {
        viewModel.getValidationLiveData().observe(viewLifecycleOwner, { event ->
            if (event != null && !event.isAlreadyHandled) {
                event.getContent()?.let {
                    showToastShort(it.errorMessage)
                }
            }
        })

        viewModel.getSlotStatusLiveData().observe(viewLifecycleOwner, { event ->
            if (event != null && !event.isAlreadyHandled) {
                event.getContent()?.let {
                    if (it) {
                        showToastShort(getString(R.string.message_slot_available))
                    } else {
                        showToastShort(getString(R.string.message_slot_not_available))
                    }
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.appbar.tvLeft -> {
                requireActivity().onBackPressed()
            }
            binding.tvStartTime -> {
                showStartTimeDialog()
            }
            binding.tvEndTime -> {
                showEndTimeDialog()
            }
            binding.btnSubmit -> {
                viewModel.scheduleMeeting()
            }
        }
    }

    private fun showStartTimeDialog() {
        TimePickerDialog(
            requireContext(), { _, hourOfDay, minute ->
                viewModel.selectedStartTime =
                    viewModel.selectedStartTime.withHour(hourOfDay).withMinute(minute).withSecond(0).withNano(0)
                setTimeInTextView(binding.tvStartTime, viewModel.selectedStartTime)
            },
            viewModel.selectedStartTime.hour,
            viewModel.selectedStartTime.minute,
            false
        ).show()
    }

    private fun showEndTimeDialog() {
        TimePickerDialog(
            requireContext(), { _, hourOfDay, minute ->
                viewModel.selectedEndTime =
                    viewModel.selectedEndTime.withHour(hourOfDay).withMinute(minute).withSecond(0).withNano(0)
                setTimeInTextView(binding.tvEndTime, viewModel.selectedEndTime)
            },
            viewModel.selectedEndTime.hour,
            viewModel.selectedEndTime.minute,
            false
        ).show()
    }

    private fun setTimeInTextView(textView: TextView, time: LocalDateTime) {
        textView.text = AppUtils.getDateWithUpdatedFormat(
            time.toString(),
            AppConstants.DateFormatConstants.DATE_FORMAT_YYYY_MM_DD_T_HH_MM,
            AppConstants.DateFormatConstants.DATE_FORMAT_HH_MM_A
        )
    }
}