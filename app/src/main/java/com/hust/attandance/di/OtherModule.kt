package com.hust.attandance.di

import com.google.gson.Gson
import com.hust.attandance.utils.others.GsonParser
import org.koin.dsl.module

@JvmField
val otherModule = module {

    single<GsonParser> {
        GsonParser(get() as Gson)
    }

    single { Gson() }


}