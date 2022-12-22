package com.hust.attandance.di

import com.hust.attandance.ui.attandance.FaceAttandanceViewModel
import com.hust.attandance.ui.classes.DetailClassesViewModel
import com.hust.attandance.ui.home.HomeViewModel
import com.hust.attandance.ui.login.LoginViewModel
import com.hust.attandance.ui.main.MainViewModel
import com.hust.attandance.ui.profile.ProfileViewModel
import com.hust.attandance.ui.splash.SplashViewModel
import com.hust.attandance.ui.student.AddStudentViewModel
import com.hust.attandance.ui.welcome.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

@JvmField
val viewModelModule: Module = module {
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        WelcomeViewModel(get(), get())
    }
    viewModel {
        LoginViewModel(get(), get())
    }
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        ProfileViewModel(get(), get(), get())
    }
    viewModel {
        SplashViewModel(get(), get())
    }
    viewModel {
        DetailClassesViewModel(get(), get())
    }
    viewModel {
        FaceAttandanceViewModel(get())
    }
    viewModel {
        AddStudentViewModel(get(), get())
    }
}