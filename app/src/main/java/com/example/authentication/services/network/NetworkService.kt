package com.example.authentication.services.network

import com.example.authentication.model.data.remote.forgotPassword.ForgotPassReqModel
import com.example.authentication.model.data.remote.forgotPassword.ForgotPassResModel
import com.example.authentication.model.data.remote.login.LoginRequestModel
import com.example.authentication.model.data.remote.login.LoginResponseModel
import com.example.authentication.model.data.remote.logout.LogOutResModel
import com.example.authentication.model.data.remote.otpValidation.OTPValidationReqModel
import com.example.authentication.model.data.remote.otpValidation.OTPValidationResModel
import com.example.authentication.model.data.remote.profile.ProfileResModel
import com.example.authentication.model.data.remote.setNewPass.SetNewPassReqModel
import com.example.authentication.model.data.remote.setNewPass.SetNewPassResModel
import com.example.authentication.model.data.remote.signUp.SignUpRequestModel
import com.example.authentication.model.data.remote.signUp.SignUpResponseModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NetworkService {
    @POST("signUp")
    suspend fun signUp(@Body request: SignUpRequestModel): SignUpResponseModel

    @POST("login")
    suspend fun login(@Body request: LoginRequestModel): LoginResponseModel

    @POST("forget-password")
    suspend fun forgotPassword(@Body request: ForgotPassReqModel): ForgotPassResModel

    @POST("verifyOtp")
    suspend fun otpValidation(@Body request: OTPValidationReqModel): OTPValidationResModel

    @POST("set-new-password")
    suspend fun setNewPass(@Body request: SetNewPassReqModel): SetNewPassResModel

    @POST("logout")
    suspend fun logout(@Header("Authorization") token: String): LogOutResModel

    @GET("me")
    suspend fun getProfile(@Header("Authorization") token: String): ProfileResModel

}