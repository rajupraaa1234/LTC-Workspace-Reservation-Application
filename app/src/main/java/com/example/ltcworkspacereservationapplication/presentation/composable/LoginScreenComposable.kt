package com.example.ltcworkspacereservationapplication.presentation.composable


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ltcworkspacereservationapplication.presentation.utils.Routes
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor

@Composable
fun LoginScreenComposable(navController: NavHostController) {
    var employeeId by remember { mutableStateOf(TextFieldValue("")) }
    var employeeIdError by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var passwordError by remember { mutableStateOf(false) }

    val employeeIdRegex = Regex("^\\d{7}\$")
    val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = AppColor.textColorPrimary,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("Welcome to ")
                }
                withStyle(
                    style = SpanStyle(
                        color = AppColor.primaryColor,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("WorkSpot")
                }
            },
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Hello there, enter your credentials to continue",
            fontSize = 14.sp,
            color = AppColor.textColorSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Employee ID",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            fontSize = 16.sp,
            color = AppColor.textColorPrimary
        )
        OutlinedTextField(
            value = employeeId,
            onValueChange = {
                employeeId = it
                employeeIdError = !employeeIdRegex.matches(employeeId.text)
            },
            placeholder = { Text("Enter your Employee ID") },
            isError = employeeIdError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        if (employeeIdError) {
            Text(
                text = "Employee ID must be 7 digits",
                color = AppColor.errorColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }

        Text(
            text = "Password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            fontSize = 16.sp,
            color = AppColor.textColorPrimary
        )
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = !passwordRegex.matches(password.text)
            },
            placeholder = { Text("Enter your Password") },
            isError = passwordError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )
        if (passwordError) {
            Text(
                text = "Password must be at least 8 characters, include a number and a special character",
                color = AppColor.errorColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }

        val isButtonEnabled =
            !employeeIdError && !passwordError && employeeId.text.isNotEmpty() && password.text.isNotEmpty()
        Button(
            onClick = {
                if (isButtonEnabled) {
                    navController.navigate(Routes.verifyPhoneNo)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isButtonEnabled) AppColor.primaryColor else AppColor.primaryColorLight,
                disabledContainerColor = AppColor.primaryColorLight
            ),
            enabled = isButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(50.dp)
                .background(if (isButtonEnabled) AppColor.primaryColor else AppColor.primaryColorLight)
                .border(
                    1.dp,
                    if (isButtonEnabled) AppColor.primaryColor else AppColor.primaryColorLight
                )
        ) {
            Text(
                text = "Continue",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}