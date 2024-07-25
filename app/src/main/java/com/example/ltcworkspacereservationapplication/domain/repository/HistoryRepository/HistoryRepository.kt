package com.example.ltcworkspacereservationapplication.domain.repository.HistoryRepository

import com.example.ltcworkspacereservationapplication.domain.model.History.DeskHistoryModel
import com.example.ltcworkspacereservationapplication.domain.model.History.MeetingHistory.MeetingRoomHistoryResponse

interface HistoryRepository {
    suspend fun getDeskHistory(employeeId : Int) : List<DeskHistoryModel>
    suspend fun getMeetingHistory(employeeId : Int) : List<MeetingRoomHistoryResponse>
}