package com.gaurav.fretello

import android.app.Application
import com.gaurav.fretello.di.appModule
import com.gaurav.fretello.di.dataModule
import com.gaurav.fretello.di.networkModule
import com.gaurav.fretello.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class FretelloApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Enable logging for only debug hence we don't use the default Logger class anywhere
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())


        // start Koin!
        startKoin {
            // Use Koin Android Logger
            androidLogger(Level.NONE)
            // declare used Android context
            androidContext(this@FretelloApplication)
            // declare modules
            modules(
                listOf(
                    appModule,
                    networkModule,
                    dataModule,
                    viewModelModule
                )
            )
        }
    }
}