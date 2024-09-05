package com.example.authentication.ui.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.authentication.ui.theme.components.PageName

@Composable
fun DashBoardScreen() {
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val oldPassword = remember { mutableStateOf("") }

    val viewModel = hiltViewModel<DashboardInfo>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(all = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        PageName(
            pageTitle = "Home Page",
            pageSubTitle = "Make sure to enter a strong password to ensure your security.",
        )
        Spacer(modifier = Modifier.height(70.dp))

    }
}