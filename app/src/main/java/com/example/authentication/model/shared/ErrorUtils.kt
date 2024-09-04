package com.example.authentication.model.shared

import com.example.authentication.model.data.shared.ErrorModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

object ErrorUtils {
    suspend fun parseError(exception: HttpException): ErrorModel {
        return withContext(Dispatchers.IO) {
            val errorBody = exception.response()?.errorBody()?.string()
            val gson = Gson()
            gson.fromJson(errorBody, ErrorModel::class.java)
        }
    }
}