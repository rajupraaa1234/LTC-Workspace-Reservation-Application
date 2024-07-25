package com.example.ltcworkspacereservationapplication.domain.repository.MeetingRoomRepository

import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Request.BookMeetingRoomRequest
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.BookMeetingRoomResponse
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.GetMeetingItemResponse

interface MeetingRoomReservationRepository {
    suspend fun bookMeetingRoom(request : BookMeetingRoomRequest) : BookMeetingRoomResponse
    suspend fun getMeetingRoom(date : String) : GetMeetingItemResponse
}