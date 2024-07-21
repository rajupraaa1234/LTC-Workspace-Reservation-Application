package com.example.ltcworkspacereservationapplication.presentation.utils.dummyData

import com.example.ltcworkspacereservationapplication.R
import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel

object listData {
    val deskList: List<DeskItemModel> = listOf(
       DeskItemModel("10-01","reserved"),
        DeskItemModel("10-03","reserved"),
        DeskItemModel("10-02","reserved"),
        DeskItemModel("10-04","reserved"),
        DeskItemModel("10-05","avail"),
        DeskItemModel("10-06","avail"),
        DeskItemModel("10-07","reserved"),
        DeskItemModel("10-07","avail"),
        DeskItemModel("10-07","reserved"),
        DeskItemModel("10-07","avail"),
        DeskItemModel("10-07","reserved"),
        DeskItemModel("10-07","avail"),
        DeskItemModel("10-07","reserved"),
        DeskItemModel("10-07","avail"),
        DeskItemModel("10-07","reserved"),
        DeskItemModel("10-07","avail"),
        DeskItemModel("10-07","reserved"),
        DeskItemModel("10-07","avail")
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
