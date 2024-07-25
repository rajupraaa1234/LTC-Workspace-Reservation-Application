package com.example.ltcworkspacereservationapplication.network

import com.example.ltcworkspacereservationapplication.domain.model.History.MeetingHistory.MeetingRoomHistoryResponse
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Request.BookMeetingRoomRequest
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.BookMeetingRoomResponse
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.GetMeetingItemResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MeetingApiService {

    // Meeting Related Api

    @POST("room/create-booking")
    suspend fun bookMeetingRoom(@Body body : BookMeetingRoomRequest): BookMeetingRoomResponse

    @GET("room/bookings")
    suspend fun getAllMeetingHistory(@Query("empId") empId:Int): List<MeetingRoomHistoryResponse>

    @GET("/room/bookings/{date}")
    suspend fun getAllMeetingRooms(@Query("date") date : String) : GetMeetingItemResponse


    // Dummy Api for Testing Api Services Layer
    @GET("movies")
    suspend fun getData() : Any

}