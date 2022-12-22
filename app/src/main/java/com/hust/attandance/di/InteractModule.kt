package com.hust.attandance.di

import com.hust.attandance.interact.*
import org.koin.dsl.module

@JvmField
val interactModule = module {

    single { LoginInteract(get()) }
    single { ProfileInteract(get()) }
    single { CheckCurrentUserInteract(get()) }
    single { LogoutInteract(get()) }
    single { GetClassInteract(get()) }
    single { CreateClassInteract(get()) }

}