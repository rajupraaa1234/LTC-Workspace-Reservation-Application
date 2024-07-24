package com.example.ltcworkspacereservationapplication.domain.usecase.HistoryUseCase

import com.example.ltcworkspacereservationapplication.domain.model.History.MeetingHistory.MeetingRoomHistoryResponse
import com.example.ltcworkspacereservationapplication.domain.repository.HistoryRepository.HistoryRepository


class MeetingHistoryUseCase(private val repository: HistoryRepository) {
    suspend operator fun invoke(employeeId : Int): List<MeetingRoomHistoryResponse> {
        return repository.getMeetingHistory(employeeId)
    }
}