package com.hust.attandance.data.exceptions

import android.content.Context
import com.hust.attandance.data.interceptors.NoConnectionInterceptor
import com.hust.attandance.utils.exceptions.ExceptionFactory
import com.hust.attandance.utils.exceptions.KvException
import com.hust.attandance.utils.others.GsonParser
import org.koin.java.KoinJavaComponent.inject


class ApiExceptionFactory(val context: Context) : ExceptionFactory {
//    private val gsonParser by inject(GsonParser::class.java)

    override fun buildError(cause: Throwable): KvException? {
        var exception: KvException? = null
//        if (cause is HttpException) {
//            if (cause.getUrlHost() == "graph.facebook.com") {
//                exception = KvException(ExceptionEnum.ACCESS_DENIED.value, cause)
//            } else {
//                val errorBody = cause.response()?.errorBody()?.string()
//                val errorResponse =
//                    if (errorBody != null && cause.getContentType().contains("json")) {
//                        gsonParser.fromJson<KvResponse<Any>>(errorBody)
//                    } else null
//                if (errorResponse != null) {
//                    val message =
//                        errorResponse.responseStatus?.message ?: cause.message ?: cause.message()
//                    when {
//                        cause.code() == 423 -> {
//                            exception = KvException(
//                                code = cause.code(),
//                                if (Utils.isFreeVersion(context)) {
//                                    context.getString(R.string.wrong_app_free_version)
//                                } else {
//                                    context.getString(R.string.wrong_app_pro_version)
//                                },
//                                cause
//                            )
//                        }
//                        cause.code() == 401 -> {
//                            exception =
//                                KvException(
//                                    if (cause.getRelativePath() == "auth")
//                                        ExceptionEnum.LOGIN_FAILED.value
//                                    else ExceptionEnum.SESSION_EXPIRE_KV.value,
//                                    message,
//                                    cause
//                                )
//                        }
//                        cause.code() == 420 -> {
//                            exception =
//                                if (errorResponse.responseStatus?.errorCode == Constants.ERROR_MESSAGE_UNTRUSTED_DEVICE || errorResponse.responseStatus?.errorCode == Constants.ERROR_MESSAGE_USER_NEW_DEVICE_EXCEPTION) {
//                                    if (message.startsWith("{")) { // admin otp
//                                        val statusMessage =
//                                            gsonParser.fromJson<KvResponseStatusMessage>(message)
//                                        KvException(
//                                            ExceptionEnum.UNTRUSTED_DEVICE.value,
//                                            statusMessage.otpPhoneNumber,
//                                            cause
//                                        )
//                                    } else { // nv otp
//                                        val statusMessage = gsonParser.fromJson<OTPResponse>(
//                                            errorBody ?: ""
//                                        ).responseStatus.message
//                                        KvException(
//                                            ExceptionEnum.UNTRUSTED_DEVICE.value,
//                                            statusMessage,
//                                            cause
//                                        )
//                                    }
//                                } else {
//                                    KvException(ExceptionEnum.OTP_ERROR.value, message, cause)
//                                }
//                        }
//                        else -> {
//                            if (message != null) {
//                                when (cause.getRelativePath()) {
//                                    "webhook_management/list" -> {
//                                        exception = KvException(
//                                            if (cause.code() == 400)
//                                                ExceptionEnum.SERVER_UNAVAILABLE.value
//                                            else ExceptionEnum.GENERAL_KV.value,
//                                            cause
//                                        )
//                                    }
//                                    "currentsession", "customers" -> exception =
//                                        KvException(ExceptionEnum.GENERAL_KV.value, message, cause)
//                                    "userdevice/verifyotp" -> exception =
//                                        KvException(ExceptionEnum.INVALID_OTP.value, cause)
//                                }
//                            }
//                            exception =
//                                if (exception == null && message == "Your session is over. Please sign in again." && errorResponse.responseStatus?.errorCode == "Unauthorized") {
//                                    KvException(ExceptionEnum.SESSION_EXPIRE_KV.value, cause)
//                                } else {
//                                    KvException(ExceptionEnum.GENERAL_KV.value, message, cause)
//                                }
//                        }
//                    }
//                } else if (errorBody != null) {
//                    exception = if (errorBody.contains("503")) {
//                        KvException(ExceptionEnum.SERVER_UNAVAILABLE.value, cause)
//                    } else
//                        KvException(ExceptionEnum.GENERAL_KV.value, errorBody, cause)
//                }
//            }
//        }else

        if (cause is NoConnectionInterceptor.NoConnectivityException) {
            exception = KvException(ExceptionEnum.NO_INTERNET.value, cause)
        }
        return exception
    }

}

