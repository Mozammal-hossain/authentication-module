package com.example.authentication.model

import com.example.authentication.model.data.local.login.LoginCredential
import com.example.authentication.model.data.remote.profile.ProfileResModel
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

sealed class ProfileModelResult {
    data class Success(val response: ProfileResModel) : ProfileModelResult()
    data class Error(val error: ErrorModel) : ProfileModelResult()
}

class ProfileModel @Inject constructor(
    private val networkService: NetworkService,
    private val userDao: UserDao
) {
    suspend fun getProfile(): ProfileModelResult {
        return withContext(Dispatchers.IO) {
            try {
                val userToken = userDao.getAll()?.get(0)?.token;

                Timber.i("userToken: $userToken");
                val bearerToken = "Bearer $userToken"

                val response = networkService.getProfile(bearerToken)

                Timber.i("response: $response");

                userDao.updateProfilePicById(
                    id = response.data._id,
                    newProfilePic = response.data.avatar
                )

                ProfileModelResult.Success(response)

            } catch (e: HttpException) {
                ProfileModelResult.Error(parseError(e))
            } catch (e: IOException) {
                Timber.e(e)
                ProfileModelResult.Error( ErrorModel(
                    errorMessages = listOf(),
                    message = "Unexpected error: ${e.message}",
                    statusCode = 0,
                    success = false
                ))
            }
        }
    }
}