package com.example.authentication.model

import com.example.authentication.model.data.setNewPass.SetNewPassReqModel
import com.example.authentication.model.data.setNewPass.SetNewPassResModel
import com.example.authentication.model.data.shared.ErrorModel
import com.example.authentication.model.shared.ErrorUtils.parseError
import com.example.authentication.services.network.NetworkService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed class SetNewPassResult {
    data class Success(val response: SetNewPassResModel) : SetNewPassResult()
    data class Error(val errorModel: ErrorModel) : SetNewPassResult()
}

class SetNewPassModel @Inject constructor(
    private val networkService: NetworkService
) {

    suspend fun setNewPass(email: String, password: String, confirmPassword: String): SetNewPassResult {
        return try{
            val response = networkService.setNewPass(
                SetNewPassReqModel(
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword,
                ),
            )

            SetNewPassResult.Success(response)
        } catch (e: HttpException) {

            val errorModel = parseError(e)

            SetNewPassResult.Error(errorModel)
        } catch (e: IOException) {

            val errorModel = ErrorModel(
                errorMessages = listOf(),
                message = "Network error: ${e.message}",
                statusCode = 0,
                success = false
            )

            SetNewPassResult.Error(errorModel)
        }
    }
}