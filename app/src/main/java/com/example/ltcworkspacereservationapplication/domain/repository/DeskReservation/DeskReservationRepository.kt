package com.example.ltcworkspacereservationapplication.domain.repository.DeskReservation

import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request.DeskReservationRequest
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskReservationResponse

interface DeskReservationRepository {
    suspend fun getDeskList(date: String) : List<DeskItemModel>
    suspend fun bookDesk(deskReservationRequest: DeskReservationRequest) : DeskReservationResponse
}