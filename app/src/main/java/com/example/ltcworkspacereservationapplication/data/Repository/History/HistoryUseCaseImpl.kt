package com.example.ltcworkspacereservationapplication.data.Repository.History

import com.example.ltcworkspacereservationapplication.domain.model.History.DeskHistoryModel
import com.example.ltcworkspacereservationapplication.domain.model.History.MeetingHistory.MeetingRoomHistoryResponse
import com.example.ltcworkspacereservationapplication.domain.repository.HistoryRepository.HistoryRepository
import com.example.ltcworkspacereservationapplication.network.ApiService

class HistoryUseCaseImpl(private val apiService: ApiService) : HistoryRepository {
    override suspend fun getDeskHistory(employeeId: Int): List<DeskHistoryModel> {
        return apiService.getAllDeskHistory(employeeId)
    }

    override suspend fun getMeetingHistory(employeeId: Int): List<MeetingRoomHistoryResponse> {
        return apiService.getAllMeetingHistory(employeeId)
    }
}