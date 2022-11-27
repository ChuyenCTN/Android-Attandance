package com.hust.attandance.di

import com.hust.attandance.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

@JvmField
val viewModelModule: Module = module {
    viewModel {
        HomeViewModel()
    }
}