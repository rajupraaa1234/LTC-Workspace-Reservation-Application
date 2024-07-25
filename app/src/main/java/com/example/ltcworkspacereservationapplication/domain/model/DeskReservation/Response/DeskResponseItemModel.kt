package com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response

data class DeskResponseItemModel(
    val seatId : Int,
    val seatNumber : Int,
    val floorNumber : Int,
    val reservationStatus : String,
    var imageId : Int
)
