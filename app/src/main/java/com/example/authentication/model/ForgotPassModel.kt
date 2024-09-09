package com.example.authentication.model

import com.example.authentication.model.data.remote.forgotPassword.ForgotPassReqModel
import com.example.authentication.model.data.remote.forgotPassword.ForgotPassResModel
import com.example.authentication.model.data.shared.ErrorModel
import com.example.authentication.model.shared.ErrorUtils.parseError
import com.example.authentication.services.network.NetworkService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed class ForgotPassResult {
    data class Success(val response: ForgotPassResModel) : ForgotPassResult()
    data class Error(val errorModel: ErrorModel) : ForgotPassResult()
}



class ForgotPassModel @Inject constructor(
    private val apiService: NetworkService
) {
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