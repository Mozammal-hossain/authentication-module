@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.authentication.ui.navigation


import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.authentication.R
import com.example.authentication.ui.screen.ChangePasswordScreen
import com.example.authentication.ui.screen.dashboard.DashBoardScreen
import com.example.authentication.ui.screen.forgotPssword.ForgotPasswordScreen
import com.example.authentication.ui.screen.login.LoginScreen
import com.example.authentication.ui.screen.otpConfirmation.OTPConfirmationScreen
import com.example.authentication.ui.screen.setPassword.SetPasswordScreen
import com.example.authentication.ui.screen.signUp.SignUpScreen


enum class AuthModuleScreen(@StringRes val title: Int) {
    Start(title = R.string.login),
    Signup(title = R.string.signup),
    Dashboard(title = R.string.dashboard),
    ForgotPassword(title = R.string.forgot_password),
    ResetPassword(title = R.string.reset_password),
    UpdateProfile(title = R.string.update_profile),
    ChangePassword(title = R.string.change_password),
    EmailConfirmation(title = R.string.email_confirmation),
    DashBoardScreen(title = R.string.dashboard),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthModuleAppBar(
    currentScreen: AuthModuleScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}


@Composable
fun AuthModuleApp(
    navController: NavHostController = rememberNavController()
) {

    Scaffold {
        val innerPadding = it
        NavHost(
            navController = navController,
            startDestination = AuthModuleScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
                .padding(innerPadding)
        ) {
            composable(route = AuthModuleScreen.Start.name) {
                LoginScreen(
                    onNavigateToSignUp = {
                        navController.navigate(AuthModuleScreen.Signup.name)
                    },
                    onNavigateToForgotPassword = {
                        navController.navigate(AuthModuleScreen.ForgotPassword.name)
                    },
                    onNavigateToDashboard = {
                        navController.navigate(AuthModuleScreen.Dashboard.name)
                    },

                    )
            }

            composable(route = AuthModuleScreen.Signup.name) {

                SignUpScreen(
                    onNavigateToLogin = {
                        navController.navigate(AuthModuleScreen.Start.name)
                    },
                    onNavigateToOTP = {
                        navController.navigate("${AuthModuleScreen.EmailConfirmation.name}/false")

                    }
                )
            }

            composable(route = AuthModuleScreen.ForgotPassword.name) {

                ForgotPasswordScreen(
                    onNavigateToOTP = {
                        navController.navigate("${AuthModuleScreen.EmailConfirmation.name}/true")
                    },
                )
            }

            composable(
                route = "${AuthModuleScreen.EmailConfirmation.name}/{isForSettingPassword}",
                arguments = listOf(navArgument("isForSettingPassword") { type = NavType.BoolType })
            ) {
                val isForSettingPassword =
                    it.arguments?.getBoolean("isForSettingPassword") ?: false

                OTPConfirmationScreen(
                    isForSettingPassword = isForSettingPassword, // Pass retrieved argument
                    onNavigateToResetPass = {
                        navController.navigate(AuthModuleScreen.ResetPassword.name)
                    },
                    onNavigateToLogin = {
                        navController.navigate(AuthModuleScreen.Start.name)
                    }
                )
            }



            composable(route = AuthModuleScreen.ResetPassword.name) {

                SetPasswordScreen(
                    navigateToLogin = {
                        navController.navigate(AuthModuleScreen.Start.name)
                    },
                )
            }

            composable(route = AuthModuleScreen.ChangePassword.name) {
                ChangePasswordScreen()
            }

            composable(route = AuthModuleScreen.Dashboard.name) {
                DashBoardScreen(
                    navigateToLogin = {
                        navController.navigate(AuthModuleScreen.Start.name)
                    },
                )

            }

        }

    }

}