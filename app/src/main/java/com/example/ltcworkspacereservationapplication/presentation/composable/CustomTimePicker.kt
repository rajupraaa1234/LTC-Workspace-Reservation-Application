package com.example.ltcworkspacereservationapplication.presentation.composable

import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar


@Composable
fun CustomTimePicker(type: Int, onTimeSelected: (String) -> Unit) {
    val context = LocalContext.current
    val currentTime = Calendar.getInstance()
    val hour = currentTime.get(Calendar.HOUR_OF_DAY)
    val minute = currentTime.get(Calendar.MINUTE)

    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            val time = String.format("%02d:%02d", selectedHour, selectedMinute)
            onTimeSelected(time)
        },
        hour,
        minute,
        true
    )

    timePickerDialog.setOnCancelListener {
        if (type == 1)
            onTimeSelected(START_TIME);
        else
            onTimeSelected(END_TIME);
    }

    LaunchedEffect(key1 = Unit) {
        timePickerDialog.show()
    }
}

private const val START_TIME = "Start Time"
private const val END_TIME = "End Time"