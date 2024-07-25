package com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request

data class InstantBookingRequest(
    val employId: Int,
    val date: String,
    val seatNumber: Int,
    val floorNumber: Int,
    val reservationStatus : String
)