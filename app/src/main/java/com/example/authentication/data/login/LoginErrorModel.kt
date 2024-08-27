package com.example.authentication.data.login

data class LoginErrorModel(
    val errorMessages: List<ErrorMessage>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)