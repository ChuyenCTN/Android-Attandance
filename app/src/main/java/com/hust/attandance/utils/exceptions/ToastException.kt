package com.hust.attandance.utils.exceptions

import com.hust.attandance.BuildConfig
import com.hust.attandance.utils.exceptions.KvException

data class ToastException(
    override val code: Int = 0,
    override val message: String = "",
    override val cause: Throwable? = null
) : KvException(code, "$message", cause) {


}

fun errorCodeInformation(code: Int): String {
    return if (BuildConfig.DEBUG) {
        " - ErrorCode: $code"
    } else {
        ""
    }
}