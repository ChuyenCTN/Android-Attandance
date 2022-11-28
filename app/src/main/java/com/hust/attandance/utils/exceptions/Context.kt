package com.hust.attandance.utils.exceptions

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

fun Context.hasPermissions(permissions: Array<String>) = permissions.all {
    ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}

fun Context.hideKeyboard(target: View?) {
    target?.let {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Context.createNotificationChannel(
    channelId: String,
    @StringRes nameId: Int,
    @StringRes descriptionId: Int,
    importance: Int,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel(channelId, getString(nameId), importance).apply {
            setShowBadge(false)
            description = getString(descriptionId)
            lockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE
        }.let {
            (getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.createNotificationChannel(
                it
            )
        }

    }
}

//fun Context.createMessageChannel() {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        createNotificationChannel(
//            Constants.CHANNEL_MESSAGE_ID,
//            R.string.noti_channel_message_title,
//            R.string.noti_channel_message_description,
//            NotificationManager.IMPORTANCE_HIGH
//        )
//    }
//}
//
//fun Context.createCommentChannel() {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        createNotificationChannel(
//            Constants.CHANNEL_COMMENT_ID,
//            R.string.noti_channel_comment_title,
//            R.string.noti_channel_comment_description,
//            NotificationManager.IMPORTANCE_HIGH
//        )
//    }
//}
//
//fun Context.createReactionChannel() {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        createNotificationChannel(
//            Constants.CHANNEL_REACTION_ID,
//            R.string.noti_channel_reaction_title,
//            R.string.noti_channel_reaction_description,
//            NotificationManager.IMPORTANCE_HIGH
//        )
//    }
//}
//
//fun Context.createOtherChannel() {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        createNotificationChannel(
//            Constants.CHANNEL_MESSAGE_ID,
//            R.string.noti_channel_other_title,
//            R.string.noti_channel_other_description,
//            NotificationManager.IMPORTANCE_DEFAULT
//        )
//    }
//}
//
//fun Context.createOmniNotificationChannel(){
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        createNotificationChannel(
//            Constants.CHANNEL_OMNI_SHOPEE_NOTIFICATION_ID,
//            R.string.omni_notification_channel_shopee_title,
//            R.string.omni_notification_channel_shopee_desciption,
//            NotificationManager.IMPORTANCE_HIGH
//        )
//    }
//}
//
//fun Context.createExpiredNotificationChannel(){
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        createNotificationChannel(
//            Constants.CHANNEL_OMNI_EXPIRED_NOTIFICATION_ID,
//            R.string.omni_notification_channel_ecommerce_title,
//            R.string.omni_notification_channel_ecommerce_desciption,
//            NotificationManager.IMPORTANCE_HIGH
//        )
//    }
//}

fun Context.openBrowser(url: String?): Boolean {
    if (url == null) {
        return false
    }
    val localUri: Uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, localUri)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    return try {
        startActivity(intent)
        true
    } catch (exception: Exception) {
        false
    }
}

fun Context.copyToClipboard(label: String? = "", content: String) {
    val clipBoardManager =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, content)
    clipBoardManager.setPrimaryClip(clip)
}