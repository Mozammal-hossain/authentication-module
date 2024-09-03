package com.example.authentication.data.otpValidation

data class OTPValidationReqModel(
    val email: String,
    val otp: String
)