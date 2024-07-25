package com.example.ltcworkspacereservationapplication.domain.usecase.DeskReservationUsecase

import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request.DeskReservationRequest
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskReservationResponse
import com.example.ltcworkspacereservationapplication.domain.repository.DeskReservation.DeskReservationRepository
import javax.inject.Inject

class BookDeskUseCase @Inject constructor(private val repository: DeskReservationRepository) {
    suspend operator fun invoke(request: DeskReservationRequest): DeskReservationResponse {
        return repository.bookDesk(request)
    }
}