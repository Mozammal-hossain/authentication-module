package com.example.authentication.model.data.remote.otpValidation

data class OTPValidationReqModel(
    val email: String,
    val otp: String
)