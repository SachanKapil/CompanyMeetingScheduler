package com.companymeetingscheduler.utils

import android.util.Log

/**
 * Created by {Kapil Sachan} on 18/11/2020.
 */

object PrintLog {
    private val LOG: Boolean = com.companymeetingscheduler.BuildConfig.DEBUG
    fun i(tag: String, message: String) {
        if (LOG) Log.i(tag, message)
    }

    fun e(tag: String, message: String) {
        if (LOG) Log.e(tag, message)
    }

    fun e(tag: String, message: String, tr: Throwable) {
        if (LOG) Log.e(tag, message, tr)
    }

    fun d(tag: String, message: String) {
        if (LOG) Log.d(tag, message)
    }

    fun v(tag: String, message: String) {
        if (LOG) Log.v(tag, message)
    }

    fun w(tag: String, message: String) {
        if (LOG) Log.w(tag, message)
    }
}