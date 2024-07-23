package com.example.ltcworkspacereservationapplication.presentation.mvvm

import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel

sealed interface ReservationViewModelEffects {
    sealed interface Composable : ReservationViewModelEffects{
        data class SelectedFloor(val list : List<DeskItemModel>) : Composable
        data object deskListUpdated : Composable
    }
}