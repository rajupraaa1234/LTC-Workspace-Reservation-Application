package com.example.ltcworkspacereservationapplication.network

import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request.DeskReservationRequest
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskReservationResponse
import com.example.ltcworkspacereservationapplication.domain.model.History.DeskHistoryModel
import com.example.ltcworkspacereservationapplication.domain.model.History.MeetingHistory.MeetingRoomHistoryResponse
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Request.BookMeetingRoomRequest
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.BookMeetingRoomResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    // Desk Related Api
    @GET("desk/total-seat/{date}")
    suspend fun getAllDesk(@Query("date") date: String): List<DeskItemModel>

    @POST("desk/seat-booking")
    suspend fun bookDesk(@Body body : DeskReservationRequest): DeskReservationResponse


    @GET("/deskdeskHistory/{employeeId}")
    suspend fun getAllDeskHistory(@Query("employeeId") employeeId:Int): List<DeskHistoryModel>

    @GET("room/roomHistory/{employeeId}")
    suspend fun getAllMeetingHistory(@Query("employeeId") employeeId:Int): List<MeetingRoomHistoryResponse>


    // Meeting Related Api

    @POST("room/create-booking")
    suspend fun bookMeetingRoom(@Body body : BookMeetingRoomRequest): BookMeetingRoomResponse


    // Dummy Api for Testing Api Services Layer
    @GET("movies")
    suspend fun getData() : Any

}