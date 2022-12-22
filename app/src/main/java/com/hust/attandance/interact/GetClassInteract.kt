package com.hust.attandance.interact

import com.hust.attandance.data.repo.LoginRepo
import com.hust.attandance.data.response.LoginResonse

class GetClassInteract(private val loginRepo: LoginRepo) :
    Interact<Interact.Param, List<LoginResonse>?>() {


    override suspend fun execute(param: Param): List<LoginResonse>? {
        return loginRepo.getListData()
    }
}