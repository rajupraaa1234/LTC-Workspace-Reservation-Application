package com.example.ltcworkspacereservationapplication.presentation.mvvm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ltcworkspacereservationapplication.presentation.state.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class ReservationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppState())
    val uiState: StateFlow<AppState>
        get() = _uiState

    val TAG =  "ReservationViewModel"

    fun sendIntent(intent: AppIntent) {
        when (intent) {
            AppIntent.OnFilterButtonClicked -> onFilterButtonClicked()
            is AppIntent.OnDatePickerClick -> onDatePickerClicked(intent.dateSelected)
            is AppIntent.onFloorSelect -> onFloorSelected(intent.selectedFloor)
        }
    }

    private fun onFloorSelected(selectedFloor: Int) {
        //TODO
    }

    private fun onFilterButtonClicked() {
        TODO("Not yet implemented")
    }

    private fun onDatePickerClicked(dateSelected: String) {
        _uiState.update { it.copy(selectedDate = dateSelected) }
    }

}