package com.example.ltcworkspacereservationapplication.network

import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskResponseItemModel
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request.DeskReservationRequest
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request.InstantBookingRequest
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskReservationResponse
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.InstantBookingResponse
import com.example.ltcworkspacereservationapplication.domain.model.History.DeskHistoryModel
import com.example.ltcworkspacereservationapplication.domain.model.History.MeetingHistory.MeetingRoomHistoryResponse
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Request.BookMeetingRoomRequest
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.BookMeetingRoomResponse
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.GetMeetingItemResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {


    // Desk Related Api
    @GET("desk/total-seat/")
    suspend fun getAllDesk(@Query("date") date: String): List<DeskResponseItemModel>

    @POST("desk/seat-booking")
    suspend fun bookDesk(@Body body : DeskReservationRequest): DeskReservationResponse


    @GET("desk/deskHistory")
    suspend fun getAllDeskHistory(@Query("employId") employId:Int): List<DeskHistoryModel>

    @PUT("desk/confirm-seat")
    suspend fun bookInstantDesk(@Body body: InstantBookingRequest) : InstantBookingResponse


}