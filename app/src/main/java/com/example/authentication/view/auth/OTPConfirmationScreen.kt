package com.example.authentication.view.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authentication.model.OTPValidationResult
import com.example.authentication.ui.theme.components.InputFieldWithLabel
import com.example.authentication.ui.theme.components.PageName
import com.example.authentication.viewModel.ForgotPassViewModel
import timber.log.Timber

@Composable
fun OTPConfirmationScreen(
    onNavigateToLogin: () -> Unit,
    forgotPassViewModel: ForgotPassViewModel
) {

    val otpCode = remember { mutableStateOf("") }
    val context = LocalContext.current

    // Observing the LiveData from the ViewModel
    val otpValidationState by forgotPassViewModel.otpValidationState.observeAsState()
    val errorState by forgotPassViewModel.errorState.observeAsState()


    otpValidationState?.let {
        if ( it is OTPValidationResult.Success) {

            forgotPassViewModel.resetOTPValidationState()
            Toast.makeText(
                context,
                "OTP code is valid",
                Toast.LENGTH_LONG
            ).show()

            onNavigateToLogin()

            Timber.i("OTP Validation successful: ${it.response.message}")
        }
    }


    errorState?.let {
        Timber.e("OTP Validation failed: $it")
        Toast.makeText(
            context,
            it,
            Toast.LENGTH_LONG
        ).show()
        forgotPassViewModel.resetOTPValidationState() // Reset error state after handling
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(all = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        PageName(
            pageTitle = "Email Confirmation",
            pageSubTitle = "We’ve sent a code to your email address. Please check your inbox.",
        )
        Spacer(modifier = Modifier.height(70.dp))

        InputFieldWithLabel(
            label = "Your Code",
            hintText = "",
            textFieldValue = otpCode,
        )

        Spacer(Modifier.weight(1f))

        Column {
            Button(
                onClick = {
                    forgotPassViewModel.validateOTP(
                        otp = otpCode.value
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF24786D)
                ),
            ) {
                Text(
                    text = "Submit",
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Resend code",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color(0xFF24786D),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }

    }
}