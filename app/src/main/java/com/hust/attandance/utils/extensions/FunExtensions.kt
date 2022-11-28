package com.hust.attandance.utils.extensions

import android.content.Context
import com.hust.attandance.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


inline fun <reified T> isEqual(first: List<T>, second: List<T>): Boolean {
    if (first.size != second.size) {
        return false
    }
    return first.containsAll(second) && second.containsAll(first)
}

fun <T> List<T>?.getPositionOrNull(position: Int): T? {
    return if (this != null && position >= 0 && position < this.size) {
        this[position]
    } else {
        null
    }
}

fun <T> Any?.castList(clazz: Class<T>): ArrayList<T>? {
    if (this == null) {
        return null
    }
    if (this is List<*>) {
        val result = arrayListOf<T>()
        for (o in this) {
            result.add(clazz.cast(o)!!)
        }
        return result
    }
    throw ClassCastException()
}

/**
 * Price and Quantity Utils
 */

fun String.fromPriceStr(): Double {
    return fromDoubleStr()
}

fun String.fromPercentStr(): Double {
    return fromDoubleStr()
}

fun String.fromQuantityStr(): Double {
    return fromDoubleStr()
}

fun String.fromDoubleStr(): Double {
    val textValidate = replace(",", "")
    return when {
        textValidate.isBlank() -> 0.0
        else -> textValidate.toDouble()
    }
}

fun Double.toPriceStr(): String {
    return toDoubleStr("###,###,###,###.###")
}

fun Double.toPercentStr(): String {
    return toDoubleStr("##.##")
}

fun Double.toQuantityStr(): String {
    return toDoubleStr("###,###,###,###.###")
}

fun Double.toDoubleStr(format: String = "##.#####"): String {
    val df = DecimalFormat(format, DecimalFormatSymbols.getInstance(Locale.US))
    return df.format(this)
}

/**
 * Time Utils
 */
fun Long.toTimeHhMm(): String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.US)
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(this))
}

fun Long.toTimeHhMmSs(): String {
    val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.US)
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(this))
}

fun Long.toTimeDDMMYYYY(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(this))
}

fun Long.toTimeDDMMYYYYHHmm(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US)
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(this))
}

fun Long.toISO8601(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(this))
}

fun Long.toISO8601Z(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(this))
}

fun Long.toISO8601ZZ(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ", Locale.US)
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(this))
}

fun String.fromISO8601ZZ(): Long {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ", Locale.US)
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.parse(this).time
}

fun Long.toISO8601ZZZZZ(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.US)
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(this))
}

fun String.fromISO8601ZZZZZ(): Long {
    return try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.US)
        dateFormat.timeZone = TimeZone.getDefault()
        dateFormat.parse(this).time
    } catch (ex: Exception) {
        ex.printStackTrace()
        0
    }
}

fun String.fromISO8601SS(): Long {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.US)
    dateFormat.timeZone = TimeZone.getDefault()
    return try {
        dateFormat.parse(this).time
    } catch (ex: Exception) {
        ex.printStackTrace()
        0
    }
}


fun Long.toTimeStr(format: String): String {
    val dateFormat = SimpleDateFormat(format, Locale.US)
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(this)
}

fun String.fromISO8601SS(expectPattern: String = "dd/MM/yyyy HH:mm:ss"): String {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS"
    val format = SimpleDateFormat(pattern, Locale.US)
    format.timeZone = TimeZone.getDefault()
    val date: Date? = format.parse(this)
    date?.let {
        val millis: Long = date.time
        val newFormat = SimpleDateFormat(expectPattern, Locale.US)
        format.timeZone = TimeZone.getDefault()
        return newFormat.format(millis)
    }
    return ""
}

fun String.fromTimeStr(format: String): Long {
    val dateFormat = SimpleDateFormat(format, Locale.US)
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.parse(this).time
}

fun String.toISO8601(inputPattern: String = "dd/MM/yyyy HH:mm"): String {
    return try {
        val format = SimpleDateFormat(inputPattern, Locale.US)
        val date: Date = format.parse(this)
        val millis: Long = date.time
        val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'0000+07:00'"
        val newformat = SimpleDateFormat(pattern, Locale.US)
        format.timeZone = TimeZone.getDefault()
        newformat.format(millis)
    } catch (ex: Exception) {
        ex.printStackTrace()
        ""
    }
}

fun String.toTimeDDMMYYYYHHMM(): String {
    return try {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.US)
        val date: Date = format.parse(this)
        val millis: Long = date.time
        val pattern = "dd/MM/yyyy HH:mm"
        val newformat = SimpleDateFormat(pattern, Locale.US)
        format.timeZone = TimeZone.getDefault()
        newformat.format(millis)
    } catch (ex: Exception) {
        ex.printStackTrace()
        ""
    }
}

fun String.toTimeDDMMYYYYHHMMFromIsoZZZZZ(): String {
    return try {
        val fromISO8601ZZZZZ = this.fromISO8601ZZZZZ()
        if (fromISO8601ZZZZZ == 0L)
            ""
        else fromISO8601ZZZZZ.toTimeDDMMYYYYHHmm()
    } catch (ex: Exception) {
        ex.printStackTrace()
        ""
    }
}

fun String?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}

fun Long.formatDurationHHmm(): String {
    val time = this / 1000
    return String.format("%02d:%02d", time / 60, time % 60)
}

/**
 * Gen the different time into string
 *
 * @param context
 * @return
 */


fun Long.toRecentTimeFormat(context: Context): String {
    val currentTime = System.currentTimeMillis()
    return when (val timeDiff = currentTime - this) {
        in (Long.MIN_VALUE..TimeUnit.SECONDS.toMillis(60)) -> context.getString(R.string.label_time_recent)
        in (TimeUnit.MINUTES.toMillis(1)..TimeUnit.MINUTES.toMillis(60)) -> {
            (timeDiff / TimeUnit.MINUTES.toMillis(1)).let {
                context.getString(R.string.label_time_minutes_ago, it)
            }
        }
        in (TimeUnit.HOURS.toMillis(1)..TimeUnit.HOURS.toMillis(24)) -> {
            (timeDiff / TimeUnit.HOURS.toMillis(1)).let {
                context.getString(R.string.label_time_hours_ago, it)
            }
        }
        in (TimeUnit.DAYS.toMillis(1)..TimeUnit.DAYS.toMillis(7)) -> {
            (timeDiff / TimeUnit.DAYS.toMillis(1)).let {
                context.getString(R.string.label_time_days_ago, it)
            }
        }
        else -> {
            (timeDiff / TimeUnit.DAYS.toMillis(7)).let {
                context.getString(R.string.label_time_week_ago, it)
            }
        }
    }
}