package com.example.authentication.ui.screen.forgotPssword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.model.ForgotPassModel
import com.example.authentication.model.ForgotPassResult
import com.example.authentication.model.OTPValidationModel
import com.example.authentication.model.OTPValidationResult
import com.example.authentication.model.SetNewPassModel
import com.example.authentication.model.SetNewPassResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ForgotPassViewModel @Inject constructor(
    private val forgotPassModel: ForgotPassModel,
    private val otpValidationModel: OTPValidationModel,
    private val setNewPassModel: SetNewPassModel
) : ViewModel() {


    // Email for password reset
    private var correctEmail: String = ""

    /// LiveData for forgot password state
    private val _forgotPassState = MutableLiveData<ForgotPassResult? >()
    val forgotPassState: LiveData<ForgotPassResult?> = _forgotPassState


    /// LiveData for error state
    private  val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState


    fun forgotPassword(email: String) {

        viewModelScope.launch {
            when (val result = forgotPassModel.forgotPassword(email)) {
                is ForgotPassResult.Success -> {
                    _forgotPassState.value = result
                    _errorState.value = null

                    correctEmail = email;


                    Timber.i("Login successful: ${result.response.message}")
                }
                is ForgotPassResult.Error -> {
                    _errorState.value = result.errorModel.message
                    Timber.e("Login failed: ${result.errorModel.message}")
                }
            }
        }
    }

    fun resetForgotPassState() {
        _forgotPassState.value = null
    }

    private  val _otpValidationState = MutableLiveData<OTPValidationResult?>()
    val otpValidationState: LiveData<OTPValidationResult?> get() = _otpValidationState



    fun validateOTP(otp: String){
        Timber.i("email is $correctEmail and otp is $otp");

        viewModelScope.launch {
            when (val result = otpValidationModel.validateOTP(correctEmail, otp)) {

                is OTPValidationResult.Success -> {
                    _otpValidationState.value = result
                    _errorState.value = null  // Clear any previous errors
                    Timber.i("OTP successful: ${result.response.message}")
                }
                is OTPValidationResult.Error -> {
                    _errorState.value = result.error.message
                    Timber.e("OTP failed: ${result.error.message}")
                }
            }
        }
    }

    fun resetOTPValidationState() {
        _otpValidationState.value = null
    }


    private  val _setNewPassState = MutableLiveData<SetNewPassResult?>()
    val setNewPassState: LiveData<SetNewPassResult?> get() = _setNewPassState



    fun setPass(password: String, confirmPassword: String){
        viewModelScope.launch {
           when (val result = setNewPassModel.setNewPass(correctEmail, password, confirmPassword)) {
                is SetNewPassResult.Success -> {
                    _setNewPassState.value = result
                    _errorState.value = null  // Clear any previous errors
                    Timber.i("Reset successful: ${result.response.message}")
                }
                is SetNewPassResult.Error -> {
                    _errorState.value = result.errorModel.message
                    Timber.e("Reset failed: ${result.errorModel.message}")
                }
            }

        }
    }

    fun resetSetPassState() {
        _setNewPassState.value = null
    }




}