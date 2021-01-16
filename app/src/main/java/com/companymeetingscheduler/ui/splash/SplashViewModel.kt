package com.companymeetingscheduler.ui.splash

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.companymeetingscheduler.constants.AppConstants

class SplashViewModel : ViewModel() {

    private val splashTimeOut: Long = 3000

    private val splashLiveData = MutableLiveData<Int>()

    internal fun getSplashLiveData(): MutableLiveData<Int> {
        return splashLiveData
    }

    // Method to start new activity after 1 second
    fun showSplashScreen() {
        Handler().postDelayed({
            splashLiveData.value = AppConstants.ScreenConstants.OPEN_HOME_SCREEN
        }, splashTimeOut)
    }

}