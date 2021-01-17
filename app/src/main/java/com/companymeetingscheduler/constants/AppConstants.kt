package com.companymeetingscheduler.constants

/**
 * Created by {Kapil Sachan} on 16/01/2021.
 */

object AppConstants {

    object NetworkingConstants {
        const val INTERNAL_SERVER_ERROR_CODE = 500
        const val NO_INTERNET_CONNECTION = 1
    }

    object ApiErrorConstants {
        const val API_ERROR_CODE_INVALID_TOKEN = 419
        const val API_ERROR_CODE_SESSION_EXPIRED = 420
    }

    object ScreenConstants {
        const val OPEN_HOME_SCREEN = 1
    }

    object ScreenOrientationConstants {
        const val KEY_ORIENTATION_CHANGE = "key_orientation_change"
    }

    object UiValidationConstants {
        const val ERROR_START_TIME = 100
        const val ERROR_END_TIME = 101
        const val ERROR_DESCRIPTION = 102
        const val ERROR_SLOT_NOT_AVAILABLE = 103
    }

    object DateFormatConstants {
        const val DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd"
        const val DATE_FORMAT_EEEE_DD_MM_YYYY = "EEEE, dd-MM-yyyy"
        const val DATE_FORMAT_DD_MM_YYYY = "dd-MM-yyyy"
        const val DATE_FORMAT_HH_MM = "HH:MM"
        const val DATE_FORMAT_HH_MM_A = "hh:mm a"
        const val DATE_FORMAT_YYYY_MM_DD_T_HH_MM = "yyyy-MM-dd'T'HH:mm"
    }
}
