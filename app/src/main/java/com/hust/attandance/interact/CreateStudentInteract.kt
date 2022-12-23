package com.hust.attandance.interact

import com.hust.attandance.data.model.StudentResponse
import com.hust.attandance.data.repo.AttandanceRepo

class CreateStudentInteract(private val attandanceRepo: AttandanceRepo) :
    Interact<CreateStudentInteract.Param, Unit>() {
    data class Param(val student: StudentResponse) : Interact.Param()

    override suspend fun execute(param: Param) {
        return attandanceRepo.insertStudent(param.student)
    }
}