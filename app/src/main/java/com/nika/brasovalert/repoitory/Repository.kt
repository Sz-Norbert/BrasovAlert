package com.nika.brasovalert.repoitory

import androidx.lifecycle.LiveData
import com.nika.brasovalert.api.AlertsApi
import com.nika.brasovalert.db.UserDao
import com.nika.brasovalert.db.UserEntity
import com.nika.brasovalert.remote.AuthBody
import com.nika.brasovalert.remote.AuthResponse
import com.nika.brasovalert.remote.CreateReportBody
import com.nika.brasovalert.remote.CreateReportResponse
import com.nika.brasovalert.remote.RegisterBody
import com.nika.brasovalert.remote.RegisterResponse
import com.nika.brasovalert.remote.ReportResponse
import okhttp3.MultipartBody

import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(private val alertApi: AlertsApi, private val userDao: UserDao) {

    suspend fun registerUser(user: RegisterBody): Resource<RegisterResponse> {
        return safeCall { alertApi.register(user) }
    }

    suspend fun createReport(createReportbody: CreateReportBody): Resource<CreateReportResponse> {
        val nameRequestBody = createPartFromString("name", createReportbody.name)
        val descriptionRequestBody = createPartFromString("description", createReportbody.description)
        val localitateRequestBody = createPartFromString("localitate", createReportbody.localitate)

        return safeCall {
            alertApi.postReport(
                createReportbody.token,
                nameRequestBody,
                descriptionRequestBody,
                localitateRequestBody
            )
        }
    }

    private fun createPartFromString(name: String, value: String): RequestBody {
        return RequestBody.create(MultipartBody.FORM, value)
    }


    suspend fun insertUser(user:UserEntity){
        userDao.upsertUser(user)
    }

    fun getUser(): LiveData<UserEntity>{
        return userDao.getUser()
    }

    suspend fun authUser(user : AuthBody):Resource<AuthResponse>{
        return safeCall { alertApi.authUser(user)}
    }

    suspend fun getReports(token:String):Resource<ReportResponse>{
       return safeCall { alertApi.getReports(token) } }
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




