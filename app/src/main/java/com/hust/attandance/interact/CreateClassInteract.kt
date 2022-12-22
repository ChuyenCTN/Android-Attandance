package com.hust.attandance.interact

import com.hust.attandance.data.repo.LoginRepo
import com.hust.attandance.data.response.LoginResonse

class CreateClassInteract(private val loginRepo: LoginRepo) :
    Interact<CreateClassInteract.Param, Unit>() {
    data class Param(val datas: List<LoginResonse>) : Interact.Param()

    override suspend fun execute(param: Param) {
        return loginRepo.saveListData(param.datas)
    }
}