package com.example.ltcworkspacereservationapplication.data.Repository.MeetingReservation

import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Request.BookMeetingRoomRequest
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.BookMeetingRoomResponse
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.GetMeetingItemResponse
import com.example.ltcworkspacereservationapplication.domain.repository.MeetingRoomRepository.MeetingRoomReservationRepository
import com.example.ltcworkspacereservationapplication.network.ApiService

class MeetingUseCaseImpl(private val apiService: ApiService) : MeetingRoomReservationRepository {
    override suspend fun bookMeetingRoom(request: BookMeetingRoomRequest): BookMeetingRoomResponse {
        return apiService.bookMeetingRoom(request)
    }

    override suspend fun getMeetingRoom(date : String): GetMeetingItemResponse {
        return apiService.getAllMeetingRooms(date)
    }
}