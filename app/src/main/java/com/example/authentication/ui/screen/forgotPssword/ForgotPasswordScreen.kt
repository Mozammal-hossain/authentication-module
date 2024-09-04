package com.example.authentication.ui.screen.forgotPssword

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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.authentication.model.ForgotPassResult
import com.example.authentication.ui.theme.components.InputFieldWithLabel
import com.example.authentication.ui.theme.components.PageName
import timber.log.Timber


@Composable
fun ForgotPasswordScreen(onNavigateToOTP: (String) -> Unit,
                         forgotPassViewModel: ForgotPassViewModel,
    ) {

    val email = remember { mutableStateOf("") }
    val context = LocalContext.current

    // Observing the LiveData from the ViewModel
    val forgotPassState by forgotPassViewModel.forgotPassState.observeAsState()
    val errorState by forgotPassViewModel.errorState.observeAsState()

    // Trigger navigation when forgotPassState is Success
    LaunchedEffect(forgotPassState) {
        if (forgotPassState is ForgotPassResult.Success) {
            Timber.i("Forgot Password successful: ${(forgotPassState as ForgotPassResult.Success).response.message}")
            onNavigateToOTP(email.value)
            Toast.makeText(context, (forgotPassState as ForgotPassResult.Success).response.message, Toast.LENGTH_LONG).show()
            forgotPassViewModel.resetForgotPassState() // Reset after handling success
        }
    }

    LaunchedEffect(errorState) {
        errorState?.let {
            Toast.makeText(context, errorState.toString() , Toast.LENGTH_LONG).show()
            forgotPassViewModel.resetForgotPassState()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(all = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        PageName(
            pageTitle = "Forgot Password",
            pageSubTitle = "Enter your email address. We will send a code to verify your identity",
        )
        Spacer(modifier = Modifier.height(70.dp))

        InputFieldWithLabel(
            label = "Your Email",
            hintText = "",
            textFieldValue = email,
        )

        Spacer(Modifier.weight(1f))

        Column {
            Button(
                onClick =

                {
                    Timber.tag("ForgotPasswordScreenDebug")
                        .d("Email: ${email.value}")

                    // Trigger the Forgot Password process
                    forgotPassViewModel.forgotPassword(email.value);

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
                text = "Remember your password? Login",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color(0xFF24786D),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }

    }
}