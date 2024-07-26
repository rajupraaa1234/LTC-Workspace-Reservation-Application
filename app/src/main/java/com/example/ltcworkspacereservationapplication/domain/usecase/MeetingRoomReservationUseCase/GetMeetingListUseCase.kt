package com.example.ltcworkspacereservationapplication.domain.usecase.MeetingRoomReservationUseCase

import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.GetMeetingItemResponse
import com.example.ltcworkspacereservationapplication.domain.repository.MeetingRoomRepository.MeetingRoomReservationRepository
import javax.inject.Inject

class GetMeetingListUseCase @Inject constructor(private val repository : MeetingRoomReservationRepository) {
    suspend operator fun invoke(date : String): GetMeetingItemResponse {
       return repository.getMeetingRoom(date)
    }
}