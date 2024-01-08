package com.nika.brasovalert.db

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface  UserDao {

    @Upsert
    suspend fun upsertUser(user: UserEntity)

}