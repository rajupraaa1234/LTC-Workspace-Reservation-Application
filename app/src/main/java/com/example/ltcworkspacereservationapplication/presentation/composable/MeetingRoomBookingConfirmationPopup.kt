package com.example.ltcworkspacereservationapplication.presentation.composable

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ltcworkspacereservationapplication.R
import com.example.ltcworkspacereservationapplication.presentation.utils.Spacing
import com.example.ltcworkspacereservationapplication.presentation.utils.color.AppColor


@Composable
fun ConfirmationPopup(onDismiss: () -> Unit, onSubmit: (String, String, String) -> Unit) {
    var capacity by remember { mutableStateOf("") }

    val startTimeDialogPicker = remember { mutableStateOf(false) }
    val endTimeDialogPicker = remember { mutableStateOf(false) }

    val startTime = remember { mutableStateOf(START_TIME) }
    val endTime = remember { mutableStateOf(END_TIME) }

    val isCapacityValid = capacity.toIntOrNull()?.let { it in 1..50 } == true
    val isTimeValid = startTime.value != START_TIME && endTime.value != END_TIME && startTime.value < endTime.value

    val isButtonEnabled = isCapacityValid && isTimeValid

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(Spacing.Size_16),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CloseButton(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = Spacing.Size_5)
                ) {
                    onDismiss()
                }

                Text(
                    stringResource(id = R.string.popup_instruction),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = capacity,
                    onValueChange = {
                        capacity = it
                    },
                    placeholder = { Text("Capacity") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .border(0.dp, color = AppColor.primaryColor),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                if (!isCapacityValid) {
                    Text(
                        text = "Capacity must be between 1 and 50",
                        color = AppColor.errorColor,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TimeCard(START_TIME, startTime.value) {
                            startTimeDialogPicker.value = true
                        }
                        TimeCard(END_TIME, endTime.value) {
                            endTimeDialogPicker.value = true
                        }
                    }

                    if (startTimeDialogPicker.value) {
                        CustomTimePicker(1) {
                            startTime.value = it
                            startTimeDialogPicker.value = false
                        }

                    }
                    if (endTimeDialogPicker.value) {
                        CustomTimePicker(2) {
                            endTime.value = it
                            endTimeDialogPicker.value = false
                        }
                    }
                }

                if (!isTimeValid && startTime.value != START_TIME && endTime.value != END_TIME) {
                    Text(
                        text = "Start time must be less than end time",
                        color = AppColor.errorColor,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

                Spacer(modifier = Modifier.height(Spacing.Size_20))
                Button(
                    onClick = {
                        onSubmit(startTime.value, endTime.value, capacity)
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
                        text = "Book",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun CloseButton(modifier: Modifier, onClose: () -> Unit) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(Spacing.Size_20)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable { onClose() }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White
            )
        }
    }
}

@Composable
fun TimeCard(label: String, time: String, onClick: () -> Unit) {
    val optedTime = if (time == START_TIME || time == END_TIME) "00:00" else time
    Card(
        shape = RoundedCornerShape(Spacing.Size_16),
        backgroundColor = Color(0xFFF5F5F5),
        modifier = Modifier
            .width(Spacing.Size_120)
            .height(Spacing.Size_60)
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(Spacing.Size_10)
        ) {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Spacer(modifier = Modifier.height(Spacing.Size_8))
            Text(
                text = optedTime,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )
        }
    }
}

private const val START_TIME = "Start Time"
private const val END_TIME = "End Time"