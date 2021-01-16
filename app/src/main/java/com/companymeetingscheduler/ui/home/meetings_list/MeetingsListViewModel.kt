package com.companymeetingscheduler.ui.home.meetings_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.companymeetingscheduler.data.model.Event
import com.companymeetingscheduler.data.model.WrappedResponse
import com.companymeetingscheduler.data.model.meetings.GetMeetingsListRequest
import com.companymeetingscheduler.data.model.meetings.MeetingDetailItem
import org.threeten.bp.LocalDate

class MeetingsListViewModel : ViewModel() {

    private val repo = MeetingsListRepo()
    var selectedDate: LocalDate = LocalDate.now()
    var meetingsList: ArrayList<MeetingDetailItem>? = null
    var isDataLoading = false

    private val getMeetingsListRequestLiveData = MutableLiveData<GetMeetingsListRequest>()
    private val getMeetingsListResponseLiveData =
        Transformations.switchMap(getMeetingsListRequestLiveData) { request ->
            repo.hitGetMeetingsListApi(
                request
            )
        }

    fun getMeetingsListResponseLiveData(): LiveData<Event<WrappedResponse<ArrayList<MeetingDetailItem>>>> {
        return getMeetingsListResponseLiveData
    }

    fun hitGetMeetingsListApi() {
        val getMeetingsListRequest = GetMeetingsListRequest(
            selectedDate.toString()
        )
        getMeetingsListRequestLiveData.value = getMeetingsListRequest
    }

    fun incrementSelectedDate() {
        isDataLoading = true
        selectedDate = selectedDate.plusDays(1)
    }

    fun decrementSelectedDate() {
        isDataLoading = true
        selectedDate = selectedDate.minusDays(1)
    }
}