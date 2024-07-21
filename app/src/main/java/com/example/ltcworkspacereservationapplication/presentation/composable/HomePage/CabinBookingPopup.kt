package com.example.ltcworkspacereservationapplication.presentation.composable.HomePage

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ltcworkspacereservationapplication.presentation.composable.CustomDatePickerDialog
import com.example.ltcworkspacereservationapplication.presentation.composable.CustomTimePickerDialog
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor

@Composable
fun CabinBookingPopup(
    onDismissRequest: () -> Unit,
    onSubmit: (String, String) -> Unit
) {
    var cabinCapacity by remember { mutableStateOf("") }
    var dateTime by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    // Launch the DatePicker dialog
    if (showDatePicker) {
        CustomDatePickerDialog { selectedDate ->
            dateTime = selectedDate
            showDatePicker = false
            showTimePicker = true
        }
    }

    // Launch the TimePicker dialog
    if (showTimePicker) {
        CustomTimePickerDialog { selectedTime ->
            dateTime = "$dateTime $selectedTime"
            showTimePicker = false
        }
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            elevation = 24.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Please enter the below details for cabin booking",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = AppColor.primaryColor
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Cabin Capacity",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                OutlinedTextField(
                    value = cabinCapacity,
                    onValueChange = { newValue ->
                        // Ensure only numeric values are allowed
                        if (newValue.all { it.isDigit() }) {
                            cabinCapacity = newValue
                        }
                    },
                    label = { Text("Cabin Capacity") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Date & Time",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true }
                        .border(1.dp, Color.Gray)
                        .padding(16.dp)
                ) {
                    Text(
                        text = if (dateTime.isNotEmpty()) dateTime else "Select Date & Time",
                        style = MaterialTheme.typography.body1,
                        color = if (dateTime.isNotEmpty()) Color.Black else Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { onSubmit(cabinCapacity, dateTime) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = AppColor.primaryColor,
                            contentColor = AppColor.backgroundColor
                        )
                    ) {
                        Text("Submit")
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = onDismissRequest,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = AppColor.primaryColor,
                            contentColor = AppColor.backgroundColor
                        )
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}