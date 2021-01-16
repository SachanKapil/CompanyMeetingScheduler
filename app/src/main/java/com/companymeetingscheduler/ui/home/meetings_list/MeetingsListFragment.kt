package com.companymeetingscheduler.ui.home.meetings_list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airhireme.base.BaseFragment
import com.companymeetingscheduler.R
import com.companymeetingscheduler.data.model.meetings.MeetingDetailItem
import com.companymeetingscheduler.databinding.FragmentMeetingsListBinding
import com.companymeetingscheduler.utils.AppUtils
import com.companymeetingscheduler.utils.enable
import com.companymeetingscheduler.utils.setDateInToolbar
import kotlinx.android.synthetic.main.layout_progress_bar.view.*
import org.threeten.bp.LocalDate

class MeetingsListFragment : BaseFragment<FragmentMeetingsListBinding>(),
    SwipeRefreshLayout.OnRefreshListener, MeetingsListAdapter.MeetingsListAdapterListener,
    View.OnClickListener {
    private lateinit var binding: FragmentMeetingsListBinding
    private lateinit var host: IMeetingListFragmentHost
    private lateinit var adapter: MeetingsListAdapter
    private lateinit var viewModel: MeetingsListViewModel

    companion object {
        fun getInstance(): MeetingsListFragment {
            return MeetingsListFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_meetings_list
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IMeetingListFragmentHost) {
            host = context
        } else {
            throw IllegalStateException("host must implement IGifListingsFragmentHost")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        initViewModel()
    }

    private fun initAdapter() {
        adapter = MeetingsListAdapter(this)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MeetingsListViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = getViewDataBinding()
        setUpToolbar()
        handleUI()
        initListener()
        initRecyclerView()
        initObservers()
        getMeetingsList()
    }

    private fun getMeetingsList() {
        viewModel.meetingsList?.let {
            when {
                viewModel.isDataLoading -> {
                    binding.progressBarLayout.showLoading()
                }
                it.size != 0 -> {
                    adapter.loadData(it)
                }
                else -> {
                    binding.progressBarLayout.setErrorWithOutRetryButton(
                        getString(R.string.message_no_meeting_scheduled)
                    )
                }
            }
        } ?: let {
            if (AppUtils.isNetworkAvailable(requireContext())) {
                binding.progressBarLayout.showLoading()
                hitGetMeetingsListApi()
            } else {
                binding.progressBarLayout.setErrorWithRetryButton(getString(R.string.message_no_internet_connection))
            }
        }
    }

    private fun setUpToolbar() {
        binding.appbar.tvLeft.text = getText(R.string.txt_prev)
        binding.appbar.tvRight.text = getText(R.string.txt_next)
    }

    private fun initListener() {
        binding.refreshLayout.setOnRefreshListener(this)
        binding.refreshLayout.setColorSchemeResources(R.color.green)
        binding.progressBarLayout.btnTryAgain.setOnClickListener(this)
        binding.appbar.tvLeft.setOnClickListener(this)
        binding.appbar.tvRight.setOnClickListener(this)
        binding.btnScheduleCompanyMeeting.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        binding.rvMeetings.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvMeetings.layoutManager = layoutManager
    }


    private fun initObservers() {
        viewModel.getMeetingsListResponseLiveData()
            .observe(viewLifecycleOwner, { wrappedResponseEvent ->
                if (wrappedResponseEvent != null && !wrappedResponseEvent.isAlreadyHandled) {
                    viewModel.isDataLoading = false
                    binding.progressBarLayout.hideLoading()
                    binding.refreshLayout.isRefreshing = false
                    val objectWrappedResponse = wrappedResponseEvent.getContent()
                    objectWrappedResponse?.failureResponse?.let {
                        onFailure(it)
                    } ?: let {
                        objectWrappedResponse?.data?.let { meetingsList ->
                            if (meetingsList.size != 0) {
                                adapter.loadData(meetingsList)
                            } else {
                                adapter.clearData()
                                binding.progressBarLayout.setErrorWithOutRetryButton(
                                    getString(R.string.message_no_meeting_scheduled)
                                )
                            }
                        }
                    }
                }
            })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.meetingsList = adapter.getList()
    }

    override fun onRefresh() {
        if (AppUtils.isNetworkAvailable(requireContext())) {
            hitGetMeetingsListApi()
        } else {
            binding.refreshLayout.isRefreshing = false
            showToastShort(getString(R.string.message_no_internet_connection))
        }
    }

    private fun hitGetMeetingsListApi() {
        viewModel.hitGetMeetingsListApi()
    }

    override fun onMeetingItemClick(gifBean: MeetingDetailItem) {
        showToastShort("onMeetingItemClick")
    }

    interface IMeetingListFragmentHost {
        fun openScheduleMeetingFragment()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.progressBarLayout.btnTryAgain -> {
                if (AppUtils.isNetworkAvailable(requireContext())) {
                    binding.progressBarLayout.showLoading()
                    hitGetMeetingsListApi()
                } else {
                    showToastShort(getString(R.string.message_no_internet_connection))
                    binding.progressBarLayout.setErrorWithRetryButton(getString(R.string.message_no_internet_connection))
                }
            }
            binding.appbar.tvLeft -> {
                if (AppUtils.isNetworkAvailable(requireContext())) {
                    viewModel.decrementSelectedDate()
                    adapter.clearData()
                    binding.progressBarLayout.showLoading()
                    handleUI()
                    hitGetMeetingsListApi()
                } else {
                    showToastShort(getString(R.string.message_no_internet_connection))
                    binding.progressBarLayout.setErrorWithRetryButton(getString(R.string.message_no_internet_connection))
                }
            }
            binding.appbar.tvRight -> {
                if (AppUtils.isNetworkAvailable(requireContext())) {
                    viewModel.incrementSelectedDate()
                    adapter.clearData()
                    binding.progressBarLayout.showLoading()
                    handleUI()
                    hitGetMeetingsListApi()
                } else {
                    showToastShort(getString(R.string.message_no_internet_connection))
                    binding.progressBarLayout.setErrorWithRetryButton(getString(R.string.message_no_internet_connection))
                }
            }
            binding.btnScheduleCompanyMeeting -> {
                if (viewModel.selectedDate.isBefore(LocalDate.now())) {
                    showToastShort(getString(R.string.message_you_can_not_schedule_meeting_for_past_date))
                } else {
                    binding.btnScheduleCompanyMeeting.enable()
                }
            }
        }
    }

    private fun handleUI() {
        binding.appbar.tvTitle.setDateInToolbar(viewModel.selectedDate.toString())
    }
}