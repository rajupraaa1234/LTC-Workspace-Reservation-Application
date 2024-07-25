package com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Request

data class BookMeetingRoomRequest(
    val empId: Int,
    val empName: String,
    val roomNumber: Int,
    val floorNumber: Int,
    val date: String,
    val startTime: String,
    val endTime: String,
    val reservationStatus: String
)
