package com.hust.attandance.data.model

data class ScheduleResponse(
    val id: String,
    val time: String,
    val classes: ClassResponse,
)
