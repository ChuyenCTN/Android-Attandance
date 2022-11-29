package com.hust.attandance.data.repo

import com.hust.attandance.data.api.AttandanceApi
import com.hust.attandance.data.model.LoginRequest
import com.hust.attandance.data.response.LoginResonse
import net.citigo.kiotviet.pos.fb.data.cache.Cache

class LoginRepo(private val attandanceApi: AttandanceApi, private val cache: Cache) {

    suspend fun login(request: LoginRequest): LoginResonse? {
        val resonse = attandanceApi.login(request)
        resonse?.let { cache.saveLoginResponse(it) }
        return resonse
    }

    fun getProfileInfo(): LoginResonse? {
        return cache.getProfileConfig()
    }

    fun logout() {
        cache.clearCache()
    }

}