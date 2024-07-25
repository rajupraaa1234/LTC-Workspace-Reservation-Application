package com.example.ltcworkspacereservationapplication.presentation.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.log

object Utils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun isTimeSlotOverlapping(reservedTimeSlots: List<String>, newStartTime: String, newEndTime: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val newStart = LocalTime.parse("${newStartTime}:00", formatter)
        val newEnd = LocalTime.parse("${newEndTime}:00", formatter)

        for (slot in reservedTimeSlots) {
            val splitTime = slot.split("-")
            val reservedStart = LocalTime.parse(splitTime[0], formatter)
            val reservedEnd = LocalTime.parse(splitTime[1], formatter)
            if (newStart.isBefore(reservedEnd) && newEnd.isAfter(reservedStart)) {
                return true
            }
        }
        return false
    }

}