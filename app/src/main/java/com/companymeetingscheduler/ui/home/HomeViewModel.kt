package com.companymeetingscheduler.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.companymeetingscheduler.R
import com.companymeetingscheduler.constants.AppConstants
import com.companymeetingscheduler.data.model.Event
import com.companymeetingscheduler.data.model.FailureResponse
import com.companymeetingscheduler.data.model.WrappedResponse
import com.companymeetingscheduler.data.model.meetings.GetMeetingsListRequest
import com.companymeetingscheduler.data.model.meetings.MeetingDetailItem
import com.companymeetingscheduler.utils.ResourceUtil
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

class HomeViewModel : ViewModel() {

    private val repo = HomeRepo()
    var meetingsList: ArrayList<MeetingDetailItem>? = null
    var isDataLoading = false

    var selectedDate: LocalDate = LocalDate.now()
    var selectedStartTime: LocalDateTime = LocalDateTime.now().withSecond(0).withNano(0)
    var selectedEndTime: LocalDateTime = LocalDateTime.now().withSecond(0).withNano(0)

    var startTimeField = MutableLiveData<String>()
    var endTimeField = MutableLiveData<String>()
    var descriptionField = MutableLiveData<String>()

    private val validateLiveData = MutableLiveData<Event<FailureResponse>>()

    private val slotStatusLiveData = MutableLiveData<Event<Boolean>>()

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

    fun getValidationLiveData(): MutableLiveData<Event<FailureResponse>> {
        return validateLiveData
    }

    fun getSlotStatusLiveData(): MutableLiveData<Event<Boolean>> {
        return slotStatusLiveData
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

    fun scheduleMeeting() {
        if (validateMeeting()) {
            slotStatusLiveData.value = Event(checkSlotAvailability())
        }
    }

    private fun validateMeeting(): Boolean {
        if (startTimeField.value.isNullOrBlank()) {
            validateLiveData.value = Event(
                FailureResponse(
                    AppConstants.UiValidationConstants.ERROR_START_TIME,
                    ResourceUtil.getString(R.string.message_please_select_start_time)
                )
            )
            return false
        } else if (endTimeField.value.isNullOrBlank()) {
            validateLiveData.value = Event(
                FailureResponse(
                    AppConstants.UiValidationConstants.ERROR_END_TIME,
                    ResourceUtil.getString(R.string.message_please_select_end_time)
                )
            )
            return false
        } else if (selectedStartTime.isBefore(LocalDateTime.now())) {
            validateLiveData.value = Event(
                FailureResponse(
                    AppConstants.UiValidationConstants.ERROR_START_TIME,
                    ResourceUtil.getString(R.string.message_start_time_can_not_be_past_time)
                )
            )
            return false
        } else if (!selectedEndTime.isAfter(selectedStartTime)) {
            validateLiveData.value = Event(
                FailureResponse(
                    AppConstants.UiValidationConstants.ERROR_END_TIME,
                    ResourceUtil.getString(R.string.message_end_time_must_be_greater_than_start_time)
                )
            )
            return false
        } else if (descriptionField.value.isNullOrBlank()) {
            validateLiveData.value = Event(
                FailureResponse(
                    AppConstants.UiValidationConstants.ERROR_DESCRIPTION,
                    ResourceUtil.getString(R.string.message_please_enter_description)
                )
            )
            return false
        }
        return true
    }

    private fun checkSlotAvailability(): Boolean {
        var status = true
        meetingsList?.let {
            for (i in 0 until it.size) {
                if (it[i].startTime != null && it[i].endTime != null) {

                    val startHourMinute = it[i].startTime!!.split(":").toTypedArray()
                    val startDateTime =
                        LocalDateTime.now().withHour(startHourMinute[0].toInt())
                            .withMinute(startHourMinute[1].toInt()).withSecond(0).withNano(0)

                    val endHourMinute = it[i].endTime!!.split(":").toTypedArray()
                    val endDateTime =
                        LocalDateTime.now().withHour(endHourMinute[0].toInt())
                            .withMinute(endHourMinute[1].toInt()).withSecond(0).withNano(0)

                    status = selectedStartTime.isBefore(startDateTime) && selectedEndTime.isBefore(
                        startDateTime
                    ) || selectedStartTime.isAfter(endDateTime) && selectedEndTime.isAfter(
                        endDateTime
                    )

                    if (!status) {
                        break
                    }
                }
            }
        }
        return status
    }
}