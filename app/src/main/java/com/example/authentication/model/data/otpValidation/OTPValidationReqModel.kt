package com.example.authentication.model.data.otpValidation

data class OTPValidationReqModel(
    val email: String,
    val otp: String
)