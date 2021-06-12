package com.gaurav.fretello.di

import com.gaurav.fretello.ui.sessions.SessionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule by lazy {
    module {
        viewModel { SessionsViewModel(get()) }
    }
}