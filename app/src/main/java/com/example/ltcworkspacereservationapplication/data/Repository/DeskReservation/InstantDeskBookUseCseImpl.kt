package com.example.ltcworkspacereservationapplication.data.Repository.DeskReservation

import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request.InstantBookingRequest
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.InstantBookingResponse
import com.example.ltcworkspacereservationapplication.domain.repository.DeskReservation.InstantDeskBookingRepository
import com.example.ltcworkspacereservationapplication.network.ApiService

class InstantDeskBookUseCseImpl(private val apiService: ApiService) :InstantDeskBookingRepository {
    override suspend fun bookInstantDesk(instantBookingRequest: InstantBookingRequest): InstantBookingResponse {
        return apiService.bookInstantDesk(instantBookingRequest)
    }

}