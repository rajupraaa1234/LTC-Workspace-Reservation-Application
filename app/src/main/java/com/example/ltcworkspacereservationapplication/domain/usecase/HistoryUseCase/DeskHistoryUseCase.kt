package com.example.ltcworkspacereservationapplication.domain.usecase.HistoryUseCase

import com.example.ltcworkspacereservationapplication.domain.model.History.DeskHistoryModel
import com.example.ltcworkspacereservationapplication.domain.repository.HistoryRepository.HistoryRepository

class DeskHistoryUseCase(private val repository: HistoryRepository) {
    suspend operator fun invoke(employeeId : Int): List<DeskHistoryModel> {
        return repository.getDeskHistory(employeeId)
    }
}