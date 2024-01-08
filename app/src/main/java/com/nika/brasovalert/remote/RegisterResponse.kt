package com.nika.brasovalert.remote

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("data")
    val userInfo: Data,
    val message: String
)