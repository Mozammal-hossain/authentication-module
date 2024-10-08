package com.example.authentication.model

import com.example.authentication.model.data.local.login.LoginCredential
import com.example.authentication.model.data.remote.login.LoginRequestModel
import com.example.authentication.model.data.remote.login.LoginResponseModel
import com.example.authentication.model.data.shared.ErrorModel
import com.example.authentication.model.shared.ErrorUtils.parseError
import com.example.authentication.services.local.UserDao
import com.example.authentication.services.network.NetworkService
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
    suspend fun isLoggedIn(): Boolean = withContext(Dispatchers.IO) {
        userDao.getAll()?.isNotEmpty() ?: false
    }

    suspend fun login(email: String, password: String, isRememberMe: Boolean): LoginResult {
        return try {
            val response = networkService.login(
                LoginRequestModel(
                    FCMToken = "Token1",
                    OS = "Android",
                    email = email,
                    model = "Model1",
                    password = password,
                )
            )
            Timber.i("response: $response")

            if (isRememberMe) {
                val loggedInUser = response.user

                withContext(Dispatchers.IO) {
                    val loginCredential = LoginCredential(
                        email = email,
                        password = password,
                        id = loggedInUser._id,
                        token = response.token,
                        name = loggedInUser.firstname + " " + loggedInUser.lastname,
                        profilePic = null,
                    )

                    userDao.upsertUser(loginCredential)
                }
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