package com.hust.attandance.data.model

data class AttandanceRequest(
    val id: String,
    val classId: String,
    val studentIds: List<String>
)