package com.nika.brasovalert.remote

data class RegisterBody(
    val confirmPassword: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String
)