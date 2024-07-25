package com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response

data class ReservedTimeSlot(
    val startTime: String,
    val endTime: String
)

data class BookedRoomInfo(
    val floorNumber: Int,
    val roomNumber: Int,
    val date: String,
    val reservedTimeSlots: List<ReservedTimeSlot>
)

data class RoomInfo(
    val roomNumber: Int,
    val floorNumber: Int,
    val capacity: Int
)

data class GetMeetingItemResponse(
    val bookedRoomInfoList: List<BookedRoomInfo>,
    val roomInfo: List<RoomInfo>
)