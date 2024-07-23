package com.example.ltcworkspacereservationapplication.domain.repository.HistoryRepository

import com.example.ltcworkspacereservationapplication.domain.model.History.DeskHistoryModel
import com.example.ltcworkspacereservationapplication.domain.model.History.GetDeskHistoryRequest

interface HistoryRepository {
    suspend fun getDeskHistory(request : GetDeskHistoryRequest) : List<DeskHistoryModel>
}