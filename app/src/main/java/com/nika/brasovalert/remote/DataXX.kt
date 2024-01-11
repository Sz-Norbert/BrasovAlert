package com.nika.brasovalert.remote

data class DataXX(
    val authorId: Int,
    val createdAt: String,
    val description: String,
    val id: String,
    val images: List<Any>,
    val localitate: String,
    val name: String,
    val resolved: Boolean,
    val resolvedById: Int,
    val updatedAt: String
)