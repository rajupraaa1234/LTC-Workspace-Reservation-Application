package com.example.ltcworkspacereservationapplication.data.Repository.DeskReservation

import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskResponseItemModel
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request.DeskReservationRequest
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskReservationResponse
import com.example.ltcworkspacereservationapplication.domain.repository.DeskReservation.DeskReservationRepository
import com.example.ltcworkspacereservationapplication.network.ApiService

class DeskUseCaseImpl(private val apiService: ApiService) : DeskReservationRepository {
    override suspend fun getDeskList(date : String): List<DeskResponseItemModel> {
       return apiService.getAllDesk(date)
    }

    override suspend fun bookDesk(deskReservationRequest: DeskReservationRequest): DeskReservationResponse {
        return apiService.bookDesk(deskReservationRequest)
    }
}