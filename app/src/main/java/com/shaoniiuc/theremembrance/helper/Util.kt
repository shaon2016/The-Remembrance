package com.shaoniiuc.theremembrance.helper

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

object Util {


    fun formatTime(dateLong: Long): String {
        val timeFormat = SimpleDateFormat("H:mm")
        return timeFormat.format(Date(dateLong))
    }

    fun formatDate(dateLong: Long): String {
        val dateFormat = SimpleDateFormat("MMM d, yyyy")
        return dateFormat.format(Date(dateLong))
    }

    fun showShortToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun _24HrBack(initialTimeInMillis : Long): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = initialTimeInMillis
        cal.add(Calendar.HOUR, -24)
        return cal.timeInMillis
    }
}