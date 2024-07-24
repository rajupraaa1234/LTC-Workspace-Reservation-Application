package com.example.ltcworkspacereservationapplication.domain.repository.HistoryRepository

import com.example.ltcworkspacereservationapplication.domain.model.History.DeskHistoryModel

interface HistoryRepository {
    suspend fun getDeskHistory(employeeId : Int) : List<DeskHistoryModel>

    suspend fun getMeetingHistory(employeeId : Int) : List<DeskHistoryModel>
}