package com.example.ltcworkspacereservationapplication.domain.usecase.DeskReservationUsecase

import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request.InstantBookingRequest
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.InstantBookingResponse
import com.example.ltcworkspacereservationapplication.domain.repository.DeskReservation.InstantDeskBookingRepository
import javax.inject.Inject

class InstantDeskBookUseCase @Inject constructor(private val repository: InstantDeskBookingRepository) {
    suspend operator fun invoke(request: InstantBookingRequest) : InstantBookingResponse {
        return repository.bookInstantDesk(request)
    }
}