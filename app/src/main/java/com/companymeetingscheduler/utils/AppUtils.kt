package com.companymeetingscheduler.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.companymeetingscheduler.CompanyMeetingSchedulerApp
import com.companymeetingscheduler.R
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 * Created by {Kapil Sachan} on 16/01/2021.
 */

object AppUtils {

    fun hideKeyboard(context: Activity) {
        try {
            val inputManager: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                context.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showKeyboard(context: Activity) {
        try {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(context.currentFocus, InputMethodManager.SHOW_IMPLICIT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("HardwareIds")
    fun getUniqueDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    fun aspectRatio(width: Int, height: Int): Double {
        return max(width, height).toDouble() / min(width, height)
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId: Int =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    fun showSnackBar(mSnackView: View, message: String): Snackbar {
        val snackBar = Snackbar.make(mSnackView, message, Snackbar.LENGTH_SHORT)
        snackBar.view.let {
            it.setBackgroundColor(
                ContextCompat.getColor(
                    CompanyMeetingSchedulerApp.appContext,
                    R.color.green
                )
            )
            it.findViewById<TextView>(R.id.snackbar_text).setTextColor(Color.WHITE)
            it.findViewById<TextView>(R.id.snackbar_text).maxLines = 3
        }
        snackBar.show()
        return snackBar
    }

    fun getDateWithUpdatedFormat(
        date: String,
        sourceFormat: String,
        targetFormat: String
    ): String {
        val sdfSource = SimpleDateFormat(sourceFormat, Locale.US)
        val sdfTarget = SimpleDateFormat(targetFormat, Locale.US)
        var sourceDate: Date? = null
        try {
            sourceDate = sdfSource.parse(date)
        } catch (parseException: ParseException) {
            parseException.printStackTrace()
        }
        sourceDate?.let {
            return sdfTarget.format(it)
        } ?: return ""
    }

    fun isPortraitMode(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }
}