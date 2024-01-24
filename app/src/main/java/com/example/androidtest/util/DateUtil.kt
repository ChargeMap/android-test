package com.example.androidtest.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale

object DateUtil {

    val TAG = DateUtil::class.java.simpleName

    /**
     * @param date ISO date format
     * @param format expected destination format
     */
    fun formatDate(date: String, format: String): String {
        if (date.isEmpty()) return ""
        return try {
            val sourceDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val destinationDateFormat = SimpleDateFormat(format, Locale.getDefault())
            destinationDateFormat.format(sourceDateFormat.parse(date)!!)
        } catch (e: Exception) {
            Log.e(TAG, e.message, e.cause)
            ""
        }
    }

    const val DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm"
}