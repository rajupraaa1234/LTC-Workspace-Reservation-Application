package com.example.ltcworkspacereservationapplication.domain.model

data class DeskItemModel(
    val seatId : Int,
    val seatNumber : Int,
    val floorNumber : Int,
    val reservationStatus : String,
    var imageId : Int
)
