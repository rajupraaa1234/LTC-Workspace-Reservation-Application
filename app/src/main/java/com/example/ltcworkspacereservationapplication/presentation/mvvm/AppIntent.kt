package com.example.ltcworkspacereservationapplication.presentation.mvvm

sealed interface AppIntent {
    data class OnDatePickerClick(val dateSelected : String) : AppIntent
    data class onFloorSelect(val selectedFloor : Int) : AppIntent
    data object OnFilterButtonClicked : AppIntent
    data class onLoginClick(val employeeId: String) : AppIntent
}