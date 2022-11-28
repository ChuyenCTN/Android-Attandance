package com.hust.attandance.utils.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.window.layout.WindowMetricsCalculator

fun Activity.getScreenSize(): Rect {
    return WindowMetricsCalculator.getOrCreate()
        .computeCurrentWindowMetrics(this).bounds // E.g. [0 0 1350 1800]
}

fun Activity.getScreenWidth(): Int {
    return getScreenSize().width()
}

fun Activity.getScreenHeight(): Int {
    return getScreenSize().height()
}

fun Context.isConnectionOn(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val connection = connectivityManager.getNetworkCapabilities(network)
        return connection != null && (
                connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    } else {
        val activeNetwork = connectivityManager.activeNetworkInfo
        if (activeNetwork != null) {
            return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
        }
        return false
    }
}