package com.example.ltcworkspacereservationapplication.data.Repository.MeetingReservation

import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Request.BookMeetingRoomRequest
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.BookMeetingRoomResponse
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.GetMeetingItemResponse
import com.example.ltcworkspacereservationapplication.domain.repository.MeetingRoomRepository.MeetingRoomReservationRepository
import com.example.ltcworkspacereservationapplication.network.ApiService
import com.example.ltcworkspacereservationapplication.network.MeetingApiService

class MeetingUseCaseImpl(private val meetingApiService: MeetingApiService) : MeetingRoomReservationRepository {
    override suspend fun bookMeetingRoom(request: BookMeetingRoomRequest): BookMeetingRoomResponse {
        return meetingApiService.bookMeetingRoom(request)
    }

    override suspend fun getMeetingRoom(date : String): GetMeetingItemResponse {
        return meetingApiService.getAllMeetingRooms(date)
    }
}