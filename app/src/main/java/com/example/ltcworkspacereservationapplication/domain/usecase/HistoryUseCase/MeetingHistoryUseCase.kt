package com.example.ltcworkspacereservationapplication.domain.usecase.HistoryUseCase

import com.example.ltcworkspacereservationapplication.domain.model.History.DeskHistoryModel
import com.example.ltcworkspacereservationapplication.domain.repository.HistoryRepository.HistoryRepository


class MeetingHistoryUseCase(private val repository: HistoryRepository) {
    suspend operator fun invoke(employeeId : String): List<DeskHistoryModel> {
        return repository.getDeskHistory(employeeId)
    }
}