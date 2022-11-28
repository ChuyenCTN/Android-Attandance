package com.hust.attandance.ui.common

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseActivity(layoutRes: Int) : AppCompatActivity(layoutRes) {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

}