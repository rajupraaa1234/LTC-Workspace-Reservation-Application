package com.example.ltcworkspacereservationapplication.data.Repository.History

import com.example.ltcworkspacereservationapplication.domain.model.History.MeetingHistory.MeetingRoomHistoryResponse
import com.example.ltcworkspacereservationapplication.domain.repository.HistoryRepository.MeetingHistoryRepository
import com.example.ltcworkspacereservationapplication.network.MeetingApiService

class MeetingHistoryUseCaseImpl(private val meetingApiService: MeetingApiService) : MeetingHistoryRepository{
    override suspend fun getMeetingHistory(employeeId: Int): List<MeetingRoomHistoryResponse> {
        return meetingApiService.getAllMeetingHistory(employeeId)
    }
}