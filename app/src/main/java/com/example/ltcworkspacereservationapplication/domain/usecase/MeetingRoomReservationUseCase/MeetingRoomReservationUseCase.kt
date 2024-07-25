package com.example.ltcworkspacereservationapplication.domain.usecase.MeetingRoomReservationUseCase

import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Request.BookMeetingRoomRequest
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.BookMeetingRoomResponse
import com.example.ltcworkspacereservationapplication.domain.repository.MeetingRoomRepository.MeetingRoomReservationRepository
import javax.inject.Inject

class MeetingRoomReservationUseCase @Inject constructor(private val repository: MeetingRoomReservationRepository) {
    suspend operator fun invoke(request: BookMeetingRoomRequest): BookMeetingRoomResponse {
        return repository.bookMeetingRoom(request)
    }
}