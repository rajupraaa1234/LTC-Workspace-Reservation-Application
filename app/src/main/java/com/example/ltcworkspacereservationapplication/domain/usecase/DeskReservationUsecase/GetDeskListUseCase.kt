package com.example.ltcworkspacereservationapplication.domain.usecase.DeskReservationUsecase

import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request.GetDeskReservationRequest
import com.example.ltcworkspacereservationapplication.domain.repository.DeskReservation.DeskReservationRepository

class GetDeskListUseCase(private val repository : DeskReservationRepository,private val request : GetDeskReservationRequest) {
    suspend operator fun invoke() : List<DeskItemModel>{
        return repository.getDeskList(request)
    }
}