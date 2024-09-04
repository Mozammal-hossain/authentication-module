package com.example.authentication.model.data

data class SetNewPassReqModel(
    val confirmPassword: String,
    val email: String,
    val password: String
)