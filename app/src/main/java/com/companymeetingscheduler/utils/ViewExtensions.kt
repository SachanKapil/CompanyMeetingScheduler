package com.companymeetingscheduler.utils

import android.view.View
import android.widget.TextView
import com.companymeetingscheduler.constants.AppConstants

/**
 * Created by {Kapil Sachan} on 16/01/2021.
 */


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.select() {
    isSelected = true
}

fun View.unSelect() {
    isSelected = false
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun TextView.setDateInToolbar(date: String) {
    text = AppUtils.getDateWithUpdatedFormat(
        date,
        AppConstants.DateFormatConstants.DATE_FORMAT_YYYY_MM_DD,
        if (AppUtils.isPortraitMode(context)) {
            AppConstants.DateFormatConstants.DATE_FORMAT_DD_MM_YYYY
        } else {
            AppConstants.DateFormatConstants.DATE_FORMAT_EEEE_DD_MM_YYYY
        }
    )
}



