package com.hust.attandance.di

import com.hust.attandance.interact.CheckCurrentUserInteract
import com.hust.attandance.interact.LoginInteract
import com.hust.attandance.interact.LogoutInteract
import com.hust.attandance.interact.ProfileInteract
import org.koin.dsl.module

@JvmField
val interactModule = module {

    single { LoginInteract(get()) }
    single { ProfileInteract(get()) }
    single { CheckCurrentUserInteract(get()) }
    single { LogoutInteract(get()) }

}