package com.hust.attandance.utils.exceptions

import android.content.Context
import com.hust.attandance.R
import com.hust.attandance.data.exceptions.ExceptionEnum
import retrofit2.HttpException

open class KvException(
    open val code: Int = 0,
    override val message: String = "",
    cause: Throwable? = null
) : Throwable(message, cause) {
    constructor(code: Int, cause: Throwable) : this(code, "", cause)

    fun generateException(context: Context): KvException {
        return when (code) {

            // Generic
            ExceptionEnum.NO_INTERNET.value -> {
                if (message.isNotEmpty()) {
                    ToastException(code, message + errorCodeInformation(code), cause)
                } else {
                    ToastException(
                        code,
                        context.getString(R.string.msg_no_internet) + errorCodeInformation(code),
                        cause
                    )
                }

            }
            ExceptionEnum.UNDEFINED.value -> {
                ToastException(
                    code,
                    context.getString(R.string.error_unidentified) + errorCodeInformation(code),
                    cause
                )
            }
            else -> {
                ToastException(code, message + errorCodeInformation(code), cause)
            }
        }
    }

    fun is5xxException(): Boolean {
        return cause != null && cause is HttpException && (cause as HttpException).code() >= 500
    }


}