package com.hust.attandance.di

import com.hust.attandance.ui.home.HomeViewModel
import com.hust.attandance.ui.login.LoginViewModel
import com.hust.attandance.ui.main.MainViewModel
import com.hust.attandance.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

@JvmField
val viewModelModule: Module = module {
    viewModel {
        HomeViewModel()
    }
    viewModel {
        SplashViewModel(get())
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        MainViewModel(get())
    }
}