package com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request

data class DeskReservationRequest(
    val employId: Int,
    val name: String,
    val date: String,
    val seatNumber: Int,
    val floorNumber: Int,
    val reservationStatus: String
)