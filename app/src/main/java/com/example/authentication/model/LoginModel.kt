package com.example.authentication.model

import com.example.authentication.data.login.LoginRequestModel
import com.example.authentication.data.login.LoginResponseModel
import NetworkModule
import com.example.authentication.data.shared.ErrorModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

sealed class LoginResult {
    data class Success(val response: LoginResponseModel) : LoginResult()
    data class Error(val errorModel: ErrorModel) : LoginResult()
}

class LoginModel {
    private  val apiService = NetworkModule.api

    private var _loginResponse: LoginResponseModel? = null;

    suspend fun login(email: String, password: String): LoginResult {
         return  try {
                val response = apiService.login(
                    LoginRequestModel(
                        FCMToken = "Token1",
                        OS = "Android",
                        email = email,
                        model = "Model1",
                        password = password,
                    )
                )
                Timber.i( "response: $response");
                LoginResult.Success(response)
            } catch (e: HttpException) {
                // Handle HTTP errors
                Timber.e("HTTP error: ${e.message}")
             val errorModel = parseError(e)
             LoginResult.Error(errorModel)
            } catch (e: IOException) {
                // Handle network errors
                Timber.e("Network error: ${e.message}")
             LoginResult.Error(
                 ErrorModel(
                     errorMessages = listOf(),
                     message = "Network error: ${e.message}",
                     statusCode = 0,
                     success = false
                 )
             )
            } catch (e: Exception) {
                // Handle unexpected errors
                Timber.e("Unexpected error: ${e.message}")
             LoginResult.Error(
                 ErrorModel(
                     errorMessages = listOf(),
                     message = "Unexpected error: ${e.message}",
                     statusCode = 0,
                     success = false
                 )
             )
            }
    }

    private suspend fun parseError(exception: HttpException): ErrorModel {
        return withContext(Dispatchers.IO) {
            val errorBody = exception.response()?.errorBody()?.string()
            val gson = Gson()
            gson.fromJson(errorBody, ErrorModel::class.java)
        }
    }
}