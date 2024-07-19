package com.example.ltcworkspacereservationapplication.presentation.mvvm

import androidx.lifecycle.ViewModel
import com.example.ltcworkspacereservationapplication.presentation.state.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class ReservationViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(AppState())
    val uiState: StateFlow<AppState>
        get() = _uiState



}