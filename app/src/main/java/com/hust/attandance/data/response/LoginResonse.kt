package com.hust.attandance.data.response


import com.google.gson.annotations.SerializedName

data class LoginResonse(
    @SerializedName("address")
    val address: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("faceId")
    val faceId: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("studentId")
    val studentId: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("userName")
    val userName: String
)