package com.example.authentication.model

import com.example.authentication.model.data.remote.resend.ResendRequestDataModel
import com.example.authentication.model.data.remote.resend.ResendResponseDataModel
import com.example.authentication.model.data.shared.ErrorModel
import com.example.authentication.model.shared.ErrorUtils.parseError
import com.example.authentication.services.network.NetworkService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed class ResendOTPResult {
    data class Success(val response: ResendResponseDataModel) : ResendOTPResult()
    data class Error(val error: ErrorModel) : ResendOTPResult()
}


class ResendOTPModel @Inject constructor(
    private val apiService: NetworkService
) {
    suspend fun resendOTP(email: String): ResendOTPResult {
        return try {
            val requestBody = ResendRequestDataModel(email)

            val response = apiService.resendOTP(requestBody)

            ResendOTPResult.Success(response)
        } catch (e: HttpException) {
            val errorModel = parseError(e)
            ResendOTPResult.Error(errorModel)
        } catch (e: IOException) {
            val errorModel = ErrorModel(
                errorMessages = listOf(),
                message = "Network error: ${e.message}",
                statusCode = 0,
                success = false
            )
            ResendOTPResult.Error(errorModel)
        }
    }
}