package com.example.ltcworkspacereservationapplication.domain.model.History

data class CabinHistoryModel(
    val meetingRoomId: String,
    val date: String,
    val status: String,
    val time: String,
    val floor: Int
)