package com.example.authentication.model

import com.example.authentication.data.forgotPassword.ForgotPassReqModel
import com.example.authentication.data.forgotPassword.ForgotPassResModel
import com.example.authentication.data.shared.ErrorModel
import com.example.authentication.model.shared.ErrorUtils.parseError
import retrofit2.HttpException
import java.io.IOException

sealed class ForgotPassResult {
    data class Success(val response: ForgotPassResModel) : ForgotPassResult()
    data class Error(val errorModel: ErrorModel) : ForgotPassResult()
}


class ForgotPassModel {
    private val apiService = NetworkModule.api
    suspend fun forgotPassword(email: String): ForgotPassResult {
        return try{
            val response = apiService.forgotPassword(ForgotPassReqModel(email))
           ForgotPassResult.Success(response)
        } catch (e: HttpException) {
            val errorModel = parseError(e)

            ForgotPassResult.Error(errorModel)

        } catch (e : IOException) {

            val errorModel = ErrorModel(
                errorMessages = listOf(),
                message = "Network error: ${e.message}",
                statusCode = 0,
                success = false,
            )

            ForgotPassResult.Error(errorModel);
        }
        catch (e: Exception) {

            val errorModel = ErrorModel(
                errorMessages = listOf(),
                message = "Unexpected error: ${e.message}",
                statusCode = 0,
                success = false
            )

            ForgotPassResult.Error(errorModel)
        }


    }

}