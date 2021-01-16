package com.companymeetingscheduler.utils

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.companymeetingscheduler.CompanyMeetingSchedulerApp
import com.companymeetingscheduler.R

/**
 * Created by {Kapil Sachan} on 18/11/2020.
 */

object ResourceUtil {

    fun getResourceIdFromResourceName(drawableName: String): Int {
        try {
            val res = R.drawable::class.java
            val field = res.getField(drawableName)
            return field.getInt(null)
        } catch (e: Exception) {

        }
        return -1
    }

    fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(CompanyMeetingSchedulerApp.appContext, resId)
    }

    fun getColor(colorResId: Int): Int {
        return ContextCompat.getColor(CompanyMeetingSchedulerApp.appContext, colorResId)
    }

    fun getString(resId: Int): String {
        return CompanyMeetingSchedulerApp.appContext.getString(resId)
    }

    fun getString(context: Context, resId: Int): String {
        return context.getString(resId)
    }

    fun getStringArray(resId: Int): ArrayList<String> {
        val stringArray: Array<String> =
            CompanyMeetingSchedulerApp.appContext.resources.getStringArray(resId)
        return stringArray.toCollection(ArrayList())
    }

    fun getFont(font: Int): Typeface? {
        return ResourcesCompat.getFont(CompanyMeetingSchedulerApp.appContext, font)
    }
}