package com.companymeetingscheduler.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.companymeetingscheduler.R

/**
 * Created by {Kapil Sachan} on 16/01/2021.
 */

object DialogUtils {

    fun showProgressLoader(context: Context): Dialog {
        val dialog = Dialog(context)
        dialog.window?.let {
            it.requestFeature(Window.FEATURE_NO_TITLE)
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setDimAmount(0.3f)
        }
        dialog.setContentView(R.layout.dialog_progress_loader)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        return dialog
    }
}