package com.hust.attandance.interact

import com.hust.attandance.data.model.ScheduleResponse
import com.hust.attandance.data.repo.AttandanceRepo

class CreateScheduleInteract(private val attandanceRepo: AttandanceRepo) :
    Interact<CreateScheduleInteract.Param, Unit>() {
    data class Param(val student: ScheduleResponse) : Interact.Param()

    override suspend fun execute(param: Param) {
        return attandanceRepo.insertSchedule(param.student)
    }
}