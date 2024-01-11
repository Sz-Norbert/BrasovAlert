package com.nika.brasovalert.api

import com.nika.brasovalert.remote.AuthBody
import com.nika.brasovalert.remote.AuthResponse
import com.nika.brasovalert.remote.CreateReportResponse
import com.nika.brasovalert.remote.RegisterBody
import com.nika.brasovalert.remote.RegisterResponse
import com.nika.brasovalert.remote.ReportResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AlertsApi {





    @POST("auth/login")
    suspend fun authUser(@Body user: AuthBody):Response<AuthResponse>


    @GET("reports")
    suspend fun getReports(
        @Header("Authorization") token: String
    ):Response<ReportResponse>

    @POST("auth/register")
    suspend fun register(@Body registerBody : RegisterBody):Response<RegisterResponse>


    @Multipart
    @POST("reports/new")
    suspend fun postReport(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("localitate") localitate: RequestBody
    ): Response<CreateReportResponse>



}