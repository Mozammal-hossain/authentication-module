package com.example.authentication.model

import com.example.authentication.model.data.login.LoginCredential
import com.example.authentication.model.data.login.LoginRequestModel
import com.example.authentication.model.data.login.LoginResponseModel
import com.example.authentication.model.data.shared.ErrorModel
import com.example.authentication.model.shared.ErrorUtils.parseError
import com.example.authentication.services.local.UserDao
import com.example.authentication.services.network.NetworkService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

sealed class LoginResult {
    data class Success(val response: LoginResponseModel) : LoginResult()
    data class Error(val error: ErrorModel) : LoginResult()
}

class LoginModel @Inject constructor(
    private val networkService: NetworkService,
    private val userDao: UserDao
) {

    private var _loginResponse: LoginResponseModel? = null;



    suspend fun isLoggedIn(): Boolean = withContext(Dispatchers.IO) {
        userDao.getAll()?.isNotEmpty() ?: false
    }


    suspend fun login(email: String, password: String): LoginResult {
         return  try {
                val response = networkService.login(
                    LoginRequestModel(
                        FCMToken = "Token1",
                        OS = "Android",
                        email = email,
                        model = "Model1",
                        password = password,
                    )
                )
                Timber.i( "response: $response");

             withContext(Dispatchers.IO) {
                 val loginCredential = LoginCredential(email = email, password = password)
                 userDao.insertUser(loginCredential)
             }

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
}