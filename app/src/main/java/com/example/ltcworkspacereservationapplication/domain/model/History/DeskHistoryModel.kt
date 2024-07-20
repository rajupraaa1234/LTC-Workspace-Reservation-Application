package com.example.ltcworkspacereservationapplication.domain.model.History


data class DeskHistoryModel(
    val deskId: String,
    val date: String,
    val floor : Int,
    val status : String
)