package com.example.authmodulejpc.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authentication.data.login.LoginResponseModel
import com.example.authentication.ui.theme.components.InputFieldWithLabel
import com.example.authentication.ui.theme.components.PageName

@Composable
fun SignUpScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToOTP: () -> Unit,
){
    var checked by remember { mutableStateOf(false) }
    val email = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val profileState = remember { mutableStateOf<LoginResponseModel?>(null) }
    val confirmPassword = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(all = 24.dp)
    ) {
        Spacer(Modifier.weight(1f))
        Column{
            PageName(
                pageTitle = "Sign up with Email",
                pageSubTitle = "Get chatting with friends and family today by signing up for our chat app!",
            )
        }
        Spacer(modifier = Modifier.size(60.dp))
        Column {
            InputFieldWithLabel(
                label = "Your name",
                hintText = "",
                textFieldValue = name,
            )
            Box(modifier = Modifier.size(30.dp))

            InputFieldWithLabel(
                label = "Your email",
                hintText = "",
                textFieldValue = email,
            )
            Box(modifier = Modifier.size(30.dp))


            InputFieldWithLabel(
                label = "Password",
                hintText = "",
                textFieldValue = password,
            )
            Box(modifier = Modifier.size(30.dp))


            InputFieldWithLabel(
                label = "Confirm Password",
                hintText = "",
                textFieldValue = confirmPassword,
            )

        }

        Spacer(Modifier.weight(1f))

        Column {
            Button(
                onClick = { checked = !checked },
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
                    text = "Create an account",
                    modifier = Modifier.clickable {
                        onNavigateToOTP()
                    }
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Already have any account? Login",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color(0xFF24786D),
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .clickable {
                        onNavigateToLogin()
                    }
            )
        }
    }
}