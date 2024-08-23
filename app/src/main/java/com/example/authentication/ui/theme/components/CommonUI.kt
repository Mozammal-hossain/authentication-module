package com.example.authentication.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PageName(
    pageTitle: String,
    pageSubTitle: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = pageTitle,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        Text(
            text = pageSubTitle,
            color = Color.Gray.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier =
            Modifier.padding(
                horizontal = 41.dp,
                vertical = 8.dp,
            ),
        )
    }
}


@Composable
fun InputFieldWithLabel(
    label: String,
    hintText: String,
    textFieldValue: MutableState<String>,
) {

    Column(
        modifier = Modifier
            .padding(0.dp)
    ) {

        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color(0xFF24786D)
        )

        BasicTextField(
            value = textFieldValue.value,
            singleLine = true,
            onValueChange = { textFieldValue.value = it },
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .height(30.dp)
                        .drawBehind {
                            drawLine(
                                color = Color.Gray.copy(alpha = 0.5f),
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                            )
                        }
                    ,
                ) {
                    if (textFieldValue.value.isEmpty()) {
                        Text(
                            text = hintText,
                            color = Color(0xFF24786D).copy(alpha = 0.2f),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 0.dp,
                                ),

                            )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 5.dp,
                            ),
                    ) {
                        innerTextField()
                    }
                }
            },

            )
    }
}
