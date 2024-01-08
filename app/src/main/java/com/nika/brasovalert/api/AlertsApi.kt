package com.nika.brasovalert.api

import com.nika.brasovalert.remote.AuthBody
import com.nika.brasovalert.remote.AuthResponse
import com.nika.brasovalert.remote.RegisterBody
import com.nika.brasovalert.remote.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AlertsApi {





    @POST("auth/login")
    suspend fun authUser(@Body user: AuthBody):Response<AuthResponse>



    @POST("auth/register")
    suspend fun register(@Body registerBody : RegisterBody):Response<RegisterResponse>

}