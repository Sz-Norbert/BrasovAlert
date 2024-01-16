package com.nika.brasovalert.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id :Int,
    val  token :String,
    val firstName : String,
    val lastName : String,
    val email : String


)