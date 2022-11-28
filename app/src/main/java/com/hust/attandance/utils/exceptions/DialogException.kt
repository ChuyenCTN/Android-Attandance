package com.hust.attandance.utils.exceptions

data class DialogException(
    override val code: Int,
    var title: String,
    override var message: String,
    override val cause: Throwable? = null,
) : KvException(code, message, cause)