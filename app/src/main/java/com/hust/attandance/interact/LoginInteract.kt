package com.hust.attandance.interact

import com.hust.attandance.data.model.LoginRequest
import com.hust.attandance.data.repo.LoginRepo
import com.hust.attandance.data.response.LoginResonse

class LoginInteract(private val loginRepo: LoginRepo) :
    Interact<LoginInteract.Param, LoginResonse?>() {
    data class Param(val loginRequest: LoginRequest) : Interact.Param()

    override suspend fun execute(param: Param): LoginResonse? {
        return loginRepo.login(param.loginRequest)
    }
}