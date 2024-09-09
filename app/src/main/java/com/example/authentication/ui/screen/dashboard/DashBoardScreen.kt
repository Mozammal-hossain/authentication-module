package com.example.authentication.ui.screen.dashboard

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.authentication.ui.theme.components.PageName
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
import timber.log.Timber
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun DashBoardScreen(
    navigateToLogin: () -> Unit,
) {

    val viewModel = hiltViewModel<DashboardInfoViewModel>()

    val user by viewModel.userState.observeAsState()
    val context = LocalContext.current

    // Observing the LiveData from the ViewModel
    val logoutState by viewModel.logoutSuccessState.observeAsState()

    val errorState by viewModel.errorState.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getUser()
    }


    LaunchedEffect(logoutState) {
        if (logoutState == true) {
            navigateToLogin()

            Toast.makeText( context, "Logout successful", Toast.LENGTH_LONG).show()
        }
    }
    LaunchedEffect(errorState) {
        errorState?.let {
            Toast.makeText( context, "Logout failed", Toast.LENGTH_LONG).show()
        }
    }




    logger.info("User: ${user?.profilePic}")

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

        // Display the image from Base64

        if(user?.profilePic != null) {
            Timber.i("ProfilePic: ${user!!.profilePic}")
            Image(
                bitmap = base64toBitmap(user!!.profilePic.toString()).asImageBitmap(),
                contentDescription = "Profile Picture",
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            AsyncImage(
                model = "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg",
                contentDescription = "Placeholder Image"
            )
        }


        Spacer(Modifier.weight(1f))

        Button(
            onClick = {viewModel.getProfile()},
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
                text = "Get Profile Data",
            )
        }

        Button(
            onClick = {viewModel.logout()},
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
                text = "Log out",
            )
        }


    }
}

@OptIn(kotlin.io.encoding.ExperimentalEncodingApi::class)
fun base64toBitmap(base64String: String): Bitmap {
    val decodedBytes = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}