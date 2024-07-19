package com.example.ltcworkspacereservationapplication.presentation.state

import com.example.ltcworkspacereservationapplication.R
import com.example.ltcworkspacereservationapplication.presentation.utils.dummyData.listData

data class AppState(
    val employeeId : Int = 5605305,
    val employeeName : String = "Raju Kumar",
    val deskList: List<Pair<Int, String>> = listData.deskList,
    val cabinList: List<Pair<Int, String>> = listData.cabinList
)
