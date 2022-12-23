package com.hust.attandance.data.model

data class ScheduleResponse(
    val id: String,
    val time: String,
    val date: String,
    val number: Int,
    val total: Int,
    val note: String,
    val session: String,
)
