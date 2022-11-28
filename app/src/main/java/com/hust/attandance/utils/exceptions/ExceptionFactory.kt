package com.hust.attandance.utils.exceptions


interface ExceptionFactory {
    fun buildError(cause: Throwable): KvException?
}