package com.companymeetingscheduler

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen


/**
 * Created by {Kapil Sachan} on 16/01/2021.
 */

class CompanyMeetingSchedulerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        AndroidThreeTen.init(this)
    }

    companion object {
        lateinit var appContext: Context
    }
}
