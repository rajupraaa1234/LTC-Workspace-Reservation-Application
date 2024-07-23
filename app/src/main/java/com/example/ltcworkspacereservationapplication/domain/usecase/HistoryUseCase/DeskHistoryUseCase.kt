package com.example.ltcworkspacereservationapplication.domain.usecase.HistoryUseCase

import com.example.ltcworkspacereservationapplication.domain.model.History.DeskHistoryModel
import com.example.ltcworkspacereservationapplication.domain.model.History.GetDeskHistoryRequest
import com.example.ltcworkspacereservationapplication.domain.repository.HistoryRepository.HistoryRepository

class DeskHistoryUseCase(private val repository: HistoryRepository,private val request : GetDeskHistoryRequest) {
    suspend operator fun invoke(): List<DeskHistoryModel> {
        return repository.getDeskHistory(request)
    }
}