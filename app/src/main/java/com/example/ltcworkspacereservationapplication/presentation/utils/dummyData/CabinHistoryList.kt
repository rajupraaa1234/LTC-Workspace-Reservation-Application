package com.example.ltcworkspacereservationapplication.presentation.utils.dummyData

import com.example.ltcworkspacereservationapplication.domain.model.History.CabinHistoryModel
import com.example.ltcworkspacereservationapplication.domain.model.History.DeskHistoryModel
import com.example.ltcworkspacereservationapplication.domain.model.History.MeetingRoomHistoryResponse

object CabinHistoryList {
    val getList: List<MeetingRoomHistoryResponse> = listOf(
        MeetingRoomHistoryResponse(
            10,
            5605305,
            "Raju Kumar",
            12,
            10,
            10,
            "2024-07-27",
            "08:30:00",
            "10:00:00",
            "Booked"
        ),
        MeetingRoomHistoryResponse(
            11,
            5605305,
            "Raju Kumar",
            13,
            10,
            10,
            "2024-08-27",
            "08:30:00",
            "10:00:00",
            "Booked"
        ),
        MeetingRoomHistoryResponse(
            12,
            5605305,
            "Raju Kumar",
            13,
            10,
            10,
            "2024-08-29",
            "09:30:00",
            "10:00:00",
            "Booked"
        ),
        MeetingRoomHistoryResponse(
            14,
            5605305,
            "Raju Kumar",
            14,
            5,
            5,
            "2024-08-29",
            "09:30:00",
            "10:00:00",
            "Reserved"
        ),
        MeetingRoomHistoryResponse(
            15,
            5605305,
            "Raju Kumar",
            15,
            6,
            5,
            "2024-08-30",
            "09:30:00",
            "10:00:00",
            "Reserved"
        ),
    )
}
