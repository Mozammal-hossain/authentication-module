package com.example.authentication.model.data.shared

import com.example.authentication.model.data.remote.login.ErrorMessage

data class ErrorModel(
    val errorMessages: List<ErrorMessage>,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)