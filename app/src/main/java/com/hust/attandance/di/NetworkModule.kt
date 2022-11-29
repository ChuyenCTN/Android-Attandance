package com.hust.attandance.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.hust.attandance.BuildConfig
import com.hust.attandance.data.api.ApiSource
import com.hust.attandance.data.api.AttandanceApi
import com.hust.attandance.data.interceptors.AttandanceInterceptor
import com.hust.attandance.data.interceptors.NoConnectionInterceptor
import com.hust.attandance.utils.helpers.FlipperInitializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@JvmField
val networkModule: Module = module {

    single { AttandanceInterceptor() }
    single { NoConnectionInterceptor(get()) }

    single<Converter.Factory> { GsonConverterFactory.create(get()) }

    single {
        val builder = OkHttpClient.Builder()
            .addInterceptor(get() as NoConnectionInterceptor)
            .addInterceptor(get() as AttandanceInterceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            Timber.d(" flipper setup")
            builder.addInterceptor(get() as ChuckerInterceptor)
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            Timber.d(" flipper = ${FlipperInitializer.setupInterceptor()}")
            FlipperInitializer.setupInterceptor()?.let { flipperInterceptor ->
                Timber.d(" setup flipper: $flipperInterceptor")
                builder.addInterceptor(flipperInterceptor)
            }
        }
        builder.build()
    }

    single {
        val chuckerCollector = ChuckerCollector(
            context = get(),
            // Toggles visibility of the notification
            showNotification = true,
            // Allows to customize the retention period of collected data
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        ChuckerInterceptor.Builder(get())
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()
    }


    single<ApiSource> {
        object : ApiSource {
            override fun getRetrofit(): Retrofit {
                val builder = OkHttpClient.Builder()
                    .addInterceptor(get() as AttandanceInterceptor)
                    .addInterceptor(get() as NoConnectionInterceptor)
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                if (BuildConfig.DEBUG) {
                    Timber.d(" flipper setup")
                    builder.addInterceptor(get() as ChuckerInterceptor)
                    builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    Timber.d(" flipper = ${FlipperInitializer.setupInterceptor()}")
                    FlipperInitializer.setupInterceptor()?.let { flipperInterceptor ->
                        Timber.d(" setup flipper: $flipperInterceptor")
                        builder.addInterceptor(flipperInterceptor)
                    }
                }
                return Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_DOMAIN_URL)
                    .addConverterFactory(get())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(get())
                    .build()
            }
        }
    }

    single<AttandanceApi> {
        (get() as ApiSource).getRetrofit().create(AttandanceApi::class.java)
    }

}