package com.example.ltcworkspacereservationapplication.presentation.mvvm

import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskResponseItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel

sealed interface ReservationViewModelEffects {
    sealed interface Composable : ReservationViewModelEffects{
        data class SelectedFloor(val list : List<DeskResponseItemModel>) : Composable
        data object deskListUpdated : Composable
        data object meetingListUpdated : Composable
        data class MeetingRoomListByFilter(val list : List<MeetingItemModel>) : Composable
    }
}