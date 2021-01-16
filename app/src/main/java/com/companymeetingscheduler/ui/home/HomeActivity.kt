package com.companymeetingscheduler.ui.home

import android.os.Bundle
import com.companymeetingscheduler.R
import com.companymeetingscheduler.base.BaseActivity
import com.companymeetingscheduler.databinding.ActivityHomeBinding
import com.companymeetingscheduler.ui.home.meetings_list.MeetingsListFragment
import com.companymeetingscheduler.ui.home.schedule_meeting.ScheduleMeetingFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>(),
    MeetingsListFragment.IMeetingListFragmentHost {

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openMeetingsListFragment()
    }

    private fun openMeetingsListFragment() {
        replaceFragment(
            R.id.fl_main, MeetingsListFragment.getInstance(),
            MeetingsListFragment::class.java.simpleName
        )
    }

    override fun openScheduleMeetingFragment() {
        addFragmentWithBackStackWithAnimation(
            R.id.fl_main, ScheduleMeetingFragment.getInstance(),
            ScheduleMeetingFragment::class.java.simpleName
        )
    }
}