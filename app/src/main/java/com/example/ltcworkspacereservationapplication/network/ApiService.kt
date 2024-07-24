package com.example.ltcworkspacereservationapplication.network

import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request.DeskReservationRequest
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskReservationResponse
import com.example.ltcworkspacereservationapplication.domain.model.History.DeskHistoryModel
import com.example.ltcworkspacereservationapplication.domain.model.History.MeetingHistory.MeetingRoomHistoryResponse
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    // Desk Related Api

    @GET("total-seat/{date}")
    suspend fun getAllDesk(@Query("date") date: String): List<DeskItemModel>

    @POST("seat-booking")
    suspend fun bookDesk(@Body body : DeskReservationRequest): DeskReservationResponse


    @GET("deskHistory/{employeeId}")
    suspend fun getAllDeskHistory(@Query("employeeId") employeeId:Int): List<DeskHistoryModel>

    @GET("roomHistory/{employeeId}")
    suspend fun getAllMeetingHistory(@Query("employeeId") employeeId:Int): List<MeetingRoomHistoryResponse>




//
//    @GET("meetingRooms/available")
//    suspend fun getAvailableMeetingRooms(): List<MeetingItemModel>
//
//    @POST("meetingRooms/book")
//    suspend fun bookMeetingRoom(@Body meetingRoomId: Int): MeetingItemModel






}