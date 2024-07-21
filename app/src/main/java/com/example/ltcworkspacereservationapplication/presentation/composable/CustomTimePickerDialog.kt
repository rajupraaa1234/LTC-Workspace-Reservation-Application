package com.example.ltcworkspacereservationapplication.presentation.composable

import android.widget.TimePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun CustomTimePickerDialog(onTimeSelected: (String) -> Unit) {
    val mContext = LocalContext.current
    val calendar = Calendar.getInstance()

    val mHour = calendar.get(Calendar.HOUR_OF_DAY)
    val mMinute = calendar.get(Calendar.MINUTE)

    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    val timePickerDialog = android.app.TimePickerDialog(
        mContext,
        { _: TimePicker, hourOfDay: Int, minute: Int ->
            val time = calendar.apply {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }.time
            onTimeSelected(timeFormatter.format(time))
        },
        mHour,
        mMinute,
        true
    )

    LaunchedEffect(key1 = Unit) {
        timePickerDialog.show()
    }
}
