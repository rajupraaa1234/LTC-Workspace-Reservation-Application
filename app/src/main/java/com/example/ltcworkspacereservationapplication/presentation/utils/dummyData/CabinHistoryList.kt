package com.example.ltcworkspacereservationapplication.presentation.utils.dummyData

import com.example.ltcworkspacereservationapplication.domain.model.History.MeetingHistory.MeetingRoomHistoryResponse

object CabinHistoryList {
    val getList: List<MeetingRoomHistoryResponse> = listOf(
        MeetingRoomHistoryResponse(
            10,
            5605305,
            "Raju Kumar",
            12,
            10,
            "2024-07-27",
            "08:30:00",
            "10:00:00",
        ),
        MeetingRoomHistoryResponse(
            11,
            5605305,
            "Raju Kumar",
            13,
            10,
            "2024-08-27",
            "08:30:00",
            "10:00:00",
        ),
        MeetingRoomHistoryResponse(
            12,
            5605305,
            "Raju Kumar",
            13,
            10,
            "2024-08-29",
            "09:30:00",
            "10:00:00",
        ),
        MeetingRoomHistoryResponse(
            14,
            5605305,
            "Raju Kumar",
            14,
            5,
            "2024-08-29",
            "09:30:00",
            "10:00:00",
        ),
        MeetingRoomHistoryResponse(
            15,
            5605305,
            "Raju Kumar",
            15,
            6,
            "2024-08-30",
            "09:30:00",
            "10:00:00",
        ),
    )
}
