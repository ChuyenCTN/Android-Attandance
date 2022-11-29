package com.hust.attandance.data.api

import com.hust.attandance.data.model.LoginRequest
import com.hust.attandance.data.response.LoginResonse
import retrofit2.http.Body
import retrofit2.http.POST

interface AttandanceApi {

    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResonse?
}