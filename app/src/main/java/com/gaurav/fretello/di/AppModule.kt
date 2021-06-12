package com.gaurav.fretello.di

import com.gaurav.fretello.utils.PreferenceUtils
import org.koin.dsl.module

val appModule = module {
    single { PreferenceUtils(get()) }
}
