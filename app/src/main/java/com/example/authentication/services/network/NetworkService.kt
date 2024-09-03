package com.example.authentication.services.network

import com.example.authentication.data.forgotPassword.ForgotPassReqModel
import com.example.authentication.data.forgotPassword.ForgotPassResModel
import com.example.authentication.data.login.LoginRequestModel
import com.example.authentication.data.login.LoginResponseModel
import com.example.authentication.data.otpValidation.OTPValidationReqModel
import com.example.authentication.data.otpValidation.OTPValidationResModel
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkService {
    @POST("login")
    suspend fun login(@Body request: LoginRequestModel): LoginResponseModel

    @POST("forget-password")
    suspend fun forgotPassword(@Body request: ForgotPassReqModel): ForgotPassResModel

    @POST("verifyOtp")
    suspend fun otpValidation(@Body request: OTPValidationReqModel): OTPValidationResModel
}