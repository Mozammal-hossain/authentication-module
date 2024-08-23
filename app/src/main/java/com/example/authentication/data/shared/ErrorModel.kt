package com.example.authentication.data.shared

import com.example.authentication.data.login.ErrorMessage

data class ErrorModel(
    val errorMessages: List<ErrorMessage>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)