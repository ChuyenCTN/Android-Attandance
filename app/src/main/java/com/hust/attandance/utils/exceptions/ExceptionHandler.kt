package com.hust.attandance.utils.exceptions

import com.hust.attandance.BuildConfig
import com.hust.attandance.data.exceptions.ApiExceptionFactory
import com.hust.attandance.data.exceptions.ExceptionEnum
import com.hust.attandance.utils.exceptions.ExceptionFactory
import com.hust.attandance.utils.exceptions.KvException
import com.hust.attandance.utils.exceptions.ToastException
import retrofit2.HttpException

class ExceptionHandler(private val exceptionFactories: ArrayList<ApiExceptionFactory>) {

    fun process(cause: Throwable): KvException {
        cause.printStackTrace()
        if (cause is KvException) {
            return cause
        } else {
            var error: KvException? = null
            try {
                for (filter in exceptionFactories) {
                    error = filter.buildError(cause)
                    if (error != null) break
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    var errorCode = Int.MIN_VALUE
                    if (cause is HttpException)
                        errorCode = cause.code()
                    error = ToastException(errorCode, e.message ?: "Chưa xác định", e)
                }
            }
            if (error == null) {
                error = KvException(ExceptionEnum.UNDEFINED.value, cause)
            }
            return error
        }
    }

}