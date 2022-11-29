package com.hust.attandance.interact

import com.hust.attandance.data.repo.LoginRepo
import com.hust.attandance.data.response.LoginResonse

class CheckCurrentUserInteract(private val loginRepo: LoginRepo) :
    Interact<Interact.Param, LoginResonse?>() {

    override suspend fun execute(param: Param): LoginResonse? {
        return loginRepo.getProfileInfo()
    }
}