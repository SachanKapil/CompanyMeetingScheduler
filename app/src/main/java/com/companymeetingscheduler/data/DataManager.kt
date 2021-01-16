package com.companymeetingscheduler.data

import com.airhireme.data.preferences.PreferenceManager

/**
 * Created by {Kapil Sachan} on 16/01/2021.
 */

object DataManager {

    fun clearAllPreferenceData() {
        PreferenceManager.clearAllPrefs()
    }
}