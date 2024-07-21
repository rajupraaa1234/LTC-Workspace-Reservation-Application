package com.example.ltcworkspacereservationapplication.presentation.mvvm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ltcworkspacereservationapplication.R
import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel
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
            is AppIntent.OnFloorSelect -> onFloorSelected(intent.selectedFloor)
            is AppIntent.OnMeetingItemClick -> OnMeetingItemClicked(intent.item,intent.index)
            is AppIntent.OnDeskItemClick -> onDeskItemClicked(intent.item,intent.index)
            is AppIntent.OnMeetingRoomBooking -> onMeetingBooking(intent.startTime,intent.endTime,intent.capacity,intent.meetingId)
            is AppIntent.onLoginClick -> onLoginClicked(intent.employeeId)
        }
    }

    private fun onMeetingBooking(startTime: String, endTime: String, capacity: String, meetingId: String) {
        Log.d(TAG, "onMeetingBooking: ${startTime} ${endTime} ${capacity} ${meetingId}")
    }

    private fun onDeskItemClicked(item: DeskItemModel, index: Int) {
        _uiState.update { state ->
            val updatedItems = state.deskList.mapIndexed { i, item ->
                if (i == index) {
                    item.copy(imageId = R.drawable.selecteddesk) // Change to desired color
                }else{
                    if(item.status == "reserved"){
                        item.copy(imageId = R.drawable.reserveddesk)
                    }else{
                        item.copy(imageId = R.drawable.availabledesk)
                    }
                }
            }
            state.copy(deskList = updatedItems)
        }
    }

    private fun OnMeetingItemClicked(item: MeetingItemModel, index: Int) {
        _uiState.update { state ->
            val updatedItems = state.cabinList.mapIndexed { i, item ->
                if (i == index) {
                    item.copy(imageId = R.drawable.selectedcabin) // Change to desired color
                }else{
                    if(item.reservedSlot.size == 0){
                        item.copy(imageId = R.drawable.availablecabin)
                    }else{
                        item.copy(imageId = R.drawable.reservedcabin)
                    }
                }
            }
            state.copy(cabinList = updatedItems)
        }
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