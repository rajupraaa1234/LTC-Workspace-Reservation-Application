package com.example.ltcworkspacereservationapplication.domain.model.History


data class DeskHistoryModel(
    val bookingId: Int,
    val employId: String,
    val name: String,
    val date: String,
    val seatNumber: Int,
    val floorNumber: Int,
    val reservationStatus: String
)