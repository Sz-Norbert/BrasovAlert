package com.nika.brasovalert.repoitory

import com.nika.brasovalert.api.AlertsApi
import com.nika.brasovalert.db.UserDao
import com.nika.brasovalert.db.UserEntity
import com.nika.brasovalert.remote.AuthBody
import com.nika.brasovalert.remote.AuthResponse
import com.nika.brasovalert.remote.Data
import com.nika.brasovalert.remote.RegisterBody
import com.nika.brasovalert.remote.RegisterResponse

import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class Repositroy @Inject constructor(private val alertApi: AlertsApi, private val userDao: UserDao) {

    suspend fun registerUser(user: RegisterBody): Resource<RegisterResponse> {
        return safeCall { alertApi.register(user) }
    }

    suspend fun insertUser(user:UserEntity){
        userDao.upsertUser(user)
    }

    suspend fun authUser(user : AuthBody):Resource<AuthResponse>{
        return safeCall { alertApi.authUser(user)}
    }

    suspend fun <T> safeCall(apiCall: suspend () -> Response<T>): Resource<T> {
        try {
            val response = apiCall.invoke()

            if (response.isSuccessful) {
                response.body()?.let {
                    return Resource.Success(it)
                } ?: return Resource.Error("Response body is null")
            } else {
                val errorBody = response.errorBody()?.string()
                if (response.code() == 500 && errorBody?.contains("Email is already registered") == true) {
                    return Resource.Error("Email is already registered",)
                }
                return Resource.Error(errorBody ?: "Unknown error")
            }
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Couldn't reach the server")
        }
    }
}



