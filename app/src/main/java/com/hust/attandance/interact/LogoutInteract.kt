package com.hust.attandance.interact

import com.hust.attandance.data.repo.LoginRepo

class LogoutInteract(private val loginRepo: LoginRepo) :
    Interact<Interact.Param, Any>() {

    override suspend fun execute(param: Param) {
        return loginRepo.logout()
    }
}