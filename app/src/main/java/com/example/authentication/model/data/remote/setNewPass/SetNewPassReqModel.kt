package com.example.authentication.model.data.remote.setNewPass

data class SetNewPassReqModel(
    val email: String,
    val password: String,
    val confirmPassword: String
)