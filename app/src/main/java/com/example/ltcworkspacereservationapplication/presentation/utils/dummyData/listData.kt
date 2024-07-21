package com.example.ltcworkspacereservationapplication.presentation.utils.dummyData

import com.example.ltcworkspacereservationapplication.R
import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel

object listData {
    val deskList: List<DeskItemModel> = listOf(
       DeskItemModel("10-01","reserved",R.drawable.reserveddesk),
        DeskItemModel("10-03","reserved",R.drawable.reserveddesk),
        DeskItemModel("10-02","reserved",R.drawable.reserveddesk),
        DeskItemModel("10-04","reserved",R.drawable.reserveddesk),
        DeskItemModel("10-05","avail",R.drawable.reserveddesk),
        DeskItemModel("10-06","avail",R.drawable.availabledesk),
        DeskItemModel("10-07","reserved",R.drawable.availabledesk),
        DeskItemModel("10-07","avail",R.drawable.availabledesk),
        DeskItemModel("10-07","reserved",R.drawable.availabledesk),
        DeskItemModel("10-07","avail",R.drawable.reserveddesk),
        DeskItemModel("10-07","reserved",R.drawable.reserveddesk),
        DeskItemModel("10-07","avail",R.drawable.reserveddesk),
        DeskItemModel("10-07","reserved",R.drawable.reserveddesk),
        DeskItemModel("10-07","avail",R.drawable.reserveddesk),
        DeskItemModel("10-07","reserved",R.drawable.reserveddesk),
        DeskItemModel("10-07","avail",R.drawable.reserveddesk),
        DeskItemModel("10-07","reserved",R.drawable.reserveddesk),
        DeskItemModel("10-07","avail",R.drawable.reserveddesk)
    )


    val cabinList: List<MeetingItemModel> = listOf(
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10),
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10),
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10),
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10),
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10),
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10),
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10),
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10),
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10),
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10),
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10),
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10),
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10),
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10),
        MeetingItemModel("10-01", listOf("10:00-11:00","11:00-12:00","1:00-2:00"),10)
    )
}
