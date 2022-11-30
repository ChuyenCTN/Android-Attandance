package com.hust.attandance.data.model

data class ClassResponse(
    val id: String,
    val name: String?,
    val description: String,
    val mainTeacher: String,
    val studentIds: List<String>
)