package com.example.ltcworkspacereservationapplication.presentation.mvvm

import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel

sealed interface AppIntent {
    data class OnDatePickerClick(val dateSelected : String) : AppIntent
    data class OnFloorSelect(val selectedFloor : Int) : AppIntent
    data object OnFilterButtonClicked : AppIntent
    data class OnMeetingItemClick(val item : MeetingItemModel) : AppIntent
    data class OnDeskItemClick(val item : DeskItemModel) : AppIntent
    data class OnMeetingRoomBooking(val startTime : String,val endTime : String,val capacity : String,val meetingId : String) : AppIntent
    data class onLoginClick(val employeeId: String) : AppIntent
}