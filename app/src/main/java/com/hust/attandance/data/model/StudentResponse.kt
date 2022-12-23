package com.hust.attandance.data.model

data class StudentResponse(
    val code: String,
    val name: String,
    val faceId: String,
    val phone: String,
    val address: String,
    val birthDay: String,
    val image: String,
    val email: String,
    var checked: Boolean = false // chi dung de diem danh
)