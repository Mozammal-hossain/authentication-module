package com.example.authentication.ui.screen.setPassword

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.authentication.model.SetNewPassResult
import com.example.authentication.ui.theme.components.InputFieldWithLabel
import com.example.authentication.ui.theme.components.PageName
import timber.log.Timber

@Composable
fun SetPasswordScreen(
    navigateToLogin: () -> Unit
) {
    val viewModel = hiltViewModel<SetPasswordViewModel>()

    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val context = LocalContext.current

    // Observing the LiveData from the ViewModel
    val setNewPassState by viewModel.setNewPassState.observeAsState()
    val errorState by viewModel.errorState.observeAsState()

    // Trigger navigation when setPassState is Success
    LaunchedEffect(setNewPassState) {
        if (setNewPassState is SetNewPassResult.Success) {
            Timber.i("Forgot Password successful: ${(setNewPassState as SetNewPassResult.Success).response.message}")

            navigateToLogin()

            Toast.makeText(
                context,
                (setNewPassState as SetNewPassResult.Success).response.message,
                Toast.LENGTH_LONG
            ).show()

            viewModel.resetSetPassState() // Reset after handling success
        }
    }

    LaunchedEffect(errorState) {
        errorState?.let {
            Toast.makeText(context, errorState.toString(), Toast.LENGTH_LONG).show()
            viewModel.resetSetPassState()
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
            pageTitle = "Reset Password",
            pageSubTitle = "Please enter a new password. Don’t enter your old password.",
        )
        Spacer(modifier = Modifier.height(70.dp))

        InputFieldWithLabel(
            label = "Password",
            hintText = "",
            textFieldValue = password,
        )

        Spacer(modifier = Modifier.height(30.dp))

        InputFieldWithLabel(
            label = "Confirm Password",
            hintText = "",
            textFieldValue = confirmPassword,
        )

        Spacer(Modifier.weight(1f))

        Column {
            Button(
                onClick = {
                    viewModel.setPass(
                        password = password.value,
                        confirmPassword = confirmPassword.value
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
                    text = "Reset Password",
                )
            }
        }

    }
}