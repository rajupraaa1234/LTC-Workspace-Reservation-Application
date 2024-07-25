package com.example.ltcworkspacereservationapplication.domain.model

enum class AvailabilityType(
    val type : String
){
    RESERVED("Reserved"),
    BOOKED("Booked"),
    AVAILABLE("Available")
}