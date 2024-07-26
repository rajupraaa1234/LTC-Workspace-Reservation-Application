package com.example.ltcworkspacereservationapplication.presentation.composable

import android.widget.DatePicker
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun CustomDatePickerDialog(onDateSelected: (String) -> Unit) {
    val selectedDate = remember { mutableStateOf<Calendar?>(null) }
    val today = Calendar.getInstance()
    val dayAfterTomorrow = (today.clone() as Calendar).apply { add(Calendar.DAY_OF_YEAR, 2) }
    val showDatePicker = remember { mutableStateOf(false) }
    DatePickerDialog(
        onDismissRequest = { showDatePicker.value = false },
        onDateSelected = { date ->
            selectedDate.value = Calendar.getInstance().apply { timeInMillis = date }
            selectedDate.value?.let {
                val formattedDate =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.time)
                onDateSelected(formattedDate)
            }
            showDatePicker.value = false
        },
        startDate = today,
        endDate = dayAfterTomorrow
    )
}

@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (Long) -> Unit,
    startDate: Calendar,
    endDate: Calendar
) {
    androidx.compose.material.AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Select Date") },
        text = {
            AndroidView(
                factory = { context ->
                    DatePicker(context).apply {
                        contentDescription = "Date picker"
                        minDate = startDate.timeInMillis
                        maxDate = endDate.timeInMillis
                        init(
                            startDate.get(Calendar.YEAR),
                            startDate.get(Calendar.MONTH),
                            startDate.get(Calendar.DAY_OF_MONTH)
                        ) { _, year, monthOfYear, dayOfMonth ->
                            val selectedCalendar = Calendar.getInstance().apply {
                                set(year, monthOfYear, dayOfMonth)
                            }
                            onDateSelected(selectedCalendar.timeInMillis)
                        }
                    }
                },
                update = { view ->
                    view.minDate = startDate.timeInMillis
                    view.maxDate = endDate.timeInMillis
                }
            )
        },
        confirmButton = {},
        dismissButton = {}
    )
}