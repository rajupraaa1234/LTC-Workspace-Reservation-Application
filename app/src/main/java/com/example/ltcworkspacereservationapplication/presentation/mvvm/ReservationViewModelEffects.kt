package com.example.ltcworkspacereservationapplication.presentation.mvvm

import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel

sealed interface ReservationViewModelEffects {
    sealed interface Composable : ReservationViewModelEffects{
        data class SelectedFloor(val list : List<DeskItemModel>) : Composable
        data object deskListUpdated : Composable
        data object meetingListUpdated : Composable
        data class MeetingRoomListByFilter(val list : List<MeetingItemModel>) : Composable
    }
}