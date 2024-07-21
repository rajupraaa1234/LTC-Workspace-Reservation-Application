package com.example.ltcworkspacereservationapplication.presentation.state

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ltcworkspacereservationapplication.presentation.utils.dummyData.listData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class AppState(
    val employeeId : String = "5605305",
    val employeeName : String = "Raju Kumar",
    val selectedDate : String = getCurrentDate(),
    val deskList: List<Pair<Int, String>> = listData.deskList,
    val cabinList: List<Pair<Int, String>> = listData.cabinList
)

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return currentDate.format(formatter)
}