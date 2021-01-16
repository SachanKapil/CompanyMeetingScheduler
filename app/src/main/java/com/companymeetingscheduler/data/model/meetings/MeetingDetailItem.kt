package com.companymeetingscheduler.data.model.meetings

import com.google.gson.annotations.SerializedName

data class MeetingDetailItem(
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("end_time")
    var endTime: String? = null,
    @SerializedName("participants")
    var participants: ArrayList<String>? = null,
    @SerializedName("start_time")
    var startTime: String? = null
)