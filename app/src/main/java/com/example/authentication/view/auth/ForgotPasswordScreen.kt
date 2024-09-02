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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authentication.model.ForgotPassResult
import com.example.authentication.ui.theme.components.InputFieldWithLabel
import com.example.authentication.ui.theme.components.PageName
import com.example.authentication.view.main.AuthModuleScreen
import com.example.authentication.viewModel.ForgotPassViewModel
import timber.log.Timber


@Composable
fun ForgotPasswordScreen(
    forgotPassViewModel: ForgotPassViewModel = viewModel(),
    onNavigateToOTP: () -> Unit
) {
    val email = remember { mutableStateOf("") }
    val context = LocalContext.current

    // Observing the LiveData from the ViewModel
    val forgotPassState by forgotPassViewModel.forgotPassState.observeAsState()
    val errorState by forgotPassViewModel.errorState.observeAsState()

    forgotPassState?.let {
        if (it is ForgotPassResult.Success) {
            forgotPassViewModel.resetForgotPassState()
            onNavigateToOTP()
            Toast.makeText(
                context,
                it.response.message,
                Toast.LENGTH_LONG
            ).show()

            Timber.i("Forgot Password successful: ${it.response}")
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



                    errorState?.let {
                        Toast.makeText( context,   it, Toast.LENGTH_LONG).show()
                    }

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