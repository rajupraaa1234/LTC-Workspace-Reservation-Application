package com.example.ltcworkspacereservationapplication.domain.usecase.DeskReservationUsecase

import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskResponseItemModel
import com.example.ltcworkspacereservationapplication.network.ApiService
import javax.inject.Inject

class GetDeskListUseCase @Inject constructor(private val repository : ApiService) {
    suspend operator fun invoke(date : String) : List<DeskResponseItemModel>{
        return repository.getAllDesk(date)
    }
}