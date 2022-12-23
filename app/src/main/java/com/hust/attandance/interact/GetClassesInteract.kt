package com.hust.attandance.interact

import com.hust.attandance.data.model.ClassResponse
import com.hust.attandance.data.repo.AttandanceRepo

class GetClassesInteract(private val attandanceRepo: AttandanceRepo) :
    Interact<Interact.Param, List<ClassResponse>?>() {

    override suspend fun execute(param: Param): List<ClassResponse>? {
        return attandanceRepo.getClassesList()
    }
}