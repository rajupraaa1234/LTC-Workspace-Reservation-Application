package com.example.ltcworkspacereservationapplication.presentation.mvvm

import androidx.lifecycle.ViewModel
import com.example.ltcworkspacereservationapplication.presentation.state.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class ReservationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppState())
    val uiState: StateFlow<AppState>
        get() = _uiState

    fun sendIntent(intent: AppIntent) {
        when (intent) {
            is AppIntent.OnDatePickerClick -> onDatePickerClicked(intent.dateSelected)
        }
    }

    private fun onDatePickerClicked(dateSelected: String) {
        _uiState.update { it.copy(selectedDate = dateSelected) }
    }

}