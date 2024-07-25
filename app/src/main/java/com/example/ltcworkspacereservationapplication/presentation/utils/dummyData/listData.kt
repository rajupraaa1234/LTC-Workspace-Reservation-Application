package com.example.ltcworkspacereservationapplication.presentation.utils.dummyData

import com.example.ltcworkspacereservationapplication.R
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskResponseItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel

object listData {
    val deskList: List<DeskResponseItemModel> = listOf(

        DeskResponseItemModel(155, 1, 5, "Booked", R.drawable.deskbooked),
        DeskResponseItemModel(156, 2, 5, "Reserved", R.drawable.reserveddesk),
        DeskResponseItemModel(110, 3, 5, "Booked", R.drawable.deskbooked),
        DeskResponseItemModel(157, 4, 10, "Reserved", R.drawable.reserveddesk),

        DeskResponseItemModel(158, 6, 5, "Available", R.drawable.availabledesk),
        DeskResponseItemModel(159, 2, 5, "Available", R.drawable.availabledesk),
        DeskResponseItemModel(160, 10, 5, "Booked", R.drawable.deskbooked),
        DeskResponseItemModel(162, 11, 10, "Reserved", R.drawable.reserveddesk),


        DeskResponseItemModel(1581, 16, 5, "Available", R.drawable.availabledesk),
        DeskResponseItemModel(1591, 21, 5, "Available", R.drawable.availabledesk),
        DeskResponseItemModel(1602, 12, 10, "Booked", R.drawable.deskbooked),
        DeskResponseItemModel(1601, 13, 10, "Reserved", R.drawable.reserveddesk),

        DeskResponseItemModel(15811, 16, 5, "Available", R.drawable.availabledesk),
        DeskResponseItemModel(1599, 21, 5, "Available", R.drawable.availabledesk),
        DeskResponseItemModel(1612, 12, 10, "Booked", R.drawable.deskbooked),
        DeskResponseItemModel(1611, 13, 10, "Reserved", R.drawable.reserveddesk),

        )


    val cabinList: List<MeetingItemModel> = listOf(
        MeetingItemModel(
            12345,
            10,
            1,
            listOf("10:00:00-11:00:00", "11:00:00-12:00:00", "01:00:00-02:00:00"),
            10,
            R.drawable.reservedcabin
        ),
        MeetingItemModel(
            12346,
            10,
            2,
            listOf("10:00:00-11:00:00", "11:00:00-12:00:00", "01:00:00-02:00:00"),
            5,
            R.drawable.reservedcabin
        ),
        MeetingItemModel(
            12347,
            5,
            3,
            listOf("10:00:00-11:00:00", "11:00:00-12:00:00", "01:00:00-02:00:00"),
            10,
            R.drawable.reservedcabin
        ),

        MeetingItemModel(123471, 5, 3, listOf(), 10, R.drawable.availablecabin),

        MeetingItemModel(12348, 6, 4, listOf(), 5, R.drawable.availablecabin),

        MeetingItemModel(
            12348,
            6,
            4,
            listOf("10:00:00-11:00:00", "11:00:00-12:00:00", "01:00:00-02:00:00"),
            5,
            R.drawable.availablecabin
        ),
        MeetingItemModel(
            12349,
            10,
            4,
            listOf("10:00:00-11:00:00", "11:00:00-12:00:00", "01:00:00-02:00:00"),
            5,
            R.drawable.availablecabin
        ),

        MeetingItemModel(12380, 10, 4, listOf(), 5, R.drawable.availablecabin),

        MeetingItemModel(12381, 5, 4, listOf(), 5, R.drawable.availablecabin),


        MeetingItemModel(12382, 5, 4, listOf(), 5, R.drawable.availablecabin),


        MeetingItemModel(
            1230,
            5,
            3,
            listOf("10:00:00-11:00:00", "11:00:00-12:00:00", "01:00:00-02:00:00"),
            10,
            R.drawable.reservedcabin
        ),

        MeetingItemModel(1231, 5, 3, listOf(), 10, R.drawable.availablecabin),

        MeetingItemModel(12312, 6, 4, listOf(), 5, R.drawable.availablecabin),


        )
}
