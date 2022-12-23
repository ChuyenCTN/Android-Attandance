package com.hust.attandance.data.repo

import com.hust.attandance.data.api.AttandanceApi
import com.hust.attandance.data.model.ClassResponse
import com.hust.attandance.data.model.ScheduleResponse
import com.hust.attandance.data.model.StudentResponse
import net.citigo.kiotviet.pos.fb.data.cache.Cache

class AttandanceRepo(private val attandanceApi: AttandanceApi, private val cache: Cache) {

    fun insertStudent(student: StudentResponse) {
        cache.insertStudent(student)
    }

    fun getStudentList(): List<StudentResponse>? {
        return cache.getStudentList()
    }

    fun insertSchedule(schedule: ScheduleResponse) {
        cache.insertSchedule(schedule)
    }

    fun getScheduleList(): List<ScheduleResponse>? {
        return cache.getScheduleList()
    }

    fun getClassesList(): List<ClassResponse>? {
        return cache.getClassesList()
    }

}