package com.example.ltcworkspacereservationapplication.domain.repository.HistoryRepository

import com.example.ltcworkspacereservationapplication.domain.model.History.DeskHistoryModel
import com.example.ltcworkspacereservationapplication.domain.model.History.GetDeskHistoryRequest

interface HistoryRepository {
    suspend fun getDeskHistory(employeeId : String) : List<DeskHistoryModel>

    suspend fun getMeetingHistory(employeeId : String) : List<DeskHistoryModel>
}