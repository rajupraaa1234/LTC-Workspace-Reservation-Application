package com.example.ltcworkspacereservationapplication.domain.usecase.DeskReservationUsecase

import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.network.ApiService
import javax.inject.Inject

class GetDeskListUseCase @Inject constructor(private val repository : ApiService) {
    suspend operator fun invoke(date : String) : List<DeskItemModel>{
        return repository.getAllDesk(date)
    }
//    suspend operator fun invoke(): Any {
//        return repository.getData()
//    }
}