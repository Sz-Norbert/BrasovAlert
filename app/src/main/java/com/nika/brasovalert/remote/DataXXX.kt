package com.nika.brasovalert.remote

data class DataXXX(
    val author: Author,
    val authorId: Int,
    val createdAt: String,
    val description: String,
    val id: String,
    val images: List<String>,
    val localitate: String,
    val name: String,
    val resolved: Boolean,
    val resolvedById: Int,
    val updatedAt: String
)