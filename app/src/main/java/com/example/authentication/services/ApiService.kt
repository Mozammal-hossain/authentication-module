package com.example.authentication.services

import com.example.authentication.model.LoginRequestModel
import com.example.authentication.model.LoginResponseModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequestModel): LoginResponseModel
}