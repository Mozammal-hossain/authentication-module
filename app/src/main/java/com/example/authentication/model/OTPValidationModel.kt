package com.example.authentication.model

import com.example.authentication.model.data.remote.otpValidation.OTPValidationReqModel
import com.example.authentication.model.data.remote.otpValidation.OTPValidationResModel
import com.example.authentication.model.data.shared.ErrorModel
import com.example.authentication.model.shared.ErrorUtils.parseError
import com.example.authentication.services.network.NetworkService
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

sealed class OTPValidationResult {
    data class Success(val response: OTPValidationResModel) : OTPValidationResult()
    data class Error(val error: ErrorModel) : OTPValidationResult()
}

class OTPValidationModel @Inject constructor(
    private val apiService: NetworkService
) {


    suspend fun validateOTP(email: String, otp: String): OTPValidationResult {
        return try {
            Timber.i("Model class email is $email and otp is $otp")
            val response = apiService.otpValidation(
                OTPValidationReqModel(
                    email = email,
                    otp = otp
                )
            )
            OTPValidationResult.Success(response)
        } catch (e: HttpException) {
            val errorModel = parseError(e)
            OTPValidationResult.Error(errorModel)
        } catch (e: IOException) {
            val errorModel = ErrorModel(
                errorMessages = listOf(),
                message = "Network error: ${e.message}",
                statusCode = 0,
                success = false
            )
            OTPValidationResult.Error(errorModel)
        }
    }
}