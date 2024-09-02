package com.example.authentication.services.network

import com.example.authentication.data.forgotPassword.ForgotPassReqModel
import com.example.authentication.data.forgotPassword.ForgotPassResModel
import com.example.authentication.data.login.LoginRequestModel
import com.example.authentication.data.login.LoginResponseModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequestModel): LoginResponseModel

    @POST("forget-password")
    suspend fun forgotPassword(@Body request: ForgotPassReqModel): ForgotPassResModel
}