package com.example.ltcworkspacereservationapplication.presentation.mvvm

sealed interface AppIntent {
    data class OnDatePickerClick(val dateSelected : String) : AppIntent
}