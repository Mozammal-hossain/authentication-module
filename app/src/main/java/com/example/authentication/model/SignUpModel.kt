package com.example.authentication.model

import com.example.authentication.model.data.remote.signUp.SignUpRequestModel
import com.example.authentication.model.data.remote.signUp.SignUpResponseModel
import com.example.authentication.model.data.shared.ErrorModel
import com.example.authentication.model.shared.ErrorUtils.parseError
import com.example.authentication.services.network.NetworkService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed class SignUpResult {
    data class Success(val response: SignUpResponseModel) : SignUpResult()
    data class Error(val error: ErrorModel) : SignUpResult()
}

class SignUpModel @Inject constructor(
    private val networkService: NetworkService
) {
    suspend fun signUp(signUpRequestModel: SignUpRequestModel): SignUpResult {
        try {
            val response = networkService.signUp(signUpRequestModel)

            return SignUpResult.Success(response)

        } catch (e: HttpException) {
            val error = parseError(e)

            return SignUpResult.Error(error)
        } catch (e: IOException) {
            val errorModel = ErrorModel(
                errorMessages = listOf(),
                message = "Network error: ${e.message}",
                statusCode = 0,
                success = false
            )

            return SignUpResult.Error(errorModel)
        }
    }

}