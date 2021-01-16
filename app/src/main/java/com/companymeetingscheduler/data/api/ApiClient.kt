package com.companymeetingscheduler.data.api

import com.companymeetingscheduler.data.model.meetings.MeetingDetailItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by {Kapil Sachan} on 16/01/2021.
 */

interface ApiClient {

    @GET("schedule")
    fun hitGetMeetingsListApi(
        @Query("date") date: String?
    ): Call<ArrayList<MeetingDetailItem>>

}