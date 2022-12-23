package com.hust.attandance.interact

import com.hust.attandance.data.model.StudentResponse
import com.hust.attandance.data.repo.AttandanceRepo

class GetStudentsInteract(private val attandanceRepo: AttandanceRepo) :
    Interact<Interact.Param, List<StudentResponse>?>() {

    override suspend fun execute(param: Param): List<StudentResponse>? {
        return attandanceRepo.getStudentList()
    }
}