package com.example.ltcworkspacereservationapplication.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ltcworkspacereservationapplication.presentation.utils.FirebaseUtils
import com.example.ltcworkspacereservationapplication.presentation.utils.ProgressBar
import com.example.ltcworkspacereservationapplication.presentation.utils.Routes
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor
import com.google.firebase.auth.PhoneAuthProvider

@Composable
fun OtpComposableScreen(navController: NavHostController,verificationId:String, phoneNumber: String, onLogin: () -> Unit) {
    var otp by remember { mutableStateOf("") }
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester3 = remember { FocusRequester() }
    val focusRequester4 = remember { FocusRequester() }
    val focusRequester5 = remember { FocusRequester() }
    val focusRequester6 = remember { FocusRequester() }
    var isOTPValid by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 16.dp, top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = AppColor.primaryColor
            )
        }
        Text(
            text = "Enter the OTP",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = AppColor.primaryColor,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Sent to: $phoneNumber",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            OtpBox(
                otp = otp.getOrNull(0)?.toString() ?: "",
                onValueChange = { newValue -> otp = newValue + otp.drop(1) },
                focusRequester = focusRequester1,
                nextFocusRequester = focusRequester2
            )
            Spacer(modifier = Modifier.width(8.dp))
            OtpBox(
                otp = otp.getOrNull(1)?.toString() ?: "",
                onValueChange = { newValue -> otp = otp.take(1) + newValue + otp.drop(2) },
                focusRequester = focusRequester2,
                nextFocusRequester = focusRequester3
            )
            Spacer(modifier = Modifier.width(8.dp))
            OtpBox(
                otp = otp.getOrNull(2)?.toString() ?: "",
                onValueChange = { newValue -> otp = otp.take(2) + newValue + otp.drop(3) },
                focusRequester = focusRequester3,
                nextFocusRequester = focusRequester4
            )
            Spacer(modifier = Modifier.width(8.dp))
            OtpBox(
                otp = otp.getOrNull(3)?.toString() ?: "",
                onValueChange = { newValue -> otp = otp.take(3) + newValue + otp.drop(4) },
                focusRequester = focusRequester4,
                nextFocusRequester = focusRequester5
            )
            Spacer(modifier = Modifier.width(8.dp))
            OtpBox(
                otp = otp.getOrNull(4)?.toString() ?: "",
                onValueChange = { newValue -> otp = otp.take(4) + newValue + otp.drop(5) },
                focusRequester = focusRequester5,
                nextFocusRequester = focusRequester6
            )
            Spacer(modifier = Modifier.width(8.dp))
            OtpBox(
                otp = otp.getOrNull(5)?.toString() ?: "",
                onValueChange = { newValue -> otp = otp.take(5) + newValue },
                focusRequester = focusRequester6
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        isOTPValid = otp.length == 6
        Button(
            onClick = {
                if (isOTPValid) {
                    isLoading = true
                    try {
                        val credential = PhoneAuthProvider.getCredential(verificationId, otp)
                        Log.d("otp", "Credential: $credential")
                        Log.d("otp", "Verification ID: $verificationId")

                        FirebaseUtils.firebaseAuth.signInWithCredential(credential)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("firebase", "Sign in successful")
                                    onLogin()
                                    navController.navigate(Routes.HOME_SCREEN)
                                } else {
                                    Toast.makeText(navController.context, "Invalid OTP, Please try again",Toast.LENGTH_SHORT).show()
                                    Log.d("firebase", "Sign in failed: ${task.exception?.message}")
                                }
                            }
                    } catch (e: Exception) {
                        isLoading = false
                        Log.d("otp", "Exception: ${e.message}")
                    }
                } else {
                    Toast.makeText(navController.context, "Invalid OTP, Please try again",Toast.LENGTH_SHORT).show()
                    Log.d("otp", "Invalid OTP")
                }

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isOTPValid) AppColor.primaryColor else AppColor.primaryColorLight,
                disabledContainerColor = AppColor.primaryColorLight
            ),
            enabled = isOTPValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(if (isOTPValid) AppColor.primaryColor else AppColor.primaryColorLight)
                .border(
                    1.dp,
                    if (isOTPValid) AppColor.primaryColor else AppColor.primaryColorLight
                )
        ) {
            Text(
                text = "Confirm",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = AppColor.whiteColor
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester1.requestFocus()
    }
    ProgressBar(isDisplayed = isLoading)
}

@Composable
fun OtpBox(
    otp: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester? = null
) {
    OutlinedTextField(
        value = otp,
        onValueChange = {
            if (it.length <= 1) {
                onValueChange(it)
                if (it.isNotEmpty() && nextFocusRequester != null) {
                    nextFocusRequester.requestFocus()
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .focusRequester(focusRequester),
        textStyle = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Center),
        singleLine = true,
        maxLines = 1
    )
}