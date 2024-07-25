package com.example.ltcworkspacereservationapplication.presentation.mvvm

import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel

sealed interface AppIntent {
    data class OnDatePickerClick(val dateSelected: String) : AppIntent
    data class OnFloorSelect(val selectedFloor: Int) : AppIntent
    data object OnFilterButtonClicked : AppIntent
    data class OnMeetingItemClick(val item: MeetingItemModel, val index: Int) : AppIntent
    data class OnDeskItemClick(val item: DeskItemModel, val index: Int) : AppIntent
    data class OnMeetingRoomBooking(
        val startTime: String,
        val endTime: String,
        val capacity: String,
        val meetingId: String
    ) : AppIntent

    data class onLoginClick(val employeeId: String) : AppIntent
    data object OnDeskBookButtonClicked : AppIntent
    data class OnDeskListFilter(val listItem : List<DeskItemModel>) : AppIntent

    data class OnMeetingListFilterUpdate(val listItem : List<MeetingItemModel>) : AppIntent
    data class OnQRCodeScanned(val seatId : String) :AppIntent
}