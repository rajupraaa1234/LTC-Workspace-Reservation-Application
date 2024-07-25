package com.example.ltcworkspacereservationapplication.presentation.screens


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.ltcworkspacereservationapplication.R
import com.example.ltcworkspacereservationapplication.presentation.mvvm.AppIntent
import com.example.ltcworkspacereservationapplication.presentation.mvvm.ReservationViewModel
import com.example.ltcworkspacereservationapplication.presentation.utils.PreferencesManager
import com.example.ltcworkspacereservationapplication.presentation.utils.Routes
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor
import kotlinx.coroutines.launch

@Composable
fun LoginScreenComposable(navController: NavHostController,viewModel: ReservationViewModel) {
    var employeeName by remember { mutableStateOf(TextFieldValue("")) }
    var employeeNameError by remember { mutableStateOf(false) }
    var employeeId by remember { mutableStateOf(TextFieldValue("")) }
    var employeeIdError by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var passwordError by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val employeeNameRegex = Regex("^[A-Za-z ]+$")
    val employeeIdRegex = Regex("^\\d{7}\$")
    val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
    val context = LocalContext.current
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painterResource(id = R.drawable.workspot_logo),
            contentDescription = "Login",
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
                .padding(bottom = 32.dp)
                .height(50.dp)
                .width(50.dp))
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
            text = "Employee Name",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            fontSize = 16.sp,
            color = AppColor.textColorPrimary
        )
        OutlinedTextField(
            value = employeeName,
            onValueChange = {
                employeeName = it
                employeeNameError = !employeeNameRegex.matches(employeeName.text)
            },
            placeholder = { Text("Enter your Employee Name") },
            isError = employeeNameError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
        )
        if (employeeNameError) {
            Text(
                text = "Employee Name must contain only letters",
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
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = !passwordRegex.matches(password.text)
                },
                placeholder = { Text("Enter your Password") },
                isError = passwordError,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painterResource(id = if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            )
        }
        if (passwordError) {
            Text(
                text = "Password must be at least 8 characters, include a number and a special character",
                color = AppColor.errorColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }

        val isButtonEnabled = !employeeNameError && !employeeIdError && !passwordError &&
                    employeeName.text.isNotEmpty() && employeeId.text.isNotEmpty() && password.text.isNotEmpty()
        Button(
            onClick = {
                if (isButtonEnabled) {
                    PreferencesManager.setEmployeeId(context = context, employeeId = employeeId.text)
                    viewModel.viewModelScope.launch {
                        viewModel.sendIntent(AppIntent.onLoginClick(employeeId.text))
                    }
                    navController.navigate(Routes.VERIFY_PHONE_NUMBER)
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