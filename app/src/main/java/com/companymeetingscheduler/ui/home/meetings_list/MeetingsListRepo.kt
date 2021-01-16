package com.companymeetingscheduler.ui.home.meetings_list

import androidx.lifecycle.MutableLiveData
import com.companymeetingscheduler.base.NetworkCallback
import com.companymeetingscheduler.data.api.ApiManager
import com.companymeetingscheduler.data.model.Event
import com.companymeetingscheduler.data.model.FailureResponse
import com.companymeetingscheduler.data.model.WrappedResponse
import com.companymeetingscheduler.data.model.meetings.GetMeetingsListRequest
import com.companymeetingscheduler.data.model.meetings.MeetingDetailItem

class MeetingsListRepo {

    internal fun hitGetMeetingsListApi(getMeetingsListRequest: GetMeetingsListRequest): MutableLiveData<Event<WrappedResponse<ArrayList<MeetingDetailItem>>>> {

        val getMeetingsListResponseLiveData =
            MutableLiveData<Event<WrappedResponse<ArrayList<MeetingDetailItem>>>>()
        val wrappedResponse = WrappedResponse<ArrayList<MeetingDetailItem>>()

        ApiManager.hitGetMeetingsListApi(getMeetingsListRequest)
            .enqueue(object : NetworkCallback<ArrayList<MeetingDetailItem>>() {
                override fun onSuccess(t: ArrayList<MeetingDetailItem>?) {
                    wrappedResponse.data = t
                    getMeetingsListResponseLiveData.value = Event(wrappedResponse)
                }

                override fun onFailure(failureResponse: FailureResponse) {
                    wrappedResponse.failureResponse = failureResponse
                    getMeetingsListResponseLiveData.value = Event(wrappedResponse)
                }

                override fun onError(t: Throwable) {
                    wrappedResponse.failureResponse = FailureResponse.genericError()
                    getMeetingsListResponseLiveData.value = Event(wrappedResponse)
                }
            })

        return getMeetingsListResponseLiveData
    }

}