package com.example.authentication.viewModel

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.model.OTPValidationModel
import com.example.authentication.model.OTPValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OTPValidationViewModel @Inject constructor(
    private val otpValidationModel: OTPValidationModel
): ViewModel() {

    //Livedata for OTP validation state
    private  val _otpValidationState = MutableLiveData<OTPValidationResult?>()
    val otpValidationState: LiveData<OTPValidationResult?> get() = _otpValidationState

    //Livedata for error state
    private  val _errorState = MutableLiveData<OTPValidationResult?>()
    val errorState: LiveData<OTPValidationResult?> get() = _errorState


    fun validateOTP(email: String,otp: String){
        Timber.i("email is $email and otp is $otp");

        viewModelScope.launch {
            when (val result = otpValidationModel.validateOTP(email, otp)) {

                is OTPValidationResult.Success -> {
                    _otpValidationState.value = result
                    _errorState.value = null  // Clear any previous errors
                    Timber.i("Login successful: ${result.response.message}")
                }
                is OTPValidationResult.Error -> {
                    _errorState.value = result
                    Timber.e("Login failed: ${result.error.message}")
                }
            }
        }
    }

    fun resetOTPValidationState() {
        _otpValidationState.value = null
    }

}