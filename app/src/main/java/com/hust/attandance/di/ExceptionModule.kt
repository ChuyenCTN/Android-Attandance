package com.hust.attandance.di

import com.hust.attandance.data.exceptions.ApiExceptionFactory
import com.hust.attandance.utils.exceptions.ExceptionHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@JvmField
val exceptionModule = module {
    single { ApiExceptionFactory(androidContext()) }

    single {
        val exceptionFactories = arrayListOf(
            get() as ApiExceptionFactory
        )
        ExceptionHandler(exceptionFactories)
    }
}