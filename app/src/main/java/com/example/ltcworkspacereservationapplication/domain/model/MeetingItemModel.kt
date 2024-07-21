package com.example.ltcworkspacereservationapplication.domain.model

data class MeetingItemModel (
    val meetingRoomId: String,
    val reservedSlot : List<String>,
    val capacity : Int,
    val imageId : Int
)

