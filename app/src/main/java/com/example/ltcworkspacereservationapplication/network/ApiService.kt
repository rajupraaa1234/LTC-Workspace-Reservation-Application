package com.example.ltcworkspacereservationapplication.network

import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("seats/available")
    suspend fun getAvailableSeats(): List<DeskItemModel>

    @POST("seats/book")
    suspend fun bookSeat(@Body seatId: Int): DeskItemModel

    @GET("meetingRooms/available")
    suspend fun getAvailableMeetingRooms(): List<MeetingItemModel>

    @POST("meetingRooms/book")
    suspend fun bookMeetingRoom(@Body meetingRoomId: Int): MeetingItemModel

}