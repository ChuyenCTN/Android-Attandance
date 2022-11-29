package com.hust.attandance.di

import com.hust.attandance.data.repo.LoginRepo
import org.koin.dsl.module

@JvmField
val repoModule = module {

    single { LoginRepo(get(), get()) }

}