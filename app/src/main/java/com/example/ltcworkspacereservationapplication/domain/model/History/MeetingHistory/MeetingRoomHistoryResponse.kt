package com.example.ltcworkspacereservationapplication.domain.model.History.MeetingHistory

data class MeetingRoomHistoryResponse(
    val bookingId: Int,
    val empId: Int,
    val empName: String,
    val roomNumber: Int,
    val floorNumber: Int,
    val date: String,
    val startTime: String,
    val endTime: String,
    val reservationStatus: String
)