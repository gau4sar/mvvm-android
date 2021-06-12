package com.gaurav.fretello.di

import android.content.Context
import com.gaurav.fretello.data.remote.SessionRemoteDataSource
import com.gaurav.fretello.data.remote.SessionsService
import com.gaurav.fretello.data.repository.SessionRepository
import com.gaurav.fretello.utils.Constants
import com.gaurav.fretello.utils.PreferenceUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

val networkModule = module {

    single(named(Constants.DEFAULT_SCOPE)) { provideDefaultOkHttpClient(androidContext(), get()) }
    single(named(Constants.DEFAULT_SCOPE)) { provideRetrofit(get((named(Constants.DEFAULT_SCOPE))), get()) }
    single { provideGson() }

    single { provideSessionsService(get(named(Constants.DEFAULT_SCOPE))) }
    single { SessionRemoteDataSource(get()) }
}

fun provideDefaultOkHttpClient(context: Context, preferenceUtils: PreferenceUtils): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.i(message) }
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val cacheFile = File(context.cacheDir, "okhttp.cache")
    val cache = Cache(cacheFile, (10 * 1000 * 1000).toLong()) // 10 MB

    return OkHttpClient.Builder()
        .connectTimeout(45, TimeUnit.SECONDS)
        .readTimeout(45, TimeUnit.SECONDS)
        .writeTimeout(45, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }
        .addInterceptor(loggingInterceptor)
        .cache(cache)
        .build()
}

fun provideGson(): Gson {
    return GsonBuilder().create()
}

fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
}

fun provideSessionsService(retrofit: Retrofit): SessionsService =
    retrofit.create(SessionsService::class.java)