package com.example.authentication.model

import com.example.authentication.model.data.shared.ErrorModel
import com.example.authentication.model.shared.ErrorUtils.parseError
import com.example.authentication.services.local.UserDao
import com.example.authentication.services.network.NetworkService
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

sealed class LogoutResult {
    data class Success(val response: String) : LogoutResult()
    data class Error(val error: ErrorModel) : LogoutResult()
}

class LogoutModel @Inject constructor(
    private val userDao: UserDao,
    private val networkService: NetworkService
)
{

    suspend fun logout(): LogoutResult {
      return  try {

            Timber.i("Logout called");

            val userToken = userDao.getAll()?.get(0)?.token

            Timber.i("userToken: $userToken");

          val bearerToken = "Bearer $userToken"


          val response = networkService.logout(bearerToken)

            userDao.deleteAll()

             LogoutResult.Success(response.message);
        } catch (e: HttpException) {
            // Handle HTTP errors
            Timber.e("HTTP error: ${e.message}")
            val errorModel = parseError(e)
            LogoutResult.Error(errorModel)

        } catch (e: IOException) {
            // Handle network errors
            Timber.e("Network error: ${e.message}")
            LogoutResult.Error(
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
            LogoutResult.Error(
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