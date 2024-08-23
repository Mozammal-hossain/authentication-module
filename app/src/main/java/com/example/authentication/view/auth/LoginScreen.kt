package com.example.authentication.view.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authentication.R
import com.example.authentication.ui.theme.components.InputFieldWithLabel
import com.example.authentication.ui.theme.components.PageName
import com.example.authentication.viewModel.LoginViewModel
import timber.log.Timber

/**
 * Composable function for the Login Screen.
 *
 * @param onNavigateToDashboard Navigates to the Dashboard screen.
 * @param onNavigateToForgotPassword Navigates to the Forgot Password screen.
 * @param onNavigateToSignUp Navigates to the Sign-Up screen.
 * @param loginViewModel The ViewModel responsible for handling the login logic.
 */
@Composable
fun LoginScreen(
    onNavigateToDashboard: (Int) -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {
    // State variables to store email, password, and remember me checkbox status
    var checked by remember { mutableStateOf(false) }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    // Observing the LiveData from the ViewModel
    val profileState by loginViewModel.profileState.observeAsState()
    val errorState by loginViewModel.errorState.observeAsState()

    // If profileState is not null, navigate to the dashboard
    profileState?.let {
        onNavigateToDashboard(it.user._id.toInt())
        Timber.tag("Login Response").d("Profile state updated: $it")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(140.dp))

        // Page title and social login options
        Column {
            PageName(
                pageTitle = "Log in to Authy",
                pageSubTitle = "Welcome back! Sign in using your social account or email to continue with us"
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Social media login buttons (Facebook, Google, Apple)
            Row(
                modifier = Modifier
                    .width(184.dp)
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SocialLoginButton(iconId = R.drawable.facebook)
                SocialLoginButton(iconId = R.drawable.google)
                SocialLoginButton(iconId = R.drawable.apple)
            }

            Spacer(modifier = Modifier.height(30.dp))

            // OR Divider
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Divider(modifier = Modifier.weight(1f).height(1.dp))
                Text(text = "OR", Modifier.padding(horizontal = 15.dp))
                Divider(modifier = Modifier.weight(1f).height(1.dp))
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Email and Password input fields
        Column {
            InputFieldWithLabel(
                label = "Email",
                hintText = "",
                textFieldValue = email
            )

            Spacer(modifier = Modifier.height(30.dp))

            InputFieldWithLabel(
                label = "Password",
                hintText = "",
                textFieldValue = password
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Displaying error message if available
            errorState?.let {
                Text(text = it, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Remember Me checkbox and Forgot Password link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RememberMeCheckbox(checked = checked, onCheckedChange = { checked = it })

                Text(
                    text = "Forgot Password?",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF24786D),
                    modifier = Modifier.clickable { onNavigateToForgotPassword() }
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Login Button and Sign-Up link
        Column {
            Button(
                onClick = {
                    Timber.tag("LoginScreenDebug")
                        .d("Email: ${email.value} Password: ${password.value}")

                    // Trigger the login process
                    loginViewModel.login(email.value, password.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF24786D))
            ) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Don’t have an account? Sign Up",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color(0xFF24786D),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { onNavigateToSignUp() }
            )
        }
    }
}

/**
 * Composable function for a social login button with an icon.
 *
 * @param iconId Resource ID of the icon to be displayed.
 */
@Composable
fun SocialLoginButton(iconId: Int) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .border(shape = CircleShape, width = 1.dp, color = Color.Black)
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(24.dp)
                .padding(2.dp)
        )
    }
}

/**
 * Composable function for the Remember Me checkbox.
 *
 * @param checked Boolean state of the checkbox.
 * @param onCheckedChange Callback when the checkbox state changes.
 */
@Composable
fun RememberMeCheckbox(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(
            onClick = { onCheckedChange(!checked) },
            modifier = Modifier.size(16.dp),
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(2.dp, Color(0xFF24786D)),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.1f))
        ) {}
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = "Remember Me",
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color(0xFF24786D)
        )
    }
}
