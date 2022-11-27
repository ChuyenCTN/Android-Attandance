package com.hust.attandance.data.api

import retrofit2.Retrofit

interface ApiSource {
    fun getRetrofit(): Retrofit

}