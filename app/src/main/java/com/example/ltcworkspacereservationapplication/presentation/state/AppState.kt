package com.example.ltcworkspacereservationapplication.presentation.state

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.History.DeskHistoryModel
import com.example.ltcworkspacereservationapplication.domain.model.History.MeetingHistory.MeetingRoomHistoryResponse
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel
import com.example.ltcworkspacereservationapplication.presentation.utils.Routes
import com.example.ltcworkspacereservationapplication.presentation.utils.dummyData.CabinHistoryList
import com.example.ltcworkspacereservationapplication.presentation.utils.dummyData.DeskHistoryList
import com.example.ltcworkspacereservationapplication.presentation.utils.dummyData.listData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class AppState @RequiresApi(Build.VERSION_CODES.O) constructor(
    val employeeId: String = "",
    val employeeName: String = "Raju Kumar",
    val selectedDate: String = getCurrentDate(),

    val deskList: List<DeskItemModel> = listData.deskList,
    val cabinList: List<MeetingItemModel> = listData.cabinList,

    var currentFilteredList: List<DeskItemModel> = listData.deskList,
    var currentMeetingRoomFilteredList: List<MeetingItemModel> = listData.cabinList,

    val startDestination: String = Routes.LOGIN,
    val deskHistoryList: List<DeskHistoryModel> = DeskHistoryList.getList,
    val cabinHistoryList: List<MeetingRoomHistoryResponse> = CabinHistoryList.getList
)


@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return currentDate.format(formatter)
}