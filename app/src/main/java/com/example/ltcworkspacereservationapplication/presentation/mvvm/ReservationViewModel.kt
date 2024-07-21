package com.example.ltcworkspacereservationapplication.presentation.mvvm

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel
import com.example.ltcworkspacereservationapplication.presentation.state.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.log


class ReservationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppState())

    val uiState: StateFlow<AppState>
        get() = _uiState

    val TAG =  "ReservationViewModel"


    fun sendIntent(intent: AppIntent) {
        when (intent) {
            AppIntent.OnFilterButtonClicked -> onFilterButtonClicked()
            is AppIntent.OnDatePickerClick -> onDatePickerClicked(intent.dateSelected)
            is AppIntent.OnFloorSelect -> onFloorSelected(intent.selectedFloor)
            is AppIntent.OnMeetingItemClick -> OnMeetingItemClicked(intent.item)
            is AppIntent.OnDeskItemClick -> onDeskItemClicked(intent.item)
            is AppIntent.OnMeetingRoomBooking -> onMeetingBooking(intent.startTime,intent.endTime,intent.capacity,intent.meetingId)
            is AppIntent.onLoginClick -> onLoginClicked(intent.employeeId)
        }
    }

    private fun onMeetingBooking(startTime: String, endTime: String, capacity: String, meetingId: String) {
        Log.d(TAG, "onMeetingBooking: ${startTime} ${endTime} ${capacity} ${meetingId}")
    }

    private fun onDeskItemClicked(item: DeskItemModel) {
        Log.d(TAG, "onDeskItemClicked: ${item.toString()}")
    }

    private fun OnMeetingItemClicked(item: MeetingItemModel) {

        Log.d(TAG, "onMeetingItemClicked: ${item.toString()}")
    }

    private fun onLoginClicked(employeeId :String) {
        _uiState.update { it.copy(employeeId = employeeId) }
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