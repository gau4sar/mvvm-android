package com.gaurav.fretello.di

import android.app.Application
import com.gaurav.fretello.data.local.FretelloRoomDb
import com.gaurav.fretello.data.repository.SessionRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule by lazy {
    module {
        single { provideRoomDatabase(androidApplication()) }
        single { provideSessionsDao(get()) }
        single { provideExercisesDao(get()) }

        single { SessionRepository(get(), get(), get()) }
    }
}

fun provideRoomDatabase(application: Application): FretelloRoomDb {
    return FretelloRoomDb.invoke(application.applicationContext)
}

fun provideSessionsDao(fretelloDb: FretelloRoomDb) = fretelloDb.getSessionsDao()

fun provideExercisesDao(fretelloDb: FretelloRoomDb) = fretelloDb.getExercisesDao()