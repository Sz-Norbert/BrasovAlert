package com.nika.brasovalert.remote

data class CreateReportBody(
    val token  : String,
    val name : String,
    val localitate: String,
    val description:String
)
