package com.example.ltcworkspacereservationapplication.domain.model

data class MeetingItemModel (
    val meetingRoomId: Int,
    val floor:Int,
    val meetingRoomNumber:Int,
    val reservedSlot : List<String>,
    val capacity : Int,
    val imageId : Int
)

