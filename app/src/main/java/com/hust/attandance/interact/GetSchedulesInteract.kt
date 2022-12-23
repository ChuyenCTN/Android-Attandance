package com.hust.attandance.interact

import com.hust.attandance.data.model.ScheduleResponse
import com.hust.attandance.data.repo.AttandanceRepo

class GetSchedulesInteract(private val attandanceRepo: AttandanceRepo) :
    Interact<Interact.Param, List<ScheduleResponse>?>() {

    override suspend fun execute(param: Param): List<ScheduleResponse>? {
        return attandanceRepo.getScheduleList()
    }
}