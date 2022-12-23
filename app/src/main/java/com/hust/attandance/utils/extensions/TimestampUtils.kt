package com.hust.attandance.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

//fun Long.toISO8601Z(): String {
//    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
//    dateFormat.timeZone = TimeZone.getTimeZone("GMT")
//    return dateFormat.format(Date(this))
//}

fun String.fromISO8601Z(): Long {
    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        dateFormat.parse(this).time
    } catch (ex: Exception) {
        ex.printStackTrace()
        0
    }
}

fun String.fromISO8601(): Long {
    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("GMT+7")
        dateFormat.parse(this).time
    } catch (ex: Exception) {
        ex.printStackTrace()
        0
    }
}




fun Long.toISO8601HH(): String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.US)
    dateFormat.timeZone = TimeZone.getTimeZone("GMT+7")
    return dateFormat.format(Date(this))
}

fun Long.toISO8601DMY(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    dateFormat.timeZone = TimeZone.getTimeZone("GMT+7")
    return dateFormat.format(Date(this))
}



fun Long.toCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    return cal
}

fun String.fromISO8601ZToCalendar(): Calendar? {
    val cal = Calendar.getInstance()
    cal.timeInMillis = fromISO8601Z()
    return cal
}

fun String.fromISO8601ZToHHmm(): String {
    val cal = fromISO8601Z()
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(cal))
}

fun String.fromISO8601ToHHmm(): String {
    val cal = fromISO8601()
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(cal))
}

fun getCashFlowHeaderDate(calendar: Calendar): String{
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(calendar.time)
}


fun String.toNewDateFormat(oldFormat: String, newFormat: String): String{
    val inputFormat = SimpleDateFormat(oldFormat, Locale.getDefault())
    val outputFormat = SimpleDateFormat(newFormat, Locale.getDefault())
    val date = inputFormat.parse(this)
    return outputFormat.format(date)
}

fun Long.toPaymentDateCashFlow(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("GMT+7")
    return dateFormat.format(Date(this))
}

fun String.convertToMillisecond(format : String): Long {
    return try {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("GMT+7")
        dateFormat.parse(this).time
    } catch (ex: Exception) {
        ex.printStackTrace()
        0
    }
}

fun String.fromISO8601ToCalendar(): Calendar? {
    val cal = Calendar.getInstance()
    cal.timeInMillis = fromISO8601()
    return cal
}

fun String.toISO8601Date(inputPattern: String = "MM/dd/yyyy HH:mm"): Date? {
    return try {
        val format = SimpleDateFormat(inputPattern, Locale.getDefault())
        format.parse(this)
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}