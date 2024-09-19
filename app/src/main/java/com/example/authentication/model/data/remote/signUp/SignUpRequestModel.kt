package com.example.authentication.model.data.remote.signUp

data class SignUpRequestModel(
    val email: String,
    val firstname: String,
    val lastname: String,
    val password: String
)