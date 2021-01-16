package com.companymeetingscheduler.ui.home.meetings_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.companymeetingscheduler.R
import com.companymeetingscheduler.constants.AppConstants
import com.companymeetingscheduler.data.model.meetings.MeetingDetailItem
import com.companymeetingscheduler.databinding.LayoutMeetingDetailItemBinding
import com.companymeetingscheduler.utils.AppUtils


class MeetingsListAdapter(private val listener: MeetingsListAdapterListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var meetingsList = ArrayList<MeetingDetailItem>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return ViewHolderMeetingDetails(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_meeting_detail_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return meetingsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderMeetingDetails -> {
                holder.bind(meetingsList[position])
            }
        }
    }

    fun clearData() {
        meetingsList.clear()
        notifyDataSetChanged()
    }

    fun getList(): ArrayList<MeetingDetailItem> {
        return meetingsList
    }

    fun loadData(list: ArrayList<MeetingDetailItem>) {
        meetingsList.clear()
        meetingsList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolderMeetingDetails(private val binding: LayoutMeetingDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MeetingDetailItem) {
            item.startTime?.let {
                binding.tvStartTime.text = AppUtils.getDateWithUpdatedFormat(
                    it,
                    AppConstants.DateFormatConstants.DATE_FORMAT_HH_MM,
                    AppConstants.DateFormatConstants.DATE_FORMAT_HH_MM_A
                )
            }
            item.endTime?.let {
                binding.tvEndTime.text = AppUtils.getDateWithUpdatedFormat(
                    it,
                    AppConstants.DateFormatConstants.DATE_FORMAT_HH_MM,
                    AppConstants.DateFormatConstants.DATE_FORMAT_HH_MM_A
                )
            }
            binding.tvDesc.text = item.description
            binding.tvAttendees?.apply {
                var attendeesString = ""
                item.participants?.let {
                    for (i in 0 until it.size) {
                        attendeesString += if (i == 0) {
                            it[i]
                        } else {
                            String.format("%s %s", ",", it[i])
                        }
                    }
                }
                text = attendeesString
            }
        }
    }

    interface MeetingsListAdapterListener {
        fun onMeetingItemClick(gifBean: MeetingDetailItem)
    }
}