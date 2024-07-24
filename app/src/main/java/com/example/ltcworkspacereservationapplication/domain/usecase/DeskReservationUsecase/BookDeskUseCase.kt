package com.example.ltcworkspacereservationapplication.domain.usecase.DeskReservationUsecase

import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request.DeskReservationRequest
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskReservationResponse
import com.example.ltcworkspacereservationapplication.domain.repository.DeskReservation.DeskReservationRepository

class BookDeskUseCase(
    private val repository: DeskReservationRepository,
) {
    suspend operator fun invoke(request: DeskReservationRequest): DeskReservationResponse {
        return repository.bookDesk(request)
    }
}