package com.example.authentication.ui.screen.otpConfirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication.model.OTPValidationModel
import com.example.authentication.model.OTPValidationResult
import com.example.authentication.ui.screen.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OTPConfirmationViewModel @Inject constructor(
    private val otpValidationModel: OTPValidationModel,
    private val sharedViewModel: SharedViewModel
) : ViewModel() {
    private val _otpValidationState = MutableLiveData<OTPValidationResult?>()
    val otpValidationState: LiveData<OTPValidationResult?> get() = _otpValidationState

    /// LiveData for error state
    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState

    val email = sharedViewModel.getEmail() ?: ""


    fun validateOTP(otp: String) {

        viewModelScope.launch {
            when (val result = otpValidationModel.validateOTP(email, otp)) {

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

}