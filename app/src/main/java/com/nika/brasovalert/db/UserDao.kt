package com.nika.brasovalert.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface  UserDao {

    @Upsert
    suspend fun upsertUser(user: UserEntity)

    @Query("SELECT *  FROM user")
    fun getUser():LiveData<UserEntity>

}