package com.example.authentication.model.data.setNewPass

data class SetNewPassReqModel(
    val email: String,
    val password: String,
    val confirmPassword: String
)