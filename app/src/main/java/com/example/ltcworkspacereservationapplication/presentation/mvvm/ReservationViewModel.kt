package com.example.ltcworkspacereservationapplication.presentation.mvvm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ltcworkspacereservationapplication.R
import com.example.ltcworkspacereservationapplication.domain.model.AvailabilityType
import com.example.ltcworkspacereservationapplication.domain.model.DeskItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel
import com.example.ltcworkspacereservationapplication.presentation.state.AppState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ReservationViewModel(val context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(AppState())

    private val _effects = MutableSharedFlow<ReservationViewModelEffects>()


    val uiState: StateFlow<AppState>
        get() = _uiState

    val effects: Flow<ReservationViewModelEffects>
        get() = _effects


    var isLoading = mutableStateOf(false)
        protected set

   private val selectedFloor = mutableIntStateOf(-1)


    val TAG =  "ReservationViewModel"


    fun sendIntent(intent: AppIntent) {
        when (intent) {
            AppIntent.OnFilterButtonClicked -> onFilterButtonClicked()
            AppIntent.OnDeskBookButtonClicked -> onDeskBookButtonClicked()
            is AppIntent.OnDatePickerClick -> onDatePickerClicked(intent.dateSelected)
            is AppIntent.OnFloorSelect -> onFloorSelected(intent.selectedFloor)
            is AppIntent.OnMeetingItemClick -> OnMeetingItemClicked(intent.item,intent.index)
            is AppIntent.OnDeskItemClick -> onDeskItemClicked(intent.item,intent.index)
            is AppIntent.OnMeetingRoomBooking -> onMeetingBooking(intent.startTime,intent.endTime,intent.capacity,intent.meetingId)
            is AppIntent.onLoginClick -> onLoginClicked(intent.employeeId)
            is AppIntent.OnDeskListFilter -> OnDeskListFilterUpdate(intent.listItem)
        }
    }

    private fun OnDeskListFilterUpdate(listItem: List<DeskItemModel>) {
        Log.d(TAG, "OnDeskListFilterUpdate: ${listItem}")
        _uiState.update { it.copy(currentFilteredList = listItem) }
    }

    private fun onDeskBookButtonClicked() {
        if(selectedFloor.value == -1){
            Toast.makeText(context, "Please select floor to book", Toast.LENGTH_SHORT).show()
        }else{
            Log.d(TAG, "onDeskBookButtonClicked: booked")
        }
    }

    private fun onMeetingBooking(startTime: String, endTime: String, capacity: String, meetingId: String) {
        Log.d(TAG, "onMeetingBooking: ${startTime} ${endTime} ${capacity} ${meetingId}")
    }

    private fun onDeskItemClicked(itm: DeskItemModel, index: Int) {
        _uiState.update { state ->
            val updatedItems = state.currentFilteredList.mapIndexed { i, item ->
                if (item.seatId == itm.seatId) {
                    item.copy(imageId = R.drawable.selecteddesk) // Change to desired color
                }else{
                    if (item.reservationStatus == AvailabilityType.RESERVED.type){
                        item.copy(imageId = R.drawable.reserveddesk)
                    }else if(item.reservationStatus == AvailabilityType.BOOKED.type){
                        item.copy(imageId = R.drawable.deskbooked)
                    }else{
                        item.copy(imageId = R.drawable.availabledesk)
                    }
                }
            }
            state.copy(currentFilteredList = updatedItems)
        }
        ReservationViewModelEffects.Composable.deskListUpdated.send()
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

    private fun onFloorSelected(floor: Int) {
        selectedFloor.intValue = floor
        ReservationViewModelEffects.Composable.SelectedFloor(filterByFloor(floor)).send()
    }

    private fun onFilterButtonClicked() {
        TODO("Not yet implemented")
    }

    private fun onDatePickerClicked(dateSelected: String) {
        _uiState.update { it.copy(selectedDate = dateSelected) }
    }

    private fun ReservationViewModelEffects.send(){
        viewModelScope.launch { _effects.emit(this@send) }
    }


    fun filterByFloor(floorNumber: Int): List<DeskItemModel> {
       return _uiState.value.deskList.filter { it.floorNumber == floorNumber }
    }

}