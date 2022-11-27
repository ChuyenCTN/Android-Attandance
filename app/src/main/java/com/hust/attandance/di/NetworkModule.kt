package com.hust.attandance.di

import com.hust.attandance.BuildConfig
import com.hust.attandance.data.api.ApiSource
import com.hust.attandance.data.api.AttandanceApi
import com.hust.attandance.data.interceptors.AttandanceInterceptor
import com.hust.attandance.data.interceptors.NoConnectionInterceptor
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

@JvmField
val networkModule: Module = module {

    single { AttandanceInterceptor() }
    single { NoConnectionInterceptor(get()) }



    single<Converter.Factory> { GsonConverterFactory.create(get()) }

    single {
        val builder = OkHttpClient.Builder()
//            .addInterceptor(get() as HostSelectionInterceptor)
//            .addInterceptor(get() as AuthInterceptor)
            .addInterceptor(get() as AttandanceInterceptor)
            .addInterceptor(get() as NoConnectionInterceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .followSslRedirects(true)
            .followRedirects(true)
        if (BuildConfig.DEBUG) {

        }
        builder.build()
    }

    single<ApiSource> {
        object : ApiSource {
            override fun getRetrofit(): Retrofit {
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