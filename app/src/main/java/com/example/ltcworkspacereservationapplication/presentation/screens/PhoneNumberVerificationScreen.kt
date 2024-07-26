package com.example.ltcworkspacereservationapplication.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ltcworkspacereservationapplication.PhoneAuth
import com.example.ltcworkspacereservationapplication.presentation.utils.ProgressBar
import com.example.ltcworkspacereservationapplication.presentation.utils.Routes
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor
import com.example.ltcworkspacereservationapplication.storedVerificationId

@Composable
fun PhoneNumberVerificationScreen(navController: NavHostController) {
    var phoneNumber by remember { mutableStateOf("") }
    var isPhoneNumberValid by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    isPhoneNumberValid = phoneNumber.length == 10 && phoneNumber.all { it.isDigit() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 16.dp, top = 16.dp).semantics {
                    contentDescription = "Back"
                }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = AppColor.primaryColor
            )
        }
        Text(
            text = "Verify Your Phone Number",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = AppColor.primaryColor,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()  .semantics {
                    contentDescription = "Verify Your Phone Number"
                }
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { newValue ->
                phoneNumber = newValue
            },
            placeholder = { Text("Enter your phone number") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            isError = phoneNumber.isEmpty() || !isPhoneNumberValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp).semantics {
                    contentDescription = "Phone number input field"
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Gray
            )
        )
        if (phoneNumber.isEmpty() || !isPhoneNumberValid) {
            Text(
                text = "Phone number must be 10 digits",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp).semantics {
                        contentDescription = "Phone number validation message"
                    }
            )
        }

        Button(
            onClick = {
                if (isPhoneNumberValid) {
                    isLoading = true
                    PhoneAuth(navController.context, phoneNumber) {
                        val verificationId = storedVerificationId ?: ""
                        isLoading = false
                        navController.navigate("${Routes.OTP_SCREEN}?verificationId=$verificationId&phoneNumber=$phoneNumber")
                    }

                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isPhoneNumberValid) AppColor.primaryColor else AppColor.primaryColorLight,
                disabledContainerColor = AppColor.primaryColorLight
            ),
            enabled = isPhoneNumberValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(50.dp)
                .background(if (isPhoneNumberValid) AppColor.primaryColor else AppColor.primaryColorLight)
                .border(
                    1.dp,
                    if (isPhoneNumberValid) AppColor.primaryColor else AppColor.primaryColorLight
                ).semantics {
                    contentDescription = "Verify button"
                }
        ) {
            Text(
                text = "Verify",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
    ProgressBar(isDisplayed = isLoading)
}